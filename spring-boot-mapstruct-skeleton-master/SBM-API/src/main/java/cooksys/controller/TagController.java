package cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.db.entity.Hashtag;
import cooksys.db.entity.Tweet;
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
	public List<Hashtag> get(){
		return tagService.index();
	}
	
	@GetMapping("{label}")
	public List<Tweet> getTag(@PathVariable String label){
		return tagService.taggedTweets(label);
	}

}
