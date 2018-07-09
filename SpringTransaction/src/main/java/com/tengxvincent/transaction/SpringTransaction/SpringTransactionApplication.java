package com.tengxvincent.transaction.SpringTransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tengxvincent.transaction.SpringTransaction.dao")
public class SpringTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionApplication.class, args);
	}
}
