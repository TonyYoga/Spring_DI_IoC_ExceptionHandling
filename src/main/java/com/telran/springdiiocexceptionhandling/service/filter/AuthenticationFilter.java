package com.telran.springdiiocexceptionhandling.service.filter;

import com.telran.springdiiocexceptionhandling.repository.UserRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.service.TokenService;
import com.telran.springdiiocexceptionhandling.service.UserCredentials;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Order(1)
public class AuthenticationFilter implements Filter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService validationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //TODO need to check getAllTopic Request
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        String method = request.getMethod();
        if (!method.equals("GET") && !path.startsWith("/user")) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                return;
            }
            UserCredentials userCredentials = null;
            try {
                userCredentials = validationService.decodeToken(token);
            } catch (Exception ex) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Wrong token format");
                return;
            }

            UserEntity user = userRepository.getUserByEmail(userCredentials.getEmail());

            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "User not found");
                return;
            }

            if (!user.getPassword().equals(userCredentials.getPassword())) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Wrong email of password");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
