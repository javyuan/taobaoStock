package com.yuanjifeng.project.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.yuanjifeng.project.model.bean.SellBean;

@Repository
public class SellDao extends BaseDao{
	
	@Autowired
	private BuyDao buyDao;
	
	/**
	 * 出货信息
	 * @param bean
	 */
	public void sell(SellBean bean){
		bean.setTotal((bean.getUnitPrice()+bean.getUnitShipping()) * bean.getQuantity());
		bean.setProfit(bean.getTotal()-buyDao.queryProductAveCost(bean.getProductId()));
		SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		jdbcTemplate.update(sellSql, source);
	}

	private static final String sellSql = "INSERT INTO `taobao`.`sell` (`product_id`, `product_name`, `unit_price`, `unit_shipping`, `quantity`, `total`, `profit`, `status`, `create_time`, `update_time`, `update_user`) VALUES (:product_id, :product_name, :unit_price, :unit_shipping, :quantity, :total, :profit, 1, sysdate(), sysdate(), :update_user)";
}
