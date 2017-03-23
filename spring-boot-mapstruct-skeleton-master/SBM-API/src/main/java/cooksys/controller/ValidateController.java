package cooksys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.service.ValidateService;

@RestController
@RequestMapping("validate")
public class ValidateController {

	private ValidateService validateService;
	
	public ValidateController(ValidateService validateService){
		super();
		this.validateService = validateService;
	}
	
	@GetMapping("tag/exists/{label}")
	public boolean tagCheck(@RequestBody String label){
		return validateService.tag(label);
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean exists(@RequestBody String username){
		return validateService.userExists(username);
	}
	
	@GetMapping("username/available/@{username}")
	public boolean available(@RequestBody String username){
		return validateService.userAvailable(username);
	}
}
