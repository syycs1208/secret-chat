package com.galaxy.secret.model;

public class Response {

    private byte protocol;
    private byte version;

    // request  response 是否需要相应
    private byte flag;

    private long id;

    private byte[] header;
    private byte serialType;

    private byte[] body;
    private byte bodySerialType;

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public byte getSerialType() {
        return serialType;
    }

    public void setSerialType(byte serialType) {
        this.serialType = serialType;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte getBodySerialType() {
        return bodySerialType;
    }

    public void setBodySerialType(byte bodySerialType) {
        this.bodySerialType = bodySerialType;
    }
}
