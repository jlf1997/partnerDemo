package com.yunyij.partner.config;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 * 
 * @author vladimir.stankovic
 *
 * May 31, 2016
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
    
    
    private Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(settings.getTokenSigningKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }
    
    
  //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public String createJavaWebToken(Map<String, Object> claims) {
    	JwtBuilder jwtBuilder= Jwts.builder();
    	jwtBuilder.setClaims(claims);
    	long nowMillis = System.currentTimeMillis();
    	long expMillis = nowMillis + settings.getTokenExpirationTime()*60*1000;//设置token一天过期
    	jwtBuilder.setExpiration(new Date(expMillis));
    	jwtBuilder.setIssuer(settings.getTokenIssuer());
    	jwtBuilder.setIssuedAt(new Date(nowMillis));
    	jwtBuilder.setId(UUID.randomUUID().toString());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    //解析Token，同时也能验证Token，当验证失败返回null
    public Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            return null;
        }
    }
    
}
