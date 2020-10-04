package animalShop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
public class LoginTest extends AbstractTest {
	
	@Test
	public void testLoginSuccess() throws Exception {
		String userJson = "{\"username\":\"mojemeno\",\"password\":\"heslo\",\"email\":\"garpet88@gmail.com\"}";
		
		ResultActions result = mockMvc.perform(post("/rest/saveUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));
		
		RequestBuilder requestBuilder = post("/login")
				.param("username", "mojemeno")
				.param("password", "heslo");
		
		result = mockMvc.perform(requestBuilder);
		
		result.andDo(MockMvcResultHandlers.print());

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		result.andExpect(SecurityMockMvcResultMatchers.authenticated().withAuthorities(authorities));
	}
	
}
