package com.cnherb.retrieval.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

import com.cnherb.retrieval.dao.HerbDao;
import com.cnherb.retrieval.entity.Herb;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.HashMap;

@Controller
//@RequestMapping(value="/Herb/*")
public class HerbController {
    @Autowired
	private HerbDao  HerbDao;
//	@RequestMapping(value ="/herb", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	public String getHerb(HttpServletRequest request) {
//		String name = request.getParameter("name");
//		return name;
//	}
//	
//	@RequestMapping(value ="/herb", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
//	@ResponseBody	
//	public String getHerbs(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name)
//	{
//		return name;
//	}
	
	
	@RequestMapping(value ="/herb", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody	
	public Map<String, String> getHerbs(
		@RequestParam(value = "name", required = false, defaultValue = "Spring") String name,
		@RequestParam(value = "page", required = false, defaultValue = "1") String page )
	{
		Map<String, String> map = new HashMap<String, String>();
		
		Herb Herb = HerbDao.queryByName(name);
        String descript="test";
        String image ="test";
        
    	if( Herb != null) {
    		 descript = Herb.getdescript();
    		 image = Herb.getimage();
    	}
    	map.put("descript", descript);
    	map.put("image", image);
    	
		return  map;
	}
	
	
}
