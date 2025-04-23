package com.galaxy.secret.model;

public class Request {

    private byte protocol;
    private byte version;

    // request  response 是否需要相应
    private byte flag;

    private byte serialType;

    private long id;

    // 4 + 8
    // header.length
    // 1 + 4
    // body.length

    private byte[] header;

    private byte bodySerialType;

    private byte[] body;

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

    public byte getSerialType() {
        return serialType;
    }

    public void setSerialType(byte serialType) {
        this.serialType = serialType;
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

    public byte getBodySerialType() {
        return bodySerialType;
    }

    public void setBodySerialType(byte bodySerialType) {
        this.bodySerialType = bodySerialType;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static Request sample() {

        Request r = new Request();
        r.setProtocol((byte)3);
        r.setVersion((byte)4);
        r.setFlag((byte)1);
        r.setSerialType((byte)5);
        r.setId(1999L);
        r.setBodySerialType((byte)7);
        r.setHeader("fjhij".getBytes());
        r.setBody("abcde".getBytes());

        return r;
    }
}
