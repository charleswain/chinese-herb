package com.cnherb.retrieval.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTestBase {
	
}