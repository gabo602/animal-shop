package sk.pga.animalshop.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		Assert.isInstanceOf(CustomUserDetailsService.class, userDetailsService, "Only CustomUserDetailsService is supported");
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public void setUserCache(UserCache userCache) {
		throw new RuntimeException("UserCache is not supported");
	}
	
	protected CustomUserDetailsService getCustomUserDetailsService() {
		return (CustomUserDetailsService) getUserDetailsService();
	}
	
	/**
	 * Copied from {@link DaoAuthenticationProvider#retrieveUser}
	 */
	private CustomUserDetails retrieveUser(String username) throws AuthenticationException {
		CustomUserDetails loadedUser;
		try {
			loadedUser = getCustomUserDetailsService().loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}
		
		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException("CustomUserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}
	
	/**
	 * Copied from {@link AbstractUserDetailsAuthenticationProvider#authenticate(Authentication)}
	 * removed UserCache support
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage("CustomAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));
		
		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		
		UserDetails user = null;
		try {
			user = retrieveUser(username);
		} catch (UsernameNotFoundException notFound) {
			logger.debug("User '" + username + "' not found");
			
			if (hideUserNotFoundExceptions) {
				throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
			} else {
				throw notFound;
			}
		}
		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		
		getPreAuthenticationChecks().check(user);
		additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
		getPostAuthenticationChecks().check(user);
		
		Object principalToReturn = user;
		
		if (isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}
		
		return createSuccessAuthentication(principalToReturn, authentication, user);
	}
	
	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());
		
		return result;
	}
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	@Override
	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		super.setAuthoritiesMapper(authoritiesMapper);
		this.authoritiesMapper = authoritiesMapper;
	}
	
}
