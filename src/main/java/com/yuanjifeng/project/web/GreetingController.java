package com.yuanjifeng.project.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuanjifeng.project.model.bean.BuyBean;
import com.yuanjifeng.project.model.dao.BuyDao;

@Controller
public class GreetingController { 
	
	@Autowired
	private BuyDao buyDao;
	
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(Model m) {
    	List<BuyBean> bean = buyDao.getShippedList();
    	m.addAttribute("products",bean);
        return "greeting";
    }

}
