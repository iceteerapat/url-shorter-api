package com.url.controller;

import com.url.constant.ResponseCode;
import com.url.schemas.*;
import com.url.service.ShorternApiService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ShorternApiController {

    private ShorternApiService  shorternApiService;

    @Autowired
    public void setShorternApiService(ShorternApiService shorternApiService) {
        this.shorternApiService = shorternApiService;
    }

    @PostMapping("/shorter")
    public ResponseEntity<ShorterRes> perform(@RequestHeader(value = "Authorization") String authorization, @RequestBody ShorterReq req) {
        ShorterRes res = new ShorterRes();
        if(StringUtils.isBlank(req.getLongUrl())){
            ResponseCode rc = ResponseCode.BAD_REQUEST;
            res.setResponseCode(rc.code());
            res.setResponseDesc(rc.desc());
            return ResponseEntity.badRequest().body(res);
        }

        res = shorternApiService.shorter(authorization, req.getLongUrl());

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = shorternApiService.originalUrl(shortUrl);
        response.sendRedirect(originalUrl);
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
    public ResponseEntity<UrlsRes> urls(@RequestHeader(value = "Authorization") String authorization){
        UrlsRes res = new UrlsRes();

        if(StringUtils.isBlank(authorization)){
            ResponseCode rc = ResponseCode.BAD_REQUEST;
            res.setResponseCode(rc.code());
            res.setResponseDesc(rc.desc());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        res = shorternApiService.getUrls(authorization);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/urls/{id}")
    public ResponseEntity<GenericResponse> deactivate(@RequestHeader(value = "Authorization") String authorization, @PathVariable Long id){
        GenericResponse res = new GenericResponse();
        if(StringUtils.isBlank(authorization)){
            ResponseCode rc = ResponseCode.BAD_REQUEST;
            res.setResponseCode(rc.code());
            res.setResponseDesc(rc.desc());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        res = shorternApiService.deactivate(authorization, id);
        return ResponseEntity.ok(res);
    }
}
