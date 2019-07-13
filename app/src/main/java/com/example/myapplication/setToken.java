package com.example.myapplication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Проверить метод, возможно подойдёт
//Найти как вернуть в php
public class setToken {
    String setsToken(String login,String pass){
        String tok = login + (Math.random()*10000)+pass;

        Date R = new Date();
        R.setTime(R.getTime() + 259200000);
        String S = null;
        try {
            S = Jwts.builder().setSubject(tok).setId("Andrei").setIssuedAt(new Date()).setExpiration(R).signWith(SignatureAlgorithm.HS256,"solust".getBytes("UTF-8")).compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return S;
    }
}
