package it.macgood.opus.user.controller;

import it.macgood.opus.user.model.Role;
import it.macgood.opus.user.model.User;
import it.macgood.opus.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/edit")
@RequiredArgsConstructor
public class EditController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> updatePhoto(
            Authentication auth,
            @RequestParam("photo") MultipartFile photo
    ) throws IOException {

        User user = (User) auth.getPrincipal();
        userService.updatePhoto(user.getId(), user.getEmail(), photo);

        return ResponseEntity.ok("Updated");
    }

    @PutMapping
    public ResponseEntity<String> edit(
            Principal principal,
            HttpServletRequest request,
            @RequestBody User user
    ) {

        user.setRole(Role.USER);

        if (userService.findByEmail(principal.getName()).getEmail().equals(user.getEmail())) {

            userService.updateUser(user);

            return ResponseEntity.ok("Successful");
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
