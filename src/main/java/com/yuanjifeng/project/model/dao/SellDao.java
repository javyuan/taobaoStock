package com.yuanjifeng.project.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yuanjifeng.project.model.bean.SellBean;

@Repository
public class SellDao extends BaseDao{
	
	@Autowired
	private BuyDao buyDao;
	
	@Autowired
	private StockDao stockDao;
	
	/**
	 * 出货信息
	 * @param bean
	 */
	@Transactional
	public void sell(SellBean bean){
		bean.setTotal((bean.getUnitPrice()+bean.getUnitShipping()) * bean.getQuantity());
		bean.setProfit(bean.getUnitPrice()-bean.getUnitShipping()-buyDao.queryProductAveCost(bean.getProductId()));
		SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		jdbcTemplate.update(sellSql, source);
		stockDao.decreaseStock(bean);
	}

	private static final String sellSql = "INSERT INTO `taobao`.`sell` (`product_id`, `product_name`, `unit_price`, `unit_shipping`, `quantity`, `total`, `profit`, `status`, `create_time`, `update_time`, `update_user`) VALUES (:productId, :productName, :unitPrice, :unitShipping, :quantity, :total, :profit, 1, sysdate(), sysdate(), :updateUser)";
}
