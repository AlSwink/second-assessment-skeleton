package cooksys.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

//import cooksys.controller.Context;
import cooksys.db.entity.Hashtag;
import cooksys.db.entity.Tweet;
import cooksys.db.entity.User;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.repository.TweetRepository;
import cooksys.db.repository.UserRepository;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.mapper.TweetMapper;

@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	private UserRepository userRepository;
	private TweetMapper tweetMapper;

	public List<TweetDto> index() {
		return tweetRepository
				.findByDeletedFalse()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
	}

	public TweetDto post(Credentials credentials, String content) {
		// TODO Auto-generated method stub
		Tweet posted = new Tweet();
		User author = userRepository.findByCredentialsUsername(credentials.getUsername());
		posted.setAuthor(author);
		posted.setContent(content);
		author.getTweets().add(posted);
		userRepository.save(author);
		tweetRepository.save(posted);
		return tweetMapper.toTweetDto(posted);
	}

	public TweetDto get(int id) {
		return tweetMapper.toTweetDto(tweetRepository.findById(id));
	}

	public TweetDto delete(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	public void like(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		
	}

	public TweetDto reply(int id, Credentials credentials, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	public TweetDto repost(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Hashtag> getTags(int id) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> getReposts(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> mentions(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
