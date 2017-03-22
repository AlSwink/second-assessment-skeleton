package cooksys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cooksys.db.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUname(String username);
	User findByCredentialsUsername(String username);
}
