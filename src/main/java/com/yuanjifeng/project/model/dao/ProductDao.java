package com.yuanjifeng.project.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yuanjifeng.project.model.bean.ProductBean;

@Repository
public class ProductDao extends BaseDao{
	
	/**
	 * 查商品
	 * @return
	 */
	public List<ProductBean> queryAll(){
		List<ProductBean> list = jdbcTemplate.query(queryAllSql, new ProductMapper());
		return list;
	}
	private static final class ProductMapper implements RowMapper<ProductBean> {
		@Override
		public ProductBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductBean bean = new ProductBean();
			bean.setId(rs.getInt("id"));
			bean.setProductId(rs.getInt("product_id"));
			bean.setProductName(rs.getString("product_name"));
			bean.setCreateTime(rs.getDate("create_time"));
			bean.setUpdateTime(rs.getDate("update_time"));
			bean.setUpdateUser(rs.getString("update_user"));
			return bean;
		}
	}
	
	private static final String queryAllSql = "select * from product where status = 1";
}
