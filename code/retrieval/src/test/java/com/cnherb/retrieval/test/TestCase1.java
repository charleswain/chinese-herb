package com.cnherb.retrieval.test;

import org.junit.Assert;
import org.junit.Test;

import com.cnherb.retrieval.test.SpringTestBase;
import com.cnherb.retrieval.dao.*;
import com.cnherb.retrieval.entity.*;

import org.springframework.beans.factory.annotation.Autowired;

public class TestCase1 extends SpringTestBase {
	@Autowired
	private HerbDao HerbDao;
	
	@Test
	public void testSearchByName() {
		String name="人参";
		Herb Herb = HerbDao.queryByName(name);
		
        String descript=null;
        String image =null;
        
    	if( Herb != null) {
    		 descript = Herb.getdescript();
    		 image = Herb.getimage();
    	}
    	
    	System.out.println(image);
    	Assert.assertTrue(true);
	}
        
}
