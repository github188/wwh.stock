package com.zjhcsoft.struc.mapper;

import com.zjhcsoft.struc.bean.CrawlerBidInfo;
import com.zjhcsoft.struc.bean.CrawlerBidInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlerBidInfoMapper {
    int countByExample(CrawlerBidInfoExample example);

    int deleteByExample(CrawlerBidInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlerBidInfo record);

    int insertSelective(CrawlerBidInfo record);

    List<CrawlerBidInfo> selectByExample(CrawlerBidInfoExample example);

    CrawlerBidInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlerBidInfo record, @Param("example") CrawlerBidInfoExample example);

    int updateByExample(@Param("record") CrawlerBidInfo record, @Param("example") CrawlerBidInfoExample example);

    int updateByPrimaryKeySelective(CrawlerBidInfo record);

    int updateByPrimaryKey(CrawlerBidInfo record);
}