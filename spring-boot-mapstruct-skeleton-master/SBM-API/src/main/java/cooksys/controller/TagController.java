package cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.dto.HashtagDto;
import cooksys.dto.TweetDto;
import cooksys.service.TagService;

@RestController
@RequestMapping("tags")
public class TagController {
	
	private TagService tagService;
	
	public TagController(TagService tagService){
		super();
		this.tagService = tagService;
	}
	
	@GetMapping
	public List<HashtagDto> get(){
		return tagService.index();
	}
	
	@GetMapping("{label}")
	public List<TweetDto> getTag(@PathVariable String label){
		return tagService.taggedTweets(label);
	}

}
