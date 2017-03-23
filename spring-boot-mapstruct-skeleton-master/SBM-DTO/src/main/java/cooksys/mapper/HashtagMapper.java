package cooksys.mapper;

import org.mapstruct.Mapper;

import cooksys.db.entity.Hashtag;
import cooksys.dto.HashtagDto;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagDto toHashtagDto(Hashtag hashtag);
	
	Hashtag toHashtab(HashtagDto hashtagDto);

}
