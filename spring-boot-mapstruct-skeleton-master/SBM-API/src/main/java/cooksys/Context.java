package cooksys;

import java.util.ArrayList;
import java.util.List;

import cooksys.dto.TweetDto;

public class Context {

	private TweetDto target;
	
	private List<TweetDto> before = new ArrayList<>();
	
	private List<TweetDto> after = new ArrayList<>();

	public TweetDto getTarget() {
		return target;
	}

	public void setTarget(TweetDto target) {
		this.target = target;
	}

	public List<TweetDto> getBefore() {
		return before;
	}

	public void setBefore(List<TweetDto> before) {
		this.before = before;
	}

	public List<TweetDto> getAfter() {
		return after;
	}

	public void setAfter(List<TweetDto> after) {
		this.after = after;
	}
}
