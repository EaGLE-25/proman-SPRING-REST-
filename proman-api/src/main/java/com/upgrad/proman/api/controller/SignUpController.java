package com.upgrad.proman.api.controller;


import com.upgrad.proman.api.model.SignupUserRequest;
import com.upgrad.proman.api.model.SignupUserResponse;
import com.upgrad.proman.api.createUser.CreateUserEntity;
import com.upgrad.proman.service.business.SignUpBussinessService;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignUpController{
    @Autowired
    private SignUpBussinessService signUpBussinessService;
    @Autowired
    private CreateUserEntity createUserEntity;

    @RequestMapping(value = "/signup",method = RequestMethod.POST,consumes =MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupUserResponse>signup (SignupUserRequest request){
//        UserEntity userEntity = new UserEntity();
//
//        userEntity.setUuid(UUID.randomUUID().toString());
//        userEntity.setFirstName(request.getFirstName());
//        userEntity.setLastName(request.getLastName());
//        userEntity.setEmail(request.getEmailAddress());
//        userEntity.setMobilePhone(request.getMobileNumber());
//        userEntity.setPassword(request.getPassword());
//        userEntity.setStatus(UserStatus.REGISTERED.getCode());
//        userEntity.setCreatedAt(ZonedDateTime.now());
//        userEntity.setCreatedBy("api-backend");

        UserEntity createdUserEntity = signUpBussinessService.signup(createUserEntity.createUser(request));
        SignupUserResponse response = new SignupUserResponse().id(createdUserEntity.getUuid()).status("REGISTERED");
        return new ResponseEntity<SignupUserResponse>(response,HttpStatus.CREATED);
    }
}
