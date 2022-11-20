package com.nhk.thesis.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;

public class TokenAuthenticationService {
    static final int EXPIRATIONTIME = 5*24*60*60*1000; // 5 days

    static final String SECRET = "i0+YpXJ4Jg4gRX3Um3mmzEZA6MZL9P45hScnAOQRWJYT4TidQddUNHLL2stWwaKOvERv8VVbuJn8";

    static final String TOKEN_PREFIX = "Bearer";

    static final String AUD = "user";

    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username, Collection<?> list) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        String JWT = Jwts.builder().claim("role", list)
                .setSubject(username)
                .setAudience(AUD)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

//        ResponseCookie.ResponseCookieBuilder authBuilder = ResponseCookie.from(HEADER_STRING,TOKEN_PREFIX+"_"+JWT);
//        authBuilder.maxAge(24*2*60*60);
//        authBuilder.sameSite("None");
//        authBuilder.httpOnly(true);
//        authBuilder.secure(true);
//
//        ResponseCookie auth = authBuilder.build();


    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

//        String token = null;

        if(token != null && !token.equals("")) {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            String user = Jwts.parserBuilder().setSigningKey(key).requireAudience(AUD)
                    .build().parseClaimsJws(token.replace(TOKEN_PREFIX+" ", ""))
                    .getBody().getSubject();
            return (user != null)? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()): null;
        }

        return null;

//        if (token != null) {
//            // parse the token.
//            LinkedHashMap<String,String> list = (LinkedHashMap<String, String>) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                    .getBody().get("role", Collection.class).toArray()[0];
//            ArrayList<GrantedAuthority> role = new ArrayList<>();
//            role.add(new SimpleGrantedAuthority(list.get("authority")));
//            String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
//                    .getSubject();
//            return user != null ? new UsernamePasswordAuthenticationToken(user, null, role) : null;
//        }
//        return null;
    }
}
