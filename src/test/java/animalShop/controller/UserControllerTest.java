package animalShop.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import animalShop.AbstractTest;
import sk.pga.animalshop.model.dto.UserDto;
import sk.pga.animalshop.service.UserService;

@RunWith(SpringRunner.class)
public class UserControllerTest extends AbstractTest {
	
	@MockBean
	private UserService userService;
	
	@Test
	public void testCreateUser() throws Exception {
		UserDto testUser = new UserDto();
		testUser.setUsername("mojemeno");
		testUser.setPassword("heslo");
		testUser.setEmail("garpet88@gmail.com");
		
		doNothing().when(userService).saveRegularUser(testUser);
		
		String userJson = "{\"username\":\"mojemeno\",\"password\":\"heslo\",\"email\":\"garpet88@gmail.com\"}";
		
		ResultActions result = mockMvc.perform(post("/rest/saveUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));
		
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(status().isCreated());
		verify(userService, times(1)).saveRegularUser(testUser);
		verifyNoMoreInteractions(userService);
	}
}
