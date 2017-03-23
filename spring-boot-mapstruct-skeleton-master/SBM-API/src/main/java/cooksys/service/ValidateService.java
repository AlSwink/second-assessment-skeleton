package cooksys.service;

import org.springframework.stereotype.Service;

import cooksys.db.entity.User;
import cooksys.db.repository.UserRepository;

@Service
public class ValidateService {

	private UserRepository userRepository;
	public boolean tag(String label) {
		// TODO Auto-generated method stub
		return false;
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
