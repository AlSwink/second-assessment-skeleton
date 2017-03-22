package cooksys.mapper;

import org.mapstruct.Mapper;

import cooksys.db.entity.User;
import cooksys.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDto toUserDto(User user);
	
	User toUser(UserDto userDto);
}
