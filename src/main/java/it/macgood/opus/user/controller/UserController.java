package it.macgood.opus.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.macgood.opus.user.model.User;
import it.macgood.opus.user.service.UserService;
import it.macgood.opus.views.View;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @JsonView(View.GetProfileInfo.class)
    @GetMapping
    public ResponseEntity<User> profile(
            Principal principal
    ) {

        User user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok(user);
    }

    @JsonView(View.GetShortInfo.class)
    @GetMapping("/short")
    public ResponseEntity<User> getUser(
            Principal principal
    ) {

        User user = userService.findByEmail(principal.getName());

        return ResponseEntity.ok(user);
    }

    @JsonView(View.GetProfileInfo.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable String id
    ) {

        User user = userService.findById(Integer.parseInt(id));

        return ResponseEntity.ok(user);
    }

    @JsonView(View.GetShortInfo.class)
    @GetMapping("/{id}/short")
    public ResponseEntity<User> getUserShortById(
            @PathVariable String id
    ) {

        User user = userService.findById(Integer.parseInt(id));

        return ResponseEntity.ok(user);
    }

}
