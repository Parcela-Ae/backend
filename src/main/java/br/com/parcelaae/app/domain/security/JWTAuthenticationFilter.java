package br.com.parcelaae.app.domain.security;

import br.com.parcelaae.app.domain.security.model.CredentialsModel;
import br.com.parcelaae.app.domain.security.model.UserSS;
import br.com.parcelaae.app.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredentialsModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), CredentialsModel.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);
        }
        catch (IOException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication auth) throws IOException {
        var userSS = (UserSS) auth.getPrincipal();
        var token = jwtUtil.generateToken(userSS);
        fillAuthorizationInfo(response, userSS, token);
    }

    private void fillAuthorizationInfo(HttpServletResponse response, UserSS userSS, String token) throws IOException {
        var userDTO = userService.getUserProfile(userSS);
        userDTO.setAccessToken("Bearer " + token);
        String userJson = new Gson().toJson(userDTO);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(userJson);
    }
}
