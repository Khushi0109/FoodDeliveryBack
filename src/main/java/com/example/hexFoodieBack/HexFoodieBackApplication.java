package com.example.hexFoodieBack;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableEncryptableProperties
public class HexFoodieBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexFoodieBackApplication.class, args);
	}

}
