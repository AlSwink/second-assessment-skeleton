package cooksys.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import cooksys.db.entity.embeddable.Profile;

public class UserDto {

	@NotNull
	private String username;
	
	@NotNull
	private Profile profile;
	
	@NotNull
	private Long joined;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Long getJoined() {
		return joined;
	}

	public void setJoined(Long joined) {
		this.joined = joined;
	}
}
