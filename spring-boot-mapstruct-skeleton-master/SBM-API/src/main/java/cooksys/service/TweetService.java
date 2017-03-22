package cooksys.service;

import java.util.List;

import org.springframework.stereotype.Service;

//import cooksys.controller.Context;
import cooksys.db.entity.Hashtag;
import cooksys.db.entity.embeddable.Credentials;
import cooksys.dto.TweetDto;
import cooksys.dto.UserDto;

@Service
public class TweetService {

	public List<TweetDto> index() {
		// TODO Auto-generated method stub
		return null;
	}

	public TweetDto post(Credentials credentials, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	public TweetDto get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public TweetDto delete(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	public void like(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		
	}

	public TweetDto reply(int id, Credentials credentials, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	public TweetDto repost(int id, Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Hashtag> getTags(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getLikes(int id) {
		// TODO Auto-generated method stub
		return null;
	}

//	public Context context(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<TweetDto> getReplies(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> getReposts(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TweetDto> mentions(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
