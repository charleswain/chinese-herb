package com.cnherb.retrieval.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.cnherb.retrieval.dao.HerbDao;
import com.cnherb.retrieval.entity.Herb;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SearchSpringController {
	
    @Autowired
    private HerbDao HerbDao;
    
    String message = "Welcome to Spring MVC!!!";
 
    @RequestMapping("/search")
    public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) {
 
    	
        Herb Herb = HerbDao.queryByName(name);
        String descript=null;
        String image =null;
        
    	if( Herb != null) {
    		 descript = Herb.getdescript();
    		 image = Herb.getimage();
    	}
    	
    	
    	//String Herbname = "test";
    //	System.out.print(Herb);
        ModelAndView mv = new ModelAndView("searchspring");
        
        mv.addObject("message", message);
        mv.addObject("name", name);
        mv.addObject("descript", descript);
        mv.addObject("image",image);
        return mv;
    }
}