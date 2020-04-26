package cn.wuyuwei.tiny_shop.config;


import java.io.FileWriter;
import java.io.IOException;

public class AliPayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2016102400752742"; //测试
    //public static String APP_ID = "2021001157687004";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCx+QqFQucd5fGLiQGRxUtsTPd1H6dRrghZxO7m1+yExLs2oGg+q6WJ1CxogUbDszQ/AslzrrQnGQ4a6Vcnkg3yZStYM9CmQMZk8yfTrMZbLnglUMIF6IHKYh17V8EoIP9sqPi+cZ/0hxOX6qG5tmA9JxGsSUQqXRp0LZYurYaio04w/fayx9tuGGuGgYieih4cOnnqB2uq6tx7VV/LOpFrF1WHrgbrffxaDqsS+1Km2itN05cP24HSkRg89SQ5Po4zxDt9RzmBzzixl3Nc6Hi0GhsjKIlD4Z6QbmiGC6/VtwdmZLncZrUcKAyyqg9UbJgZtvAuOYRsvHlKLUJkgyh5AgMBAAECggEAJU/9MKsyIUaQOZzjw1gF1BLe3qNgRPOjtI5hN+yVHdDRH0bKFDmQXzEojlBsMaNklMFU0vgRtRC/sEyzhQxN+D/VsoxKdlCmR4vjEbHrdDpt3gTsaKiybWfcnntrd6pTYsiaPfM8gdu/892xQmpGCwwh3GkP7YHPhgP4tJiU3YvdyYWXtLGpraUk6VWRHOfewKmSoS1Lrl2/yX3HZt0dD4cp8jFl93pgNRS2AJ6+SdhQVABKkWmTeDSTR33g2Ri/Zwgrn/NaONZ5Mfjvguwd1wJJTv3lhhKMUcOr2pO32IiZXShsf+VGMA6ZJCYOqoRAV1FXMlG+cGJJZ63f1P2F8QKBgQDeQe1AAf3I/MX4P+KjDG4e+OLKjFEgu0sUXzvtt9Hi0RXa/dc2Tu3YfggZdjH0UcD7QCUns4qmtqXFU0q8Z3lG+Ny8eJtSKOcwvozcQbb5X9kbOAsoCov525eJSIdlxqbGXI19XQGr2kHnZFz7egr1mN306bJU9HMIIRPhq+NCtwKBgQDM/ftzWaRRNQ093k+0HQJdoNb9fls0b2lDKvnBSTUvclpvtO2K6Z4VHP778ATvkw5qU9/Mabpenat2wks6c50GPykHxd1GSoJ8zJ7L8s6C7DZDCxoENwXa4bRpEr8Lv+hYx6Cm13PUBTgtc+Cpt0yjtCpRbVnu9uUFW6ROHFD+TwKBgGZIULa0M+ba7QBh5hrDDaqTP0uXZ354swSdDydHAQAw2K3/Q5tql2qaFYAiNLsLJJR74J10ssomJAO7YRlzRQy0WmEh39jfmgWEq65xT5NT2MX99KRotiI2PHahOi5MTVPcJAEdXDeKKeoi9eeFMqXzw7GCGt+d35JOJqgkQmHhAoGBAJ4XtogUdzxFzOuM+y0uPjfVJlkjzNUYozaw+yMNAZ6Q85w5DgXW/BrOXPwR9MyLPk/UVcIBPEFiGe2iFICEz3IU3eDnwSpHpnD3IfSqUof4AjUcZ7kecj6HWSq70WkPXPKzKnvhp6u5RsUPHBHnkoyiEwzvZ69668+BCKtQ/UnTAoGAa9Sz7i1kibT1KzKW7Kc3g4DKNdGVFFRUrzjXl1cpWUsngXZk05p2fCjadkxtyvPbBmvzh/xfwgiLiIfDRk4lpW/dIjdSXJUaxtUo0lS+it06RX6V8SMdHAovOnZFBlVrkRqcmud6gnd4To/wlYArih6ndN/TYiaBJDpKynfcuIA=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsfkKhULnHeXxi4kBkcVLbEz3dR+nUa4IWcTu5tfshMS7NqBoPqulidQsaIFGw7M0PwLJc660JxkOGulXJ5IN8mUrWDPQpkDGZPMn06zGWy54JVDCBeiBymIde1fBKCD/bKj4vnGf9IcTl+qhubZgPScRrElEKl0adC2WLq2GoqNOMP32ssfbbhhrhoGInooeHDp56gdrqurce1VfyzqRaxdVh64G6338Wg6rEvtSptorTdOXD9uB0pEYPPUkOT6OM8Q7fUc5gc84sZdzXOh4tBobIyiJQ+GekG5ohguv1bcHZmS53Ga1HCgMsqoPVGyYGbbwLjmEbLx5Si1CZIMoeQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8081/alipay/async-notify"; //异步请求地址 估计是用于ajax的

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8081/alipay/payment-completed"; //支付成功响应后跳转地址  这里是用于 URL调整的同步的玩法
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "UTF-8";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do"; //测试

    // 支付宝网关
    public static String log_path = "E:\\";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}