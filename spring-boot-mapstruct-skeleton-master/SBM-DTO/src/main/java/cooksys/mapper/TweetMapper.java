package cooksys.mapper;

import org.mapstruct.Mapper;

import cooksys.db.entity.Tweet;
import cooksys.dto.TweetDto;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetDto toTweetDto(Tweet tweet);
	
	Tweet toTweet(TweetDto tweetDto);
}
