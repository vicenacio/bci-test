package cl.bci.bcitest.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
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
    final String requestedUri = request.getRequestURI();
    if (requestedUri.equals("/auth/login")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    final String jwt = authorizationHeader.split(" ")[1].trim();

    try {
      if (jwtUtil.isTokenExpired(jwt)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      final String id = jwtUtil.getIdFromToken(jwt);
      if (!jwtUtil.isTokenValid(jwt)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        filterChain.doFilter(request, response);
        return;
      }
      final User user = (User) userDetailsService.loadUserByUsername(id);
      final UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(
              user.getUsername(), user.getPassword(), Collections.emptyList());

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      filterChain.doFilter(request, response);
    } catch (TokenExpiredException | SignatureVerificationException | AccessDeniedException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
