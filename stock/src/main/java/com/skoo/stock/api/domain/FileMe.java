package com.skoo.stock.api.domain;


import com.skoo.orm.domain.BaseEntity;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
public class FileMe extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String mimeType;
    private byte[] content;
    private FileMe file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public FileMe getFile() {
        return file;
    }

    public void setFile(FileMe file) {
        this.file = file;
    }
}
