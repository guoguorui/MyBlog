package com.gary.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;




//增加搜索功能,结合mongo可能需要分布式
//忘记名字
//缓存优化
//做好安全措施
//提供异常处理框架88
//打包成jar
//考虑下载功能
//搞定浏览器缓存问题
@SpringBootApplication
@EnableCaching
public class DemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
