package cn.wuyuwei.tiny_shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.wuyuwei.tiny_shop.mapper")
public class TinyShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyShopApplication.class, args);
	}

}
