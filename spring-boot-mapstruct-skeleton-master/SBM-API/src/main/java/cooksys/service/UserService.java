package cooksys.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import cooksys.db.entity.User;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.entity.embeddable.Profile;
import cooksys.db.repository.UserRepository;
import cooksys.dto.UserDto;
import cooksys.mapper.UserMapper;

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
		created.setUsername(credentials.getUsername());
		created.setCredentials(credentials);
		created.setProfile(profile);
		created.setJoined(new Timestamp(System.currentTimeMillis()));
		userRepository.save(created);
		return userMapper.toUserDto(created);
	}

	public UserDto get(String username){
		return userMapper.toUserDto(userRepository.findByUsername(username));
	}

	public UserDto delete(String username, Credentials credentials, Profile profile) {
		User deleted = userRepository.findByUsername(username);
		deleted.setDeleted(true);
		return userMapper.toUserDto(deleted);
	}
	
	public void follow(String username, Credentials credentials){
		//add followers and following to user.java then come back
	}
	
	public void unfollow(String username, Credentials credentials){
		//add followers and following to user.java then come back
	}
}
