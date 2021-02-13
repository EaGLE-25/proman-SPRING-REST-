package com.upgrad.proman.api.controller;


import com.upgrad.proman.api.model.CreateUserRequest;
import com.upgrad.proman.api.model.CreateUserResponse;
import com.upgrad.proman.api.model.UserDetailsResponse;
import com.upgrad.proman.api.model.UserStatusType;
import com.upgrad.proman.api.createUser.CreateUserEntity;
import com.upgrad.proman.service.business.UserAdminBussinesService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnauthorizedException;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class UserAdminController {
    @Autowired
    UserAdminBussinesService userAdminBusinessService;
    @Autowired
    private CreateUserEntity createUserEntity;

    @RequestMapping(method = RequestMethod.GET,value = "/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsResponse>getUser(@PathVariable("id") String uuid,@RequestHeader("authorization")String authorization) throws ResourceNotFoundException, UnauthorizedException {
        final UserEntity userEntity = userAdminBusinessService.getUser(uuid,authorization);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().id(userEntity.getUuid()).firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                .mobileNumber(userEntity.getMobilePhone()).status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest userRequest){
//       UserEntity userEntity = new UserEntity();
//
//       userEntity.setUuid(UUID.randomUUID().toString());
//       userEntity.setFirstName(userRequest.getFirstName());
//       userEntity.setLastName(userRequest.getLastName());
//       userEntity.setEmail(userRequest.getEmailAddress());
//       userEntity.setMobilePhone(userRequest.getMobileNumber());
//       userEntity.setStatus(UserStatus.ACTIVE.getCode());
//       userEntity.setCreatedAt(ZonedDateTime.now());
//       userEntity.setCreatedBy("api-backend");

       UserEntity persistedUser = userAdminBusinessService.createUser(createUserEntity.createUser(userRequest));

       CreateUserResponse response = new CreateUserResponse();
       response.id(persistedUser.getUuid());
       response.status(UserStatusType.ACTIVE);

       return new ResponseEntity<CreateUserResponse>(response,HttpStatus.CREATED);
    }
}
