package cooksys.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.Context;
import cooksys.RequestWrapper;
import cooksys.db.entity.Hashtag;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.dto.HashtagDto;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;
import cooksys.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {
	
	private TweetService tweetService;
	
	public TweetController(TweetService tweetService){
		super();
		this.tweetService = tweetService;
	}
	
	@GetMapping
	public List<TweetDto> index(){
		return tweetService.index();
	}
	//can't have multiple requestbody variables, fix this
	@PostMapping
	public TweetDto post(@RequestBody RequestWrapper wrapper, HttpServletResponse httpResponse){
		TweetDto dto = tweetService.post(wrapper.getCredentials().getUsername(), wrapper.getContent());
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}
	
	@GetMapping("{id}")
	public TweetDto get(@PathVariable int id){
		return tweetService.get(id);
	}
	
	@DeleteMapping("{id}")
	public TweetDto delete(@PathVariable int id, @RequestBody Credentials credentials){
		return tweetService.delete(id, credentials);
	}
	
	@PostMapping("{id}/like")
	public void like(@PathVariable int id, @RequestBody Credentials credentials){
		tweetService.like(id, credentials);
	}
	
	//can't have multiple requestbody variables, fix this
	@PostMapping("{id}/reply")
	public TweetDto reply(@PathVariable int id, @RequestBody RequestWrapper wrapper, HttpServletResponse httpResponse){
		TweetDto dto = tweetService.reply(id, wrapper.getCredentials(), wrapper.getContent());
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}
	
	@PostMapping("{id}/repost")
	public TweetDto repost(@PathVariable int id, @RequestBody Credentials credentials, HttpServletResponse httpResponse){
		TweetDto dto = tweetService.repost(id, credentials);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return dto;
	}
	
	@GetMapping("{id}/tags")
	public List<HashtagDto> tags(@PathVariable int id){
		return tweetService.getTags(id);
	}
	
	@GetMapping("{id}/likes")
	public List<UserDto> getLikes(@PathVariable int id){
		return tweetService.getLikes(id);
	}
	
	@GetMapping("{id}/context")
	public Context getContext(@PathVariable int id){
		return tweetService.context(id);
	}
	
	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable int id){
		return tweetService.getReplies(id);
	}
	
	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable int id){
		return tweetService.getReposts(id);
	}
	
	@GetMapping("{id}/mentions")
	public List<UserDto> getMentions(@PathVariable int id){
		return tweetService.mentions(id);
	}
	
}
