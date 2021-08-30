package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.dto.UserProfileDTO;
import br.com.parcelaae.app.security.SecurityUtil;
import br.com.parcelaae.app.security.UserSS;
import br.com.parcelaae.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        UserSS userSS = SecurityUtil.getUserSS();
        var dto = userService.getUserProfile(userSS);
        return ResponseEntity.ok(dto);
    }
}
