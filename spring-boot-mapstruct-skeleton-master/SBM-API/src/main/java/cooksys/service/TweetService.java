package cooksys.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cooksys.Context;
import cooksys.TweetByTimeComparator;
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
import cooksys.mapper.UserMapper;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private TagRepository tagRepository;
	private TweetMapper tweetMapper;
	private HashtagMapper tagMapper;
	private UserMapper userMapper;

	public TweetService(TweetRepository tweetRepository, UserRepository userRepository, TagRepository tagRepository,
			TweetMapper tweetMapper, HashtagMapper tagMapper, UserMapper userMapper) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
		this.tweetMapper = tweetMapper;
		this.tagMapper = tagMapper;
		this.userMapper = userMapper;
	}

	// works
	public List<TweetDto> index() {
		return tweetRepository.findByDeletedFalse().stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
	}

	// works
	public TweetDto post(String username, String content) {

		Tweet posted = new Tweet();
		User author = userRepository.findByUname(username);
		posted.setAuthor(author);
		posted.setContent(content);
		posted.setTagsUsed(tagSearch(content));
		posted.setMentions(mentionSearch(content));
		author.getTweets().add(posted);
		userRepository.saveAndFlush(author);
		tweetRepository.saveAndFlush(posted);

		return tweetMapper.toTweetDto(posted);
	}

	// works
	public TweetDto get(int id) {
		return tweetMapper.toTweetDto(tweetRepository.findByIdAndDeletedFalse(id));
	}

	// works
	public TweetDto delete(int id, Credentials credentials) {
		if (credentialCheck(id, credentials)) {
			Tweet deleted = tweetRepository.findById(id);
			deleted.setDeleted(true);
			tweetRepository.save(deleted);
			return tweetMapper.toTweetDto(deleted);
		} else
			return null;
	}

	// works
	public void like(int id, Credentials credentials) {
		if (credentialCheck(id, credentials)) {
			Tweet liked = tweetRepository.findByIdAndDeletedFalse(id);
			User liker = userRepository.findByCredentialsUsername(credentials.getUsername());
			liked.getLikes().add(liker);
			liker.getLiked().add(liked);
			tweetRepository.save(liked);
			userRepository.save(liker);
		}
	}

	// works
	public TweetDto reply(int id, Credentials credentials, String content) {
		Tweet reply = new Tweet();
		Tweet repliedTo = tweetRepository.findByIdAndDeletedFalse(id);
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

	// works
	public TweetDto repost(int id, Credentials credentials) {
		Tweet repost = new Tweet();
		Tweet reposted = tweetRepository.findByIdAndDeletedFalse(id);
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

	public List<HashtagDto> getTags(int id) {

		List<HashtagDto> dto = tweetRepository.findByIdAndDeletedFalse(id).getTagsUsed().stream()
				.map(tagMapper::toHashtagDto).collect(Collectors.toList());
		System.out.println(tweetRepository.findById(id).getTagsUsed());
		return dto;
	}

	// works
	public List<UserDto> getLikes(int id) {
		return tweetRepository.findByIdAndDeletedFalse(id).getLikes().stream().map(userMapper::toUserDto)
				.collect(Collectors.toList());
	}

	 public Context context(int id) {
	 // TODO Auto-generated method stub
		 Context c = new Context();
		 c.setTarget(tweetMapper.toTweetDto(tweetRepository.findByIdAndDeletedFalse(id)));
		 boolean top = false;
		 TweetDto t  = c.getTarget().getInReplyTo();
		 while(top == false){
			 if(t != null){
				 c.getBefore().add(t);
				 t = t.getInReplyTo();
			 } else
				 top = true; 
		 }
		 Tweet w = tweetMapper.toTweet(c.getTarget());
		 if(w.getReplies() != null){
		 for(Tweet e : w.getReplies()){
			 c.getAfter().addAll(getReplies(e.getId()));
			 for(Tweet n : e.getReplies()){
				 c.getAfter().addAll(getReplies(n.getId()));
			 }
		 }
		 }
		 Collections.sort(c.getBefore(), new TweetByTimeComparator());
		 Collections.sort(c.getAfter(), new TweetByTimeComparator());
		 return c;
	 }
	// works
	public List<TweetDto> getReplies(int id) {
		List<Tweet> replies = tweetRepository.findByIdAndDeletedFalse(id).getReplies();
		if (replies.isEmpty())
			return null;
		else {
			List<TweetDto> replyDto = replies.stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
			return replyDto;
		}
	}

	// works
	public List<TweetDto> getReposts(int id) {
		List<Tweet> reposts = tweetRepository.findByIdAndDeletedFalse(id).getReposts();
		List<TweetDto> repostDto = reposts.stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
		return repostDto;
	}

	public List<UserDto> mentions(int id) {
		return tweetRepository.findByIdAndDeletedFalse(id).getMentions().stream().map(userMapper::toUserDto)
				.collect(Collectors.toList());
	}

	public boolean credentialCheck(int id, Credentials credentials) {
		if (tweetRepository.findById(id).getAuthor().getCredentials().equals(credentials))
			return true;
		else
			return false;
	}

	public List<Hashtag> tagSearch(String content) {
		List<Hashtag> tags = new ArrayList<>();
		for (String retval : content.split("(?=#)")) {
			if (retval.startsWith("#")) {
				Hashtag find = tagRepository.findByLabel(retval.substring(1));
				if (find == null) {

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

	public List<User> mentionSearch(String content) {
		List<User> mentions = new ArrayList<>();
		for (String retval : content.split("(?=@)")) {
			if (retval.startsWith("@")) {
				User find = userRepository.findByUname(retval.substring(1));
				if (find != null) {
					mentions.add(find);
				}
			}
		}
		return mentions;
	}
}
