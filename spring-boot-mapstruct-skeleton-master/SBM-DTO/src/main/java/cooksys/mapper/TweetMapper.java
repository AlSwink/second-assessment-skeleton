package cooksys.mapper;

import org.mapstruct.Mapper;

import cooksys.db.entity.Tweet;
import cooksys.dto.ReplyDto;
import cooksys.dto.RepostDto;
import cooksys.dto.SimpleDto;
import cooksys.dto.TweetDto;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TweetMapper {

	
	TweetDto toTweetDto(Tweet tweet);
	
	Tweet toTweet(TweetDto tweetDto);
	
	ReplyDto toReplyDto(Tweet tweet);
	
	Tweet toTweet(ReplyDto replyDto);
	
	RepostDto toRepostDto(Tweet tweet);
	
	Tweet toTweet(RepostDto repostDto);
	
	SimpleDto toSimpleDto(Tweet tweet);
	
	Tweet toTweet(SimpleDto simpleDto);
}
