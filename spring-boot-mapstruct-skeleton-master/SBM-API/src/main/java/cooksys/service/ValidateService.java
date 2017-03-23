package cooksys.service;

import org.springframework.stereotype.Service;

import cooksys.db.entity.Hashtag;
import cooksys.db.entity.User;
import cooksys.db.repository.TagRepository;
import cooksys.db.repository.UserRepository;

@Service
public class ValidateService {

	private UserRepository userRepository;
	
	private TagRepository tagRepository;
	
	public boolean tag(String label) {
		Hashtag check = tagRepository.findByLabel(label);
		if(check.equals(null))
			return false;
		else
			return true;
	}

	public boolean userExists(String username) {
		User check = userRepository.findByUname(username);
		if(check.equals(null) || check.isDeleted()){
			return false;
		}
		else 
			return true;
	}

	public boolean userAvailable(String username) {
		User check = userRepository.findByUname(username);
		if(check.equals(null))
			return true;
		else 
			return false;
	}

}
