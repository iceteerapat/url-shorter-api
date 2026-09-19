package com.url.controller;

import com.url.constant.ResponseCode;
import com.url.schemas.*;
import com.url.service.ShorternApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShorternApiController {

    private ShorternApiService  shorternApiService;

    @Autowired
    public void setShorternApiService(ShorternApiService shorternApiService) {
        this.shorternApiService = shorternApiService;
    }

    @PostMapping("/shortern")
    public void perform(@RequestBody ShorterReq req) {
        
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterationRes> register(@RequestBody RegisterationReq req){
        RegisterationRes res = new RegisterationRes();

        if(StringUtils.isBlank(req.getEmail()) || StringUtils.isBlank(req.getPassword())){
            ResponseCode rc = ResponseCode.BAD_REQUEST;
            res.setResponseCode(rc.code());
            res.setResponseDesc(rc.desc());
            return ResponseEntity.badRequest().body(res);
        }

        res = shorternApiService.register(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req){
        LoginRes res = new LoginRes();
        if(StringUtils.isBlank(req.getEmail()) || StringUtils.isBlank(req.getPassword())){
            ResponseCode rc = ResponseCode.BAD_REQUEST;
            res.setResponseCode(rc.code());
            res.setResponseDesc(rc.desc());
            return ResponseEntity.badRequest().body(res);
        }

        res = shorternApiService.login(req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/urls")
    public void urls(){

    }

    @DeleteMapping("/urls/{id}")
    public void delete(@PathVariable String id){

    }
}
