package cooksys.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

//import cooksys.controller.Context;
import cooksys.db.entity.Hashtag;
import cooksys.db.entity.Tweet;
import cooksys.db.entity.User;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.repository.TagRepository;
import cooksys.db.repository.TweetRepository;
import cooksys.db.repository.UserRepository;
import cooksys.dto.HashtagDto;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.mapper.HashtagMapper;
import cooksys.mapper.TweetMapper;

@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private TagRepository tagRepository;
	private TweetMapper tweetMapper;
	private HashtagMapper tagMapper;

	public List<TweetDto> index() {
		return tweetRepository
				.findByDeletedFalse()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
	}

	public TweetDto post(Credentials credentials, String content) {
	
		Tweet posted = new Tweet();
		User author = userRepository.findByCredentialsUsername(credentials.getUsername());
		posted.setAuthor(author);
		posted.setContent(content);
		posted.setTagsUsed(tagSearch(content));
		author.getTweets().add(posted);
		userRepository.save(author);
		tweetRepository.save(posted);
		return tweetMapper.toTweetDto(posted);
	}

	public TweetDto get(int id) {
		return tweetMapper.toTweetDto(tweetRepository.findById(id));
	}

	public TweetDto delete(int id, Credentials credentials) {
		if(credentialCheck(id, credentials)){
			Tweet deleted = tweetRepository.findById(id);
			deleted.setDeleted(true);
			tweetRepository.save(deleted);
			return tweetMapper.toTweetDto(deleted);
		}
		else return null;
	}

	public void like(int id, Credentials credentials) {
		if (credentialCheck(id, credentials)) {
			Tweet liked = tweetRepository.findById(id);
			User liker = userRepository.findByCredentialsUsername(credentials.getUsername());
			liked.getLikes().add(liker);
			liker.getLiked().add(liked);
			tweetRepository.save(liked);
			userRepository.save(liker);
		}
	}

	public TweetDto reply(int id, Credentials credentials, String content) {
		Tweet reply = new Tweet();
		Tweet repliedTo = tweetRepository.findById(id);
		User author = userRepository.findByCredentialsUsername(credentials.getUsername());
		reply.setAuthor(author);
		reply.setContent(content);
		reply.setTagsUsed(tagSearch(content));
		reply.setInReplyTo(tweetRepository.findById(id));
		repliedTo.getReplies().add(reply);
		author.getTweets().add(reply);
		userRepository.save(author);
		tweetRepository.save(reply);
		tweetRepository.save(repliedTo);
		
		return tweetMapper.toTweetDto(reply);
	}

	public TweetDto repost(int id, Credentials credentials) {
		Tweet repost = new Tweet();
		Tweet reposted = tweetRepository.findById(id);
		User author = userRepository.findByCredentialsUsername(credentials.getUsername());
		repost.setAuthor(author);
		repost.setRepostOf(reposted);
		reposted.getReposts().add(repost);
		author.getTweets().add(repost);
		userRepository.save(author);
		tweetRepository.save(repost);
		tweetRepository.save(reposted);
		return tweetMapper.toTweetDto(repost);
	}

	public Set<HashtagDto> getTags(int id) {
		
		return tweetRepository.findById(id)
				.getTagsUsed()
				.stream()
				.map(tagMapper::toHashtagDto)
				.collect(Collectors.toSet());
	}

	public List<UserDto> getLikes(int id) {
		// TODO Auto-generated method stub
		return null;
	}

//	public Context context(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<TweetDto> getReplies(int id) {
		List<Tweet> replies = tweetRepository.findById(id).getReplies();
		List<TweetDto> replyDto = replies.stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
		return replyDto;
	}

	public List<TweetDto> getReposts(int id) {
		List<Tweet> reposts = tweetRepository.findById(id).getReposts();
		List<TweetDto> repostDto = reposts.stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
		return repostDto;
	}

	public List<TweetDto> mentions(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean credentialCheck(int id, Credentials credentials){
		if(tweetRepository.findById(id).getAuthor().getCredentials().equals(credentials))
			return true;
		else return false;
	}
	
	public Set<Hashtag> tagSearch(String content){
		Set<Hashtag> tags = new HashSet<>();
		for (String retval: content.split("(?=#)")) {
	         if(retval.startsWith("#")){
	        	 Hashtag find = tagRepository.findByLabel(retval.substring(1));
	        	 if(find == null){
	        		 Hashtag temp = new Hashtag();
	        		 temp.setLabel(retval.substring(1));
	        		 tagRepository.save(temp);
	        		 tags.add(temp);
	        	 } else {
	        		 tags.add(find);
	        		 find.setLastUsed(new Timestamp(System.currentTimeMillis()));
	        		 tagRepository.save(find);
	        	 }
	         }
	    }
		return tags;
	}
	
	public void mentionSearch(String content){
		
	}
}
