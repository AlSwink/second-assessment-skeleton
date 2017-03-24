package cooksys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cooksys.db.entity.Tweet;
import cooksys.db.entity.User;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.entity.embeddable.Profile;
import cooksys.db.repository.UserRepository;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.mapper.TweetMapper;
import cooksys.mapper.UserMapper;

@Service
public class UserService {

	UserRepository userRepository;
	UserMapper userMapper;
	TweetMapper tweetMapper;

	public UserService(UserRepository userRepo, UserMapper userMap, TweetMapper tweetMapper) {
		super();
		this.userMapper = userMap;
		this.userRepository = userRepo;
		this.tweetMapper = tweetMapper;
	}

	// working
	public List<UserDto> index() {
		return userRepository.findByDeletedFalse().stream().map(userMapper::toUserDto).collect(Collectors.toList());
	}

	// working
	public UserDto post(Credentials credentials, Profile profile) {
		// find a better way to do this
		User created = new User();
		created.setUname(credentials.getUsername());
		created.setCredentials(credentials);
		created.setProfile(profile);
		userRepository.save(created);
		return userMapper.toUserDto(created);
	}

	// working
	public UserDto get(String username) {
		return userMapper.toUserDto(userRepository.findByUname(username));
	}

	// working
	public UserDto delete(String username, Credentials credentials) {
		if (credentialCheck(username, credentials)) {
			User deleted = userRepository.findByUname(username);
			deleted.setDeleted(true);
			userRepository.save(deleted);
			return userMapper.toUserDto(deleted);
		} else
			return null;
	}

	// working
	public void follow(String username, Credentials credentials) {
		if (credentialCheck(credentials.getUsername(), credentials)) {
			User follower = userRepository.findByCredentialsUsername(credentials.getUsername());
			User followee = userRepository.findByUname(username);
			follower.getFollowing().add(followee);
			followee.getFollowers().add(follower);
			userRepository.save(follower);
			userRepository.save(followee);
		}
	}

	// working
	public void unfollow(String username, Credentials credentials) {
		if (credentialCheck(credentials.getUsername(), credentials)) {
			User follower = userRepository.findByCredentialsUsername(credentials.getUsername());
			User followee = userRepository.findByUname(username);
			follower.getFollowing().remove(followee);
			followee.getFollowers().remove(follower);
			userRepository.save(follower);
			userRepository.save(followee);
		}
	}

	public UserDto patch(String username, Credentials credentials, Profile profile) {
		if (credentialCheck(username, credentials)) {
			User patched = userRepository.findByUname(username);
			patched.setProfile(profile);
			userRepository.save(patched);
			return userMapper.toUserDto(patched);
		}
		return null;
	}

	// working
	public Set<UserDto> getFollowers(String username) {
		User getting = userRepository.findByUname(username);
		Set<UserDto> dtoSet = new HashSet<>();
		for (User u : getting.getFollowers()) {
			dtoSet.add(userMapper.toUserDto(u));
		}
		return dtoSet;
	}

	// working
	public Set<UserDto> getFollowing(String username) {
		User getting = userRepository.findByUname(username);
		Set<UserDto> dtoSet = new HashSet<>();
		for (User u : getting.getFollowing()) {
			dtoSet.add(userMapper.toUserDto(u));
		}
		return dtoSet;
	}

	public List<TweetDto> feed(String username) {
		// TODO Auto-generated method stub
		List<TweetDto> self = userRepository
				.findByUname(username)
				.getTweets()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
		for(User u : userRepository.findByUname(username).getFollowing()){
			self.addAll(
					u.getTweets().stream().map(tweetMapper::toTweetDto).collect(Collectors.toList())
					);			
		}
				
		return self;
	}

	public List<TweetDto> tweets(String username) {
		List<Tweet> convert = userRepository.findByUname(username).getTweets();
		
		List<TweetDto> dto = new ArrayList<>();
		for(Tweet t : convert){
			if(t.isDeleted() == false)
				dto.add(tweetMapper.toTweetDto(t));
			
		}
		return dto;
	}

	public List<TweetDto> mentions(String username) {
		return userRepository.findByUname(username)
				.getMentioned()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
	}

	public boolean credentialCheck(String username, Credentials credentials) {
		if (userRepository.findByCredentialsUsername(username).getCredentials().equals(credentials))
			return true;
		else
			return false;
	}
}
