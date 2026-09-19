package com.url.service;

import com.url.entity.Users;
import com.url.repository.UsersRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class ValidateService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean validateUserRegister(String email) {
        Users users = usersRepository.findByEmailOrderByCreateDateDesc(email);
        return users == null;
    }

    public String generateCustomerNo() {
        while (true) {
            byte[] bytes = new byte[16];
            SECURE_RANDOM.nextBytes(bytes);
            String customerNo = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            Users users = usersRepository.findByCustomerNoOrderByCreateDateDesc(customerNo);
            if (users == null) {
                return customerNo;
            }
        }
    }

    public String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        char[] hashPass = password.toCharArray();
        try {
            return argon2.hash(2, 65536, 1, password.toCharArray());
        }finally {
            argon2.wipeArray(hashPass);
        }
    }

    public boolean verifyPassword(String hashPass, String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        char[] passwordChar = password.toCharArray();

        try {
            boolean matched = argon2.verify(hashPass, passwordChar);
            if(!matched) {
                return false;
            }
        } finally {
            argon2.wipeArray(passwordChar);
        }
        return true;
    }
}
