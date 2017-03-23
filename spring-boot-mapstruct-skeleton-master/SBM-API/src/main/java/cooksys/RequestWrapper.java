package cooksys;

import cooksys.db.entity.embeddable.Credentials;
import cooksys.db.entity.embeddable.Profile;

public class RequestWrapper {

	Credentials credentials;
	Profile profile;
	String content;
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
