package org.example.finalspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.RoleType;
import org.example.finalspring.entity.User;
import org.example.finalspring.mapper.user.UserMapper;
import org.example.finalspring.model.request.user.UserRequest;
import org.example.finalspring.model.response.user.UserResponse;
import org.example.finalspring.model.response.user.UserResponseList;
import org.example.finalspring.service.UserService;
import org.example.finalspring.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    public ResponseEntity<UserResponseList> getAll() {
        List<UserResponse> users = userService.getAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(UserResponseList.builder()
                        .users(users)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toUserResponse(userService.findById(id)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request,
                                                 @RequestParam RoleType roleType) {
        User user = userService.register(userMapper.toUser(request, roleType));

        kafkaProducerService.sendUserRegistrationEvent(userMapper.toUserEvent(user));

        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request) {
        User user = userService.update(id, userMapper.toUser(request));
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
