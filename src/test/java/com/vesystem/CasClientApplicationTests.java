package com.vesystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CasClientApplicationTests {

	@Test
	public void contextLoads() {
		String regex = "^(https|http)://localhost:9091.*";
		System.out.println(Pattern.matches(regex,"http://localhost:9090/index"));
	}



}
