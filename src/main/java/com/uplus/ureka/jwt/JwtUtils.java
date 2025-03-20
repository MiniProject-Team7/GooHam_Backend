package com.uplus.ureka.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletRequest;  // 여기를 javax에서 jakarta로 변경
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final String secretKey = "dXJla2EtZnJvbnRlbmQtc2VjcmV0LWtleS0xMjM0NTY=";

    //accessToken 만료시간 설정
    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 1000L*60*60*12; //12시간
    public static final String AUTHORIZATION_HEADER = "Authorization"; //헤더 이름

    //액세스 토큰 생성 메서드
    public String createAccessToken(String member_id, String name){
        System.out.println("createAccessToken");

        // 토큰 만료 시간 설정(access token)
        Date now = new Date();
        Date expiration = new Date(now.getTime()+ ACCESS_TOKEN_VALIDATION_SECOND);

        // JWT 생성 AccessToken 생성하여 반환, member_id를 주체로 함
        return Jwts.builder()
                .setSubject(member_id) // 이 부분에서 "sub" 필드로 member_id를 설정
                .claim("name", name) // 이 부분에서 "name" 필드로 name 값을 설정
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰 유효성 검증 메서드
    public boolean validateToken(String token){
        //토큰 파싱 후 발생하는 예외를 캐치하여 문제가 있으면 false, 정상이면 true 반환
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e){
            // 서명이 옳지 않을 때
            System.out.println("잘못된 토큰 서명입니다.");
        }
        catch (ExpiredJwtException e){
            // 토큰이 만료됐을 때
            System.out.println("만료된 토큰입니다.");
        }
        catch(IllegalArgumentException | MalformedJwtException e){
            // 토큰이 올바르게 구성되지 않았을 때 처리
            System.out.println("잘못된 토큰입니다.");
        }
        return false;
    }

    // 토큰에서 member_id를 추출하여 반환하는 메소드
    public String getId(String token){
        System.out.println("getId");

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 name을 추출하여 반환하는 메소드
    public String getName(String token){
        System.out.println("getName");

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("name").toString();
    }

    // HttpServletRequest에서 Authorization Header를 통해 access token을 추출하는 메서드입니다.
    public String getAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String determineRedirectURI(HttpServletRequest httpServletRequest, String memberURI, String nonMemberURI) {
        String token = getAccessToken(httpServletRequest);
        if (token == null) {
            return nonMemberURI; // 비회원용 URI로 리다이렉트
        } else {
            return memberURI; // 회원용 URI로 리다이렉트
        }
    }
}