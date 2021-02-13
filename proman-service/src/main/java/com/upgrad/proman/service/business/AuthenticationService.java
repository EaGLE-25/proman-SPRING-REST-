package com.upgrad.proman.service.business;


import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.proman.service.business.JwtTokenProvider;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity login(String email,String password) throws AuthenticationFailedException {
       UserEntity user = userDao.getUserByEmail(email);

       if(user == null){
           throw new AuthenticationFailedException("ATH-000","User with email not found");
       }
       String encryptedPassword = cryptographyProvider.encrypt(password,user.getSalt());

       if(encryptedPassword.equals(user.getPassword())){
           JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
           UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
           userAuthToken.setUser(user);

           final ZonedDateTime now = ZonedDateTime.now();
           final ZonedDateTime expiresAt = now.plusHours(8);
           userAuthToken.setAccessToken(jwtTokenProvider.generateToken(user.getUuid(), now, expiresAt));

           userAuthToken.setLoginAt(now);
           userAuthToken.setExpiresAt(expiresAt);
           userAuthToken.setCreatedBy("api-backend");
           userAuthToken.setCreatedAt(now);

           user.setLastLoginAt(now);
           userDao.persistToken(userAuthToken);
           userDao.updateUser(user);

           return userAuthToken;
       }else{
           throw new AuthenticationFailedException("ATH-001","Password does not match");
       }
    }
}
