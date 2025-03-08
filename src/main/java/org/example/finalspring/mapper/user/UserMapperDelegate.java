package org.example.finalspring.mapper.user;

import org.example.finalspring.entity.RoleType;
import org.example.finalspring.entity.User;
import org.example.finalspring.model.request.user.UserRequest;
import org.example.finalspring.model.response.user.UserResponse;
import org.example.finalspring.event.UserEvent;
import org.example.finalspring.entity.mongo.UsersRegistration;

import java.util.Set;

public class UserMapperDelegate implements UserMapper {
    @Override
    public User toUser(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    @Override
    public User toUser(UserRequest request, RoleType roleType) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .roles(Set.of(roleType))
                .build();
    }

    @Override
    public UserEvent toUserEvent(User user) {
        return UserEvent.builder()
                .userId(user.getId())
                .build();
    }

    @Override
    public UsersRegistration toUsersRegistration(UserEvent event) {
        return UsersRegistration.builder()
                .userId(event.getUserId())
                .build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
