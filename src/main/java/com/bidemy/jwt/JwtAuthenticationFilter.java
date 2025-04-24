package com.bidemy.jwt;

import com.bidemy.model.entity.User;
import com.bidemy.repository.UserRepository; // userRepository'yi ekledik
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header;
        String token;
        String username;

        header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = header.substring(7);

        try {
            username = jwtService.getUsernameByToken(token);
            System.out.println("Username: " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Collection<GrantedAuthority> authorities = new ArrayList<>();

                if ("ADMIN".equals(user.getRole())) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                UserDetails updatedUserDetails = new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), authorities);

                boolean isExpired = jwtService.isTokenExpired(token);
                System.out.println("Token expired: " + isExpired);

                if (updatedUserDetails != null && !isExpired) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            updatedUserDetails, null, updatedUserDetails.getAuthorities());
                    authentication.setDetails(updatedUserDetails);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Authenticated: " + SecurityContextHolder.getContext().getAuthentication());
                }
            }

        } catch (ExpiredJwtException e) {
            System.out.println("Token'in süresi dolmuş: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Genel bir hata oluştu: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
