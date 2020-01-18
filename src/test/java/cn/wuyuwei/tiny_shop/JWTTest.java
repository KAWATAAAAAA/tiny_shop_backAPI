package cn.wuyuwei.tiny_shop;

import cn.wuyuwei.tiny_shop.common.ApiException;
import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.utils.DateUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTTest {

    @Test
    public void creatToken(){
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")    // 发布者
                    .withIssuedAt(new Date())   // 生成签名的时间
                    .withExpiresAt(DateUtils.offset(new Date(),2, Calendar.HOUR))    // 生成签名的有效期,分钟
                    .withClaim("name","wuyuwei")
                    .sign(algorithm);

            System.out.println(token);
        }catch(JWTCreationException e){
            e.printStackTrace();
            //如果Claim不能转换为JSON，或者在签名过程中使用的密钥无效，那么将会抛出JWTCreationException异常。
        }


    }
    @Test
    public void verifierToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJ3dXl1d2VpIiwiZXhwIjoxNTc4NzUyNzEzLCJpYXQiOjE1Nzg3NDU1MTN9.LlC_jiUJI1pe7uEDdmQz4JoL4Qyee3kSY_RWN2ibZmo";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("exp");

            System.out.println(claim.asString());
            System.out.println(claim.asDate());


        } catch (TokenExpiredException e){
            throw new ApiException(ApiResultEnum.TOKEN_EXPIRED);
        } catch (JWTVerificationException e){
                //无效的签名/声明
            System.out.println("666");
            e.printStackTrace();
            throw new ApiException(ApiResultEnum.SIGN_VERIFI_ERROR );
        }
    }

    @Test
    public void decodeToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIm5hbWUiOiJ3dXl1d2VpIiwiZXhwIjoxNTc4NzUyNzEzLCJpYXQiOjE1Nzg3NDU1MTN9.LlC_jiUJI1pe7uEDdmQz4JoL4Qyee3kSY_RWN2ibZmo";
        try {
            DecodedJWT jwt = JWT.decode(token);

            String algorithm = jwt.getAlgorithm();
            String issuer = jwt.getIssuer();
            Date expiresAt = jwt.getExpiresAt();
            Date issuedAt = jwt.getIssuedAt();
            String type = jwt.getType();


            System.out.println(algorithm);
            System.out.println(issuer);
            System.out.println(expiresAt);
            System.out.println(issuedAt);
            System.out.println(type);

        } catch (JWTDecodeException exception){
            //无效的 token
        }
    }


}
