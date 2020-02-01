package cn.wuyuwei.tiny_shop.utils;


import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取项目的IP和端口
 *
 * @wuyuwei
 * @Time: 2019/2/22-16:21
 */


@Component
public class HostUtils implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    public int getServerPort() {
        return this.serverPort;
    }

    public String getHost() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

       // return "http://"+address.getHostAddress()+":"+this.getServerPort();
        return "http://"+address.getHostAddress()+":8080";
    }



    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
}