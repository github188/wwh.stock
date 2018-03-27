package com.skoo.stock.login.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private Date createTime;

    private Date recoverTime;

    public Ticket() {
        super();
    }

    public Ticket(String username, Timestamp createTime, Timestamp recoverTime) {
        super();
        this.username = username;
        this.createTime = createTime;
        this.recoverTime = recoverTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Timestamp recoverTime) {
        this.recoverTime = recoverTime;
    }

}
