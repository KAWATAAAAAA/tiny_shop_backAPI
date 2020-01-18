package cn.wuyuwei.tiny_shop.utils;

import cn.wuyuwei.tiny_shop.entity.RSA256Key;
import cn.wuyuwei.tiny_shop.entity.UserInfo;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.*;




public class JwtUtils {
    private static final String ISSUER = "WUYUWEI_BACK_API";

    /*-----------------------------------------Using RS256-------------------------------------------------------*/
    /*获取签发的token，返回给前端*/
    public static String generTokenByRS256(Long id) throws Exception{

        RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); // 获取公钥/私钥
        Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());

        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(SecretKeyUtils.getPublicKey(rsa256Key));
        System.out.println("-----END PUBLIC KEY-----");
        // 返回token
        return createToken(algorithm, id);

    }

    /*签发token*/
    public static String createToken(Algorithm algorithm,Object data) throws Exception {


        return JWT.create()
                .withIssuer(ISSUER)   //发布者
                .withNotBefore(new Date())  //生效时间
                .withExpiresAt(DateUtils.offset(new Date(),2, Calendar.HOUR))    // 生成签名的有效期
                .withClaim("userid", JSON.toJSONString(data)) //存数据
                .withJWTId(UUID.randomUUID().toString())    //编号
                .sign(algorithm);
    }

    /*验证token*/

    public static DecodedJWT verifierToken(String token)throws Exception{

            RSA256Key rsa256Key = SecretKeyUtils.getRSA256Key(); // 获取公钥/私钥

            //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
            Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance 可复用的验证实例
            DecodedJWT jwt = verifier.verify(token);


            return jwt;

    }

    /*------------------------------------------Using HS256-------------------------------------------------------*/

}
