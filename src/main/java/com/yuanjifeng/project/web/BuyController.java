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
import com.yuanjifeng.project.model.common.BuyTypeEnum;
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
    	bean.setTotal(bean.getTotal()*6200);
    	if (bean.getBuyType() == BuyTypeEnum.ZhiYou.getValue()) {
    		double unitCost = bean.getTotal()/bean.getQuantity();
    		if (unitCost < 0) {
				return "unitCost为负数";
			}
    		bean.setUnitCost(unitCost);
		}
    	buyDao.buy(bean);
    	return "success";
    }
}
