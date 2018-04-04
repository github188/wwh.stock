package com.javaear.koubeiblog.system.entity.enums;

public enum CommentStatusEnum {

    disAllow(1, "待审核"),
    allow(2, "通过审核"),
    delete(0, "删除");

    private int status;
    private String desc;

    CommentStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static CommentStatusEnum findStatus(int status) {
        for (CommentStatusEnum articleStatusEnum : CommentStatusEnum.values()) {
            if (articleStatusEnum.getStatus() == status)
                return articleStatusEnum;
        }
        return null;
    }

    public static String findDesc(int status) {
        CommentStatusEnum statusEnum = findStatus(status);
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
