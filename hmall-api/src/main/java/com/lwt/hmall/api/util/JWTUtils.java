package com.lwt.hmall.api.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lwt
 * @Date 2020/2/5 17:53
 * @Description
 */
public class JWTUtils {

    public static String createToken(String key, String salt, Map<String,Object> body,long validityPeriod){
        String secret = getSecret(key, salt);

        if (body==null){
            body=new HashMap<>();
        }

        for (String k : body.keySet()) {
            Object v = body.get(k);
            body.put(k,JSON.toJSONString(v));
        }

        long iat = System.currentTimeMillis();
        long exp = iat+validityPeriod;
        body.put("iat",iat);
        body.put("exp",exp);

        String token = Jwts.builder()
                .setClaims(body)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    private static String getSecret(String key, String salt) {
        String secret = DigestUtils.md5Hex(key + salt);
        return secret;
    }

    public static String createToken(String key, String salt, Map<String,Object> body){
        //有效时间1周 604800000
        return createToken(key,salt,body,604800000);
    }

    public static Map<String,Object> verifyToken(String key, String salt, String token){
        try {
            String secret = getSecret(key, salt);
            Map<String,Object> body= (Map<String, Object>) Jwts.parser().setSigningKey(secret).parse(token).getBody();
            long timeMillis = System.currentTimeMillis();
            long exp = (Long) body.get("exp");
            if (timeMillis<exp) {
                return body;
            }
        }catch (Exception e){
        }
        return null;
    }

    public static Map<String,Object> getBody(String token){
        try {
            String bodyEnc = token.split("\\.")[1];
            String bodyDec = new String(Base64.decodeBase64(bodyEnc));
            Map<String,Object> body= (Map<String, Object>) JSON.parse(bodyDec);
            return body;
        }catch (Exception e){
        }
        return null;
    }

}
