package cooksys.db.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Hashtag {

	@Id
	@GeneratedValue
	private int id;
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	@ManyToMany
	private Set<Tweet> usedIn;
}
