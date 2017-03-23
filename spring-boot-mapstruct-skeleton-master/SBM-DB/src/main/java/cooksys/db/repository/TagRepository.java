package cooksys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.db.entity.Hashtag;

public interface TagRepository extends JpaRepository<Hashtag, Integer>{
	
	Hashtag findByLabel(String label);

}
