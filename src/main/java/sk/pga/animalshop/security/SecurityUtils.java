package sk.pga.animalshop.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.NonNull;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtils {
	
	/**
	 * @return authentication of current user
	 */
	public static Optional<Authentication> getAuthenticationOptional() {
		// getContext is never null
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
	}
	
	public static Authentication getAuthentication() {
		return getAuthenticationOptional().orElse(null);
	}
	
	/**
	 * Method retrieves principal from Authentication and prints warning if principal exists but is not CustomUserDetails instance.
	 * 
	 * @param authentication
	 *            user authentication from which the returned principal will be retrieved
	 * @return authentication's principal
	 */
	public static CustomUserDetails getPrincipal(Authentication authentication) {
		if (authentication == null || authentication.getPrincipal() == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			return (CustomUserDetails) principal;
		} else {
			logger.warn("Principal should be CustomUserDetails (is " + principal.getClass() + ")");
			// if principal is not CustomUserDetails instance, treat it the same way as missing principal
			return null;
		}
	}
	
	/**
	 * Method uses {@link #getPrincipal(Authentication)} to get principal from authority
	 * 
	 * @return principal optional, if there is authentication, otherwise empty optional
	 */
	public static Optional<CustomUserDetails> getPrincipalOptional() {
		return getAuthenticationOptional().map(SecurityUtils::getPrincipal);
	}
	
	/**
	 * @return unfolded principal of {@link #getPrincipalOptional()}
	 */
	public static CustomUserDetails getPrincipal() {
		return getPrincipalOptional().orElse(null);
	}
	
	/**
	 * @return unfolded principal of {@link #getPrincipalOptional()} if exists, otherwise throws exception
	 */
	public static CustomUserDetails getPrincipalNonNull() throws NullPointerException {
		return getPrincipalOptional().orElseThrow(() -> new NullPointerException("Principal is not set"));
	}
	
	private static Collection<GrantedAuthority> getAuthorities(@NonNull Authentication authentication) {
		return Collections.unmodifiableCollection(authentication.getAuthorities());
	}
	
	public static Collection<GrantedAuthority> getAuthorities() {
		return getAuthorities(getAuthentication());
	}
	
	public static boolean hasAuthority(Authentication authentication, @NonNull String privilege) {
		return authentication != null && authentication.getAuthorities().stream().anyMatch(it -> it.getAuthority().equals(privilege));
	}
	
	public static boolean hasAuthority(@NonNull String privilege) {
		return hasAuthority(getAuthentication(), privilege);
	}
	
	public static boolean hasAnyAuthority(Authentication authentication, @NonNull Collection<String> privileges) {
		if (authentication == null || privileges.isEmpty())
			return false;
		val authorities = getAuthorities(authentication).stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
		return privileges.stream().anyMatch(authorities::contains);
	}
	
	public static boolean hasAnyAuthority(Authentication authentication, String... privileges) {
		return hasAnyAuthority(authentication, Arrays.asList(privileges));
	}
	
	public static boolean hasAnyAuthority(@NonNull Collection<String> privileges) {
		return hasAnyAuthority(getAuthentication(), privileges);
	}
	
	public static boolean hasAnyAuthority(String... privileges) {
		return hasAnyAuthority(Arrays.asList(privileges));
	}
}
