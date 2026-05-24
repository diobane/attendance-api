package com.attendance_api.api.controller;

import com.attendance_api.api.dto.UserRequestDTO;
import com.attendance_api.api.dto.UserResponseDTO;
import com.attendance_api.core.dto.RoleOption;
import com.attendance_api.core.util.JwtUtil;
import com.attendance_api.domain.entity.User;
import com.attendance_api.domain.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsernameAndActiveTrue(request.username());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.password(), userOpt.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role",  user.getRole().name(),
                "username", user.getUsername()
        ));
    }

    @GetMapping("/users")
    @RolesAllowed({RoleOption.ADMIN})
    public ResponseEntity<List<UserResponseDTO>> listUsers() {
        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/user/create")
    @RolesAllowed({RoleOption.ADMIN})
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "This username is already in use"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User saved = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(saved));
    }

    @DeleteMapping("/user/remove/{id}")
    @RolesAllowed({RoleOption.ADMIN})
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank @Size(min = 8) String password
    ) {}
}
