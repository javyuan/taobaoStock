package com.yuanjifeng.project.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanjifeng.project.model.bean.BuyBean;
import com.yuanjifeng.project.model.bean.ProductBean;
import com.yuanjifeng.project.model.bean.StockBean;
import com.yuanjifeng.project.model.dao.BuyDao;
import com.yuanjifeng.project.model.dao.ProductDao;
import com.yuanjifeng.project.model.dao.StockDao;

@Controller
public class GreetingController { 
	
	private Log log = LogFactory.getLog(BuyController.class);
	
	@Autowired
	private BuyDao buyDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private StockDao stockDao;
	
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(Model m) {
    	List<BuyBean> buyList = buyDao.getShippedList();
    	List<ProductBean> productList = productDao.queryAll();
    	List<StockBean> stockList = stockDao.queryAll();
    	m.addAttribute("buyList",buyList);
    	m.addAttribute("productList",productList);
    	m.addAttribute("stockList",stockList);
        return "greeting";
    }

    @RequestMapping(value = "/updateTrackStatus", method = RequestMethod.GET)
    @ResponseBody
    public String updateTrackStatus(@RequestParam(value="id", required=true) int id,@RequestParam(value="pid", required=true) int productId,@RequestParam(value="q", required=true) int quantity, @RequestParam(value="t", required=true) int trackStatus, @RequestParam(value="s") float shipping) {
    	BuyBean bean = new BuyBean();
    	bean.setId(id);
    	bean.setProductId(productId);
    	bean.setQuantity(quantity);
    	bean.setTrackStatus(trackStatus);
    	bean.setShipping(shipping*1000);
    	buyDao.tracking(bean);
    	return "success";
    }
}
