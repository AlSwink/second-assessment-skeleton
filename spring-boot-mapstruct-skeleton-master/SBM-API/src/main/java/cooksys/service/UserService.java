package cooksys.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cooksys.db.entity.User;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.entity.embeddable.Profile;
import cooksys.db.repository.UserRepository;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.mapper.UserMapper;

@Service
public class UserService {

	UserRepository userRepository;
	UserMapper userMapper;
	
	public UserService(UserRepository userRepo, UserMapper userMap){
		super();
		this.userMapper = userMap;
		this.userRepository = userRepo;
	}
	
	public List<UserDto> index() {
		return userRepository
				.findAll()
				.stream()
				.map(userMapper::toUserDto)
				.collect(Collectors.toList());
	}
	
	public UserDto post(Credentials credentials, Profile profile){
		//find a better way to do this
		User created = new User();
		created.setUname(credentials.getUsername());
		created.setCredentials(credentials);
		created.setProfile(profile);
		created.setJoined(new Timestamp(System.currentTimeMillis()));
		userRepository.save(created);
		return userMapper.toUserDto(created);
	}

	public UserDto get(String username){
		return userMapper.toUserDto(userRepository.findByUname(username));
	}

	public UserDto delete(String username, Credentials credentials, Profile profile) {
		User deleted = userRepository.findByUname(username);
		deleted.setDeleted(true);
		return userMapper.toUserDto(deleted);
	}
	
	public void follow(String username, Credentials credentials){
		//add followers and following to user.java then come back
	}
	
	public void unfollow(String username, Credentials credentials){
		//add followers and following to user.java then come back
	}

	public UserDto patch(String username, Credentials credentials, Profile profile) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getFollowers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getFollowing(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> feed(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> tweets(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> mentions(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
