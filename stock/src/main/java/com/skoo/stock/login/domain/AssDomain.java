package com.skoo.stock.login.domain;

import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("AssDomain")
public class AssDomain extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业ID *
     */
    public Long assId;

    /**
     * 企业ID *
     */
    public Long assIdLocal;

    /**
     * 域名 *
     */
    public String domain;

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
    }

    public Long getAssIdLocal() {
        return assIdLocal;
    }

    public void setAssIdLocal(Long assIdLocal) {
        this.assIdLocal = assIdLocal;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
