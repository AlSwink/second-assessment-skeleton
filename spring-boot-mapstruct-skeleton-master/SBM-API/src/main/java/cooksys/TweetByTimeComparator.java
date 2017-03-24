package cooksys;

import java.util.Comparator;

import cooksys.db.entity.Tweet;
import cooksys.dto.TweetDto;

public class TweetByTimeComparator implements Comparator<TweetDto> {

	@Override
	public int compare(TweetDto o1, TweetDto o2) {
		int startComparison = compare(o1.getPosted().getTime(), o2.getPosted().getTime());
		return startComparison;
	}

	private static int compare(Long a, Long b) {
		return a < b ? -1 : a > b ? 1 : 0;
	}
}
