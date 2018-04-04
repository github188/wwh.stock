package com.javaear.koubeiblog.system.entity.enums;

/**
 * @author aooer 2016/10/11.
 * @version 2.0.0
 * @since 2.0.0
 */
public enum TagStatusEnum {

    init(1, "初始化"),
    onbar(2, "加入导航栏");

    private int status;
    private String desc;

    TagStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static TagStatusEnum findStatus(int status) {
        for (TagStatusEnum tagStatusEnum : TagStatusEnum.values()) {
            if (tagStatusEnum.getStatus() == status)
                return tagStatusEnum;
        }
        return null;
    }

    public static String findDesc(int status) {
        TagStatusEnum statusEnum = findStatus(status);
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
