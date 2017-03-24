package cooksys.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.db.entity.Tweet;
import cooksys.db.entity.User;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{
	
	Tweet findById(Integer id);
	Tweet findByIdAndDeletedFalse(Integer id);
	Tweet findByRepostOf(Tweet tweet);
	Tweet findByAuthor(User user);
	List<Tweet> findByDeletedFalse();
}
