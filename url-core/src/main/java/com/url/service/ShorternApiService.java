package com.url.service;

import com.url.constant.ResponseCode;
import com.url.entity.Users;
import com.url.repository.UsersRepository;
import com.url.schemas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShorternApiService {

    private ValidateService validateService;
    private TokenService tokenService;
    private UsersRepository usersRepository;

    @Autowired
    public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public RegisterationRes register(RegisterationReq req) {
        RegisterationRes res = new RegisterationRes();
        ResponseCode rc;

        String customerNo = validateService.generateCustomerNo();
        boolean validateUsers = validateService.validateUserRegister(req.getEmail().toLowerCase());
        if (validateUsers) {
            Users users = new Users();
            users.setCustomerNo(customerNo);
            users.setEmail(req.getEmail().toLowerCase());
            users.setPassword(validateService.hashPassword(req.getPassword()));
            users.setCreateDate(new Date());
            usersRepository.save(users);

            rc = ResponseCode.SUCCESS;
        } else {
            rc = ResponseCode.DUPLICATE_USER;
        }
        res.setResponseCode(rc.code());
        res.setResponseDesc(rc.desc());

        return res;
    }

    public LoginRes login(LoginReq req) {
        LoginRes res = new LoginRes();
        ResponseCode rc;

        Users users = usersRepository.findByEmailOrderByCreateDateDesc(req.getEmail().toLowerCase());
        boolean verPass = validateService.verifyPassword(users.getPassword(), req.getPassword());
        if (verPass) {
            String jwts = tokenService.generateToken(users.getCustomerNo());
            res.setTokenJwts(jwts);
            rc = ResponseCode.SUCCESS;
        } else {
            rc = ResponseCode.INVALID_PASSWORD;
        }
        res.setResponseCode(rc.code());
        res.setResponseDesc(rc.desc());
        return res;
    }

    public ShorterRes shorter (String longUrl) {

        return null;
    }
}
