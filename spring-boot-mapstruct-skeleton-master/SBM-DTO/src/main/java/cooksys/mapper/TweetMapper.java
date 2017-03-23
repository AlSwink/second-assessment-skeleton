package cooksys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cooksys.db.entity.Tweet;
import cooksys.dto.TweetDto;
import cooksys.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TweetMapper {

	
	TweetDto toTweetDto(Tweet tweet);
	
	Tweet toTweet(TweetDto tweetDto);
}
