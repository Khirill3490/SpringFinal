package org.example.finalspring.mapper.user;


import org.example.finalspring.entity.RoleType;
import org.example.finalspring.entity.User;
import org.example.finalspring.model.request.user.UserRequest;
import org.example.finalspring.model.response.user.UserResponse;
import org.example.finalspring.event.UserEvent;
import org.example.finalspring.entity.mongo.UsersRegistration;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(UserRequest request);

    User toUser(UserRequest request, RoleType roleType);

    UserEvent toUserEvent(User user);

    UsersRegistration toUsersRegistration(UserEvent event);


    UserResponse toUserResponse(User user);
}
