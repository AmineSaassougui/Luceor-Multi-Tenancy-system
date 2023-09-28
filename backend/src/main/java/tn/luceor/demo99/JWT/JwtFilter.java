package tn.luceor.demo99.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerUsersDetailsServices service;
    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup|/router/get")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        else
        {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                userName = jwtUtils.extractUsername(token);
                claims = jwtUtils.extractAllClaims(token);
            }
            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = service.loadUserByUsername(userName);
                if(jwtUtils.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
    public boolean isAdmin(){
        return "ADMIN".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "USER".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isMember(){
        return "President_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Active_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Former_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Organizer_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "RightHand_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Writer_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Leader_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Helper_MEMBER".equalsIgnoreCase((String) claims.get("role")) ||
                "Technitial_MEMBER".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }

}
