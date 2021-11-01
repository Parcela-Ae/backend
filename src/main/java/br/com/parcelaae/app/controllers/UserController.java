package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.user.model.UserProfileApiResponse;
import br.com.parcelaae.app.domain.security.SecurityUtil;
import br.com.parcelaae.app.domain.security.model.UserSS;
import br.com.parcelaae.app.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileApiResponse> getUserProfile() {
        UserSS userSS = SecurityUtil.getUserSS();
        var dto = userService.getUserProfile(userSS);
        return ResponseEntity.ok(dto);
    }
}
