package com.zjhcsoft.struc.mapper;

import java.util.Map;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper {
	int count(@Param(value="sql")String sql);
	
	String selectPublish(@Param(value="sql")String sql);

	int insert(@Param(value="sql")String sql);

	int update(@Param(value="sql")String sql);
	
	int delete(@Param(value="sql")String sql);
	
	Map<String, Object> select(@Param(value="sql") String sql);
}