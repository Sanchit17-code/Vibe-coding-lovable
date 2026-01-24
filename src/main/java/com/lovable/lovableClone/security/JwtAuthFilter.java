package com.lovable.lovableClone.security;

import com.lovable.lovableClone.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURI());
        final String requestHeaderToken = request.getHeader("Authorization");
        if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = requestHeaderToken.split("Bearer ")[1];
        JwtUserPrincipal user = authUtil.verifyAccessToken(jwtToken); // this is done to avoid the DB call to get the user... This is enough for our use case
        if(user != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null,user.authorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
