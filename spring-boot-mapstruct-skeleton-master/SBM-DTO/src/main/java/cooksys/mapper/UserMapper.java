package cooksys.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cooksys.db.entity.User;
import cooksys.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(source = "uname", target = "uname")
	UserDto toUserDto(User user);
	@Mapping(source = "uname", target = "uname")
	User toUser(UserDto userDto);
}
