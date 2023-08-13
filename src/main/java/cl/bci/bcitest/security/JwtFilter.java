package cl.bci.bcitest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserSecurityService userDetailsService;

  @Autowired
  public JwtFilter(JwtUtil jwtUtil, UserSecurityService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authorizationHeader.split(" ")[1].trim();
    final String id = jwtUtil.getIdFromToken(jwt);
    if (!jwtUtil.isTokenValid(jwt)) {
      filterChain.doFilter(request, response);
      return;
    }
    final User user = (User) userDetailsService.loadUserByUsername(id);
    final UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            user.getUsername(), user.getPassword(), Collections.emptyList());

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
