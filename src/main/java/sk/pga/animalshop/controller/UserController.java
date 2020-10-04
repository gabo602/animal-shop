package sk.pga.animalshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import sk.pga.animalshop.model.dto.UserDto;
import sk.pga.animalshop.model.projection.UserView;
import sk.pga.animalshop.service.UserService;

@RestController
@RequestMapping("/rest")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/saveUser" }, method = RequestMethod.POST)
	public ResponseEntity<Void> saveUser(@Validated @RequestBody UserDto user, UriComponentsBuilder ucBuilder) {
		try {
			userService.saveRegularUser(user);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = { "/saveAdmin" }, method = RequestMethod.POST)
	public ResponseEntity<Void> saveAdmin(@Validated @RequestBody UserDto user, UriComponentsBuilder ucBuilder) {
		try {
			userService.saveAdminUser(user);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("authenticated")
	@RequestMapping(value = { "/getUser" }, method = RequestMethod.GET)
	public HttpEntity<UserView> getUser(@RequestParam(value = "username", required = true) String username) {
		UserView user = userService.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
