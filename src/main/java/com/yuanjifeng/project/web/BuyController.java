package com.yuanjifeng.project.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanjifeng.project.model.bean.BuyBean;
import com.yuanjifeng.project.model.dao.BuyDao;

@Controller
public class BuyController {
	
	private Log log = LogFactory.getLog(BuyController.class);
	
	@Autowired
	private BuyDao buyDao;

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    @ResponseBody
    public String buy(@ModelAttribute BuyBean bean) {
    	log.info(bean.getProductId()+","+bean.getProductName());
    	bean.setUnitPrice(bean.getUnitPrice()*6200);
    	bean.setUnitShipping(bean.getUnitShipping()*1000);
    	bean.setTotal((bean.getUnitPrice()+bean.getUnitShipping())*bean.getQuantity()*1000);
    	bean.setUnitCost((bean.getUnitPrice()+bean.getUnitShipping())*1000);
    	buyDao.buy(bean);
    	return "success";
    }
}
