package cooksys.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cooksys.db.repository.TagRepository;
import cooksys.dto.HashtagDto;
import cooksys.dto.TweetDto;
import cooksys.mapper.HashtagMapper;
import cooksys.mapper.TweetMapper;

@Service
public class TagService {
	
	private TagRepository tagRepository;
	private HashtagMapper tagMapper;
	private TweetMapper tweetMapper;
	
	public TagService(){
		super();
	}

	public List<TweetDto> taggedTweets(String label) {
		return tagRepository
				.findByLabel(label)
				.getUsedIn()
				.stream()
				.map(tweetMapper::toTweetDto)
				.collect(Collectors.toList());
	}

	public List<HashtagDto> index() {
		return tagRepository
				.findAll()
				.stream()
				.map(tagMapper::toHashtagDto)
				.collect(Collectors.toList());
				
	}

}
