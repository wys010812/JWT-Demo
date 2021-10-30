package cn.codepure.jjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.SimpleTimeZone;

@SpringBootTest
class JjwtdemoApplicationTests {

    /**
     * 创建token
     */
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

    /**
     * 解析token
     */
    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTYzNTYwNTYzNH0.hSlCKML0jpx_76O5xUd6j4-Cwu1ffrv27L2K6J6-5mQ";
        // 解析Token 获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxxxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
    }

    /**
     * 创建token （失效时间）
     */
    @Test
    public void testCreateTokenHasExp() {
        // 当前系统时间
        long now = System.currentTimeMillis();
        // 过期时间 获取时间为毫秒
        long exp = now + 60 * 1000;
        // 1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 {"jti":"8888"}
                .setId("8888")
                // 主体用户 {"sub":"Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita":"xxxxx"}
                .setIssuedAt(new Date())
                // 设置加密算法和盐
                .signWith(SignatureAlgorithm.HS256, "xxxxxxx")
                // 设置失效时间
                .setExpiration(new Date(exp));
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

    /**
     * 解析token （失效时间）
     */
    @Test
    public void testParseTokenHasExp() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTYzNTYwNzUzNSwiZXhwIjoxNjM1NjA3NTk1fQ.IM-9uPea_7g7UzBk6Gq-2UwJcekGdKVrz7J75IEpEsk";
        // 解析Token 获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxxxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间："+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间："+simpleDateFormat.format(claims.getExpiration()));
        System.out.println("现在时间："+simpleDateFormat.format(new Date()));
    }

    /**
     * 创建token （自定义声明）
     */
    @Test
    public void testCreateTokenByClaims() {
        // 当前系统时间
        long now = System.currentTimeMillis();
        // 过期时间 获取时间为毫秒
        long exp = now + 60 * 1000;
        // 1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 {"jti":"8888"}
                .setId("8888")
                // 主体用户 {"sub":"Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita":"xxxxx"}
                .setIssuedAt(new Date())
                // 设置加密算法和盐
                .signWith(SignatureAlgorithm.HS256, "xxxxxxx")
                // 自定义声明
                .claim("roles", "admin")
                .claim("logo", "xxx.jpg");
                // 还可以使用Map声明
                // .addClaims()
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

    /**
     * 解析token （自定义声明）
     */
    @Test
    public void testParseTokenByClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTYzNTYwNzk5NCwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJ4eHguanBnIn0.LMl3plwxlZMwuj6NITxNGocVQp_7vtrxxXGyKLGxbvE";
        // 解析Token 获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxxxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());
        System.out.println("issuedAt:" + claims.getIssuedAt());
        System.out.println("Roles:" + claims.get("roles"));
        System.out.println("LOGO:" + claims.get("logo"));
    }
}
