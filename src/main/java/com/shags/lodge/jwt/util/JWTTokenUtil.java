package com.shags.lodge.jwt.util;

import com.shags.lodge.config.JWTConfig;
import com.shags.lodge.dto.User;
import com.shags.lodge.exception.exception.JsonException;
import groovy.util.logging.Slf4j;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * JWT工具类
 *
 * @Author 雷智荣
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil() {
    }

    /**
     * 生成Token
     *
     * @Author 雷智荣
     * @Param user 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(User user) {
        // 登陆成功生成JWT
        try {
            return Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS512")
                    // 用户ID
                    .setId(user.getId())
                    // 用户帐号
                    .setSubject(user.getAccount())
                    // 签发时间
                    .setIssuedAt(new Date())
                    // 签发者
                    .setIssuer("JtCorp")
                    // 自定义属性 放入用户拥有权限
                    .claim("bookSet", user.getBookSet())
                    // 失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                    // 签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                    .compact();
        } catch (Exception e) {
            throw new JsonException(500, e.getMessage());
        }
    }

    /**
     * 获取用户账号
     *
     * @param httpServletRequest
     * @param jwtTokenCookieName
     * @param signingKey
     * @return
     */
    public static String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
        String token = CookieUtil.getCookie(httpServletRequest, jwtTokenCookieName);
        if (token == null) return null;
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 获取token参数
     *
     * @param httpServletRequest
     * @param jwtTokenCookieName
     * @param signingKey
     * @return
     */
    public static Claims getClaims(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
        String token = CookieUtil.getCookie(httpServletRequest, jwtTokenCookieName);
        if (token == null) return null;
        return Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
    }

    /**
     * 检测时效
     *
     * @param httpServletRequest
     * @param jwtTokenCookieName
     * @param signingKey
     * @return
     */
    public static boolean isTokenExpiration(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
        String token = CookieUtil.getCookie(httpServletRequest, jwtTokenCookieName);
        if(token==null)
            return true;
         Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
        if (null == claims) {
            return true;
        }
        if (null == claims.getExpiration()) {
            return true;
        } else {
            Date expiration = claims.getExpiration();
             return expiration.before(new Date());
        }

    }

    /**
     * 获取header token
     * @return
     */
    public static String getCurrentToken() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getHeader("Token");
    }
}
