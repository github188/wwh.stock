package com.skoo.stock.sys.domain;

import com.skoo.orm.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class OperateLog extends BaseEntity {

    public static final long LOGIN_SUCCESS = 1;
    public static final long LOGIN_FAILURE = 2;
    public static final long OPERATING = 3;
    public static final String LOGIN_SUCCESS_TITLE = "login success";
    public static final String LOGIN_FAILURE_TITLE = "login failure";
    public static final String OPERATING_INSERT_TITLE = "新增操作";
    public static final String OPERATING_UPDATE_TITLE = "修改操作";
    public static final String OPERATING_DELETE_TITLE = "删除操作";
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID*
     */
    private Long userId;

    /**
     * 站点ID*
     */
    private Long siteId;

    /**
     * 日志类型*
     */
    private Long category;

    /**
     * 日志时间*
     */
    private java.util.Date logTime;

    /**
     * IP地址*
     */
    private String ip;

    /**
     * URL地址*
     */
    private String url;

    /**
     * 日志标题*
     */
    private String title;

    /**
     * 日志内容*
     */
    private String content;

    /**
     * 排序*
     */
    private Integer orderBy;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public java.util.Date getLogTime() {
        return logTime;
    }

    public void setLogTime(java.util.Date logTime) {
        this.logTime = logTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
