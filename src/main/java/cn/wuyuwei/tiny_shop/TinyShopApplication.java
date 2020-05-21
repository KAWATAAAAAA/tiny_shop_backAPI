package cn.wuyuwei.tiny_shop;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@MapperScan("cn.wuyuwei.tiny_shop.dao")
public class TinyShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyShopApplication.class, args);
	}
	/*@Bean
	public ServerEndpointExporter serverEndpointExporter (){
		return new ServerEndpointExporter();
	}*/

}
