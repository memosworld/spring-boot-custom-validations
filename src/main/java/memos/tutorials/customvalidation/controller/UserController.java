package memos.tutorials.customvalidation.controller;

import jakarta.validation.Valid;
import memos.tutorials.customvalidation.controller.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation-examples")
public class UserController {

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.noContent()
                             .build();
    }
}
