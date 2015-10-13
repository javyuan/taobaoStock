package com.yuanjifeng.project.model.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class BaseDao {

	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
