package cn.codepure.jjwtdemo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;
import java.util.Date;

@SpringBootTest
class JjwtdemoApplicationTests {

    @Test
    public void testCreateToken() {
        // 1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 {"jti":"8888"}
                .setId("8888")
                // 主体用户 {"sub":"Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita":"xxxxx"}
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "xxxxxxx");
        // 2. 获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);

        System.out.println("================");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        // 签名无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

}
