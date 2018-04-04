package com.javaear.koubeiblog.system.entity.enums;

public enum ArticleStatusEnum {

    init(1, "初始化"),
    pubulic(2, "公开"),
    hide(3, "私密"),
    top(4, "置顶"),
    delete(0, "删除");

    private int status;
    private String desc;

    ArticleStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ArticleStatusEnum findStatus(int status) {
        for (ArticleStatusEnum articleStatusEnum : ArticleStatusEnum.values()) {
            if (articleStatusEnum.getStatus() == status)
                return articleStatusEnum;
        }
        return null;
    }

    public static String findDesc(int status) {
        ArticleStatusEnum statusEnum = findStatus(status);
        return statusEnum == null ? "" : statusEnum.getDesc();
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
