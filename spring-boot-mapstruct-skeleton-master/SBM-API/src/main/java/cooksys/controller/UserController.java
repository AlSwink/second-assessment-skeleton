package cooksys.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.entity.embeddable.Profile;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.service.UserService;

@RestController
@Validated
@RequestMapping("users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService){
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserDto> index() {
		return userService.index();
	}
	//can't have multiple requestbody variables, fix this
	@PostMapping
	public UserDto post(@RequestBody Credentials credentials, @RequestBody Profile profile, HttpServletResponse httpResponse){
		UserDto dto = userService.post(credentials, profile);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}
	
	@GetMapping("@{username}")
	public UserDto getUsername(@PathVariable String username){
		return userService.get(username);
	}
	//can't have multiple requestbody variables, fix this
	@PatchMapping("@{username}")
	public UserDto patchUser(@PathVariable String username, @RequestBody Credentials credentials, @RequestBody Profile profile, HttpServletResponse httpResponse){
		return userService.patch(username, credentials, profile);
	}
	//can't have multiple requestbody variables, fix this
	@DeleteMapping("@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody Credentials credentials, @RequestBody Profile profile, HttpServletResponse httpResponse){
		return userService.delete(username, credentials, profile);
	}
	
	@PostMapping("@{username}/follow")
	public void follow(@PathVariable String username, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		userService.follow(username, credentials);
	}
	
	@PostMapping("@{username}/unfollow")
	public void unfollow(@PathVariable String username, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		userService.unfollow(username, credentials);
	}
	
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username){
		return userService.feed(username);
	}
	
	@GetMapping("@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username){
		return userService.tweets(username);
	}
	
	@GetMapping("@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username){
		return userService.mentions(username);
	}
	
	@GetMapping("@{username}/followers")
	public List<UserDto> getFollowers(@PathVariable String username){
		return userService.getFollowers(username);
	}
	
	@GetMapping("@{username}/following")
	public List<UserDto> getFollowing(@PathVariable String username){
		return userService.getFollowing(username);
	}
	
	
}
