package com.yuanjifeng.project.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanjifeng.project.model.bean.SellBean;
import com.yuanjifeng.project.model.dao.SellDao;

@Controller
public class SellController {
	
	private Log log = LogFactory.getLog(SellController.class);
	
	@Autowired
	private SellDao sellDao;

    @RequestMapping(value = "/sell", method = RequestMethod.GET)
    @ResponseBody
    public String sell(@ModelAttribute SellBean bean) {
    	log.info(bean.getProductId()+","+bean.getProductName());
    	bean.setUnitPrice(bean.getUnitPrice()*1000);
    	bean.setUnitShipping(bean.getUnitShipping()*1000);
    	sellDao.sell(bean);
    	return "success";
    }
}
