package com.yuanjifeng.project.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yuanjifeng.project.model.bean.BuyBean;
import com.yuanjifeng.project.model.common.TrackStatusEnum;

@Repository
public class BuyDao extends BaseDao{
	

	private StockDao stockDao;
	
	/**
	 * 查询未签收的商品
	 * @return
	 */
	public List<BuyBean> getShippedList(){
		List<BuyBean> list = jdbcTemplate.query(queryShippedSql, new BuyMapper());
		return list;
	}
	
	/**
	 * 进货信息
	 * @param bean
	 */
	public void buy(BuyBean bean){
		SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		jdbcTemplate.update(buySql, source);
	}
	
	
	/**
	 * 物流状态变更
	 * @param bean
	 */
	@Transactional
	public void tracking(BuyBean bean){
		if (bean.getTrackStatus() == TrackStatusEnum.HTWShipped.getValue()) {
			double total = this.querySingle(bean.getId()).getTotal()*1000;
			bean.setUnitCost((bean.getShipping()+total)/bean.getQuantity());
			SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
			jdbcTemplate.update(trackingSql2, source);
		}else if (bean.getTrackStatus() == TrackStatusEnum.Signed.getValue()) {
			SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
			jdbcTemplate.update(trackingSql, source);
			bean = this.querySingle(bean.getId());
			stockDao.addStock(bean);
		}else{
			SqlParameterSource source = new BeanPropertySqlParameterSource(bean);
			jdbcTemplate.update(trackingSql, source);
		}
	}
	
	/**
	 * 商品的平均成本，用来计算利润
	 * @param productId
	 * @return
	 */
	public int queryProductAveCost(int productId){
		HashMap<String,Integer> paramMap = new HashMap<String,Integer>(1);
		paramMap.put("productId", productId);
		List<Map<String,Object>> costList = jdbcTemplate.queryForList(queryCostSql, paramMap);
		int cost = 0;
		for (Map<String, Object> map : costList) {
			cost = cost + (int)map.get("unit_cost");
		}
		return cost/costList.size();
	}
	
	public BuyBean querySingle(int id) {
		HashMap<String,Integer> paramMap = new HashMap<String,Integer>(1);
		paramMap.put("id", id);
		BuyBean bean = jdbcTemplate.queryForObject(queryIdSql, paramMap, new BuyMapper());
		return bean;
	}
	
	private static final class BuyMapper implements RowMapper<BuyBean> {
		@Override
		public BuyBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			BuyBean bean = new BuyBean();
			bean.setId(rs.getInt("id"));
			bean.setProductId(rs.getInt("product_id"));
			bean.setProductName(rs.getString("product_name"));
			bean.setBuyType(rs.getInt("buy_type"));
			bean.setUnitPrice(rs.getInt("unit_price")/1000.0);
			bean.setShipping(rs.getInt("shipping")/1000.0);
			bean.setUnitCost(rs.getInt("unit_cost")/1000.0);
			bean.setQuantity(rs.getInt("quantity"));
			bean.setTotal(rs.getInt("total")/1000.0);
			bean.setTracking(rs.getString("tracking"));
			bean.setTrackStatus(rs.getInt("track_status"));
			bean.setTrackStatusDesc(TrackStatusEnum.getDesc(rs.getInt("track_status")));
			bean.setCreateTime(rs.getDate("create_time"));
			bean.setUpdateTime(rs.getDate("update_time"));
			bean.setUpdateUser(rs.getString("update_user"));
			return bean;
		}
	}
	
	@Autowired
	public void setStockDao(StockDao stockDao){
		this.stockDao = stockDao;
	}

	private static final String buySql = "INSERT INTO `taobao`.`buy` (`product_id`, `product_name`, `buy_type`, `unit_price`, `shipping`, `quantity`, `total`, `unit_cost`, `tracking`, `track_status`, `status`, `create_time`, `update_time`, `update_user`) VALUES (:productId, :productName, :buyType, :unitPrice, :shipping, :quantity, :total, :unitCost, :tracking, 0, '1', sysdate(), sysdate(), :updateUser)";
	private static final String queryIdSql = "select * from buy where id = :id and status = 1";
	private static final String queryShippedSql = "select * from buy where track_status != '4' and status = 1";
	private static final String queryCostSql = "select unit_cost from buy where product_id = :productId and status = 1";
	private static final String trackingSql = "update `taobao`.`buy` set `track_status` = :trackStatus, `update_time` = sysdate() where `id` = :id and `status` = 1";
	private static final String trackingSql2 = "update `taobao`.`buy` set `shipping` = :shipping, `unit_cost` = :unitCost, `track_status` = :trackStatus, `update_time` = sysdate() where `id` = :id and `status` = 1";


}
