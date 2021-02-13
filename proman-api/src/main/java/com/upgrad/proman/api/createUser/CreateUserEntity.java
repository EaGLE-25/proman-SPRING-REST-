package com.upgrad.proman.api.createUser;


import com.upgrad.proman.api.model.CreateUserRequest;
import com.upgrad.proman.api.model.SignupUserRequest;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CreateUserEntity{
    public UserEntity createUser(SignupUserRequest request){
        UserEntity userEntity = new UserEntity();

        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmailAddress());
        userEntity.setMobilePhone(request.getMobileNumber());
        userEntity.setPassword(request.getPassword());
        userEntity.setStatus(UserStatus.REGISTERED.getCode());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");

        return userEntity;
    }

    public UserEntity createUser(CreateUserRequest request){
        UserEntity userEntity = new UserEntity();

        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmailAddress());
        userEntity.setMobilePhone(request.getMobileNumber());
        userEntity.setStatus(UserStatus.ACTIVE.getCode());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");

        return userEntity;
    }
}
