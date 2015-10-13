package com.yuanjifeng.project.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.yuanjifeng.project.model.bean.BuyBean;
import com.yuanjifeng.project.model.bean.SellBean;
import com.yuanjifeng.project.model.bean.StockBean;

@Repository
public class StockDao extends BaseDao{
	
	
	/**
	 * 查库存
	 * @return
	 */
	public List<StockBean> queryAll(){
		List<StockBean> list = jdbcTemplate.query(queryAllSql, new StockMapper());
		return list;
	}
	
	/**
	 * 减库存
	 * @param bean
	 */
	public void decreaseStock(SellBean bean){
		SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		StockBean stock = jdbcTemplate.queryForObject(queryStockSql, source, new StockMapper());
		stock.setStock(stock.getStock()-bean.getQuantity());
		source = new BeanPropertySqlParameterSource(stock);
		jdbcTemplate.update(updateStockSql, source);
	}
	/**
	 * 加库存
	 * @param bean
	 */
	public void addStock(BuyBean bean) {
		SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		StockBean stock = jdbcTemplate.queryForObject(queryStockSql, source, new StockMapper());
		if (stock == null) {
			jdbcTemplate.update(insertStockSql, source);
		}else {
			stock.setStock(stock.getStock()+bean.getQuantity());
			source = new BeanPropertySqlParameterSource(stock);
			jdbcTemplate.update(updateStockSql, source);
		}
	}
	
	private static final class StockMapper implements RowMapper<StockBean> {
		@Override
		public StockBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			StockBean bean = new StockBean();
			bean.setId(rs.getInt("id"));
			bean.setProductId(rs.getInt("product_id"));
			bean.setProductName(rs.getString("product_name"));
			bean.setStock(rs.getInt("stock"));
			bean.setCreateTime(rs.getDate("create_time"));
			bean.setUpdateTime(rs.getDate("update_time"));
			bean.setUpdateUser(rs.getString("update_user"));
			return bean;
		}
	}
	
	private static final String queryStockSql = "select * from stock where product_id = :productId and status = 1";
	private static final String queryAllSql = "select * from stock where status = 1";
	private static final String insertStockSql = "INSERT INTO `taobao`.`stock` (`product_id`, `product_name`, `stock`, `status`, `create_time`, `update_time`, `update_user`) VALUES (:product_id, :product_name, :quantity, 1, sysdate(), sysdate(), :update_user);";
	private static final String updateStockSql = "update `taobao`.`stock` set `stock` = :stock, `update_time` = sysdate() where `id` = :id and `status` = 1";
}
