package animalShop;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import sk.pga.animalshop.config.AppConfig;

@WebAppConfiguration
@SpringBootTest(classes = { AppConfig.class })
@Profile("dev-tests")
public class AbstractTest {
	
	protected final Logger				logger			= LoggerFactory.getLogger(getClass());
	
	protected static final MediaType	JSON_MEDIA_TYPE	= new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());
	
	protected MockMvc					mockMvc;
	
	@Autowired
	private FilterChainProxy			springSecurityFilterChain;
	
	@Autowired
	private WebApplicationContext		context;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
				.build();
	}
	
	protected RequestPostProcessor testUser() {
		return user("mojemeno").password("heslo").roles("USER");
	}
	
	protected RequestPostProcessor testAdmin() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return user("admin").password("adminPass").authorities(authorities);
	}
	
	protected ObjectMapper GetObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		mapper.registerModule(hibernate5Module);
		return mapper;
	}
	
}
