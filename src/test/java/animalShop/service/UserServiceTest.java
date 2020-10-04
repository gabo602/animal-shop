package animalShop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import animalShop.AbstractTest;
import sk.pga.animalshop.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceTest extends AbstractTest {
	
	@Autowired
	private UserService userService;
	
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void checkGetCurrentUserByAnonymous() {
		logger.info("Starting checkFindAllUsersByAnonymous test");
		
		userService.getCurrentUser();
	}
	
	@Test
	@WithMockUser
	public void checkGetCurrentUserByUser() {
		logger.info("Starting checkFindAllUsersByAnonymous test");
		
		userService.getCurrentUser();
	}
}
