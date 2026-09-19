package com.url.service;

import com.url.constant.ResponseCode;
import com.url.entity.Link;
import com.url.entity.Users;
import com.url.repository.LinkRepository;
import com.url.repository.UsersRepository;
import com.url.schemas.*;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShorternApiService {
    private static final Logger logger = LoggerFactory.getLogger(ShorternApiService.class);

    @Value("${com.url.http:http://localhost:8380/api/}")
    private String url;

    @Value("${com.url.charecter:8}")
    private int charecter;

    private ValidateService validateService;
    private TokenService tokenService;
    private UsersRepository usersRepository;
    private LinkRepository linkRepository;

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

    @Autowired
    public void setLinkRepository(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional(rollbackOn =  Exception.class)
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

    @Transactional(rollbackOn =  Exception.class)
    public ShorterRes shorter (String authorization, String longUrl) {
        ShorterRes res = new ShorterRes();
        ResponseCode rc;

        Claims claims = tokenService.verifyToken(authorization);
        String customerNo = claims.getSubject();

        String shortUrl = RandomStringUtils.secure().nextAlphabetic((charecter));

        Link link = linkRepository.findByLongUrlOrderByCreateDateDesc(longUrl);
        if(link == null) {
            Link linkToSave = new Link();
            linkToSave.setCustomerNo(customerNo);
            linkToSave.setLongUrl(longUrl);
            linkToSave.setShortUrl(shortUrl);
            linkToSave.setCreateDate(new Date());
            linkRepository.save(linkToSave);
            logger.info("save: {}", customerNo);
        }
        res.setShortUrl(url.concat(shortUrl));
        rc = ResponseCode.SUCCESS;
        res.setResponseCode(rc.code());
        res.setResponseDesc(rc.desc());

        return res;
    }

    public String originalUrl(String shortUrl) {
        Link link = linkRepository.findByShortUrlOrderByCreateDateDesc(shortUrl);
        if (link == null) {
            throw new RuntimeException("URL not found");
        }
        logger.info("url: {}, {}", link.getCustomerNo(), link.getLongUrl());
        return link.getLongUrl();
    }

    public UrlsRes getUrls(String authorization) {
        UrlsRes res = new UrlsRes();
        Claims claims = tokenService.verifyToken(authorization);
        String customerNo = claims.getSubject();
        logger.info("customerNo: {}", customerNo);

        List<Link> links = linkRepository.findByCustomerNoOrderByCreateDateDesc(customerNo);

        if (links.isEmpty()) {
            throw new RuntimeException("URL(s) not found");
        }

        List<LinkList> urls = new ArrayList<>();
        for(Link link : links) {
            LinkList linkList = new LinkList();
            linkList.setUrlId(link.getId());
            linkList.setShortUrl(url.concat(link.getShortUrl()));
            urls.add(linkList);
        }

        res.setResponseCode(ResponseCode.SUCCESS.code());
        res.setResponseDesc(ResponseCode.SUCCESS.desc());
        res.setUrls(urls);

        return res;
    }

    public GenericResponse deactivate(String authorization, Long id) {
        GenericResponse res = new GenericResponse();
        Claims claims = tokenService.verifyToken(authorization);
        String customerNo = claims.getSubject();
        logger.info("customerNo: {}", customerNo);

        List<Link> links = linkRepository.findByCustomerNoOrderByCreateDateDesc(customerNo);
        if (links.isEmpty()) {
            throw new RuntimeException("URL(s) not found");
        }

        linkRepository.deleteById(id);

        res.setResponseCode(ResponseCode.SUCCESS.code());
        res.setResponseDesc(ResponseCode.SUCCESS.desc());
        return res;
    }
}
