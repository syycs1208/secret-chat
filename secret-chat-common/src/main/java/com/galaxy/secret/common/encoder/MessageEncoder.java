package com.galaxy.secret.common.encoder;

import com.galaxy.secret.common.util.ByteUtil;
import com.galaxy.secret.model.Request;
import com.galaxy.secret.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        if (o instanceof Request) {
            writeRequest((Request) o, byteBuf);
            // super.flush(channelHandlerContext);
        } else if (o instanceof Response) {
            writeResponse((Response) o, byteBuf);
            // super.flush(channelHandlerContext);
        }
    }

    private void writeRequest(Request r, ByteBuf buf) {

        // 总长度
        int totalLength = 4 + 12 + r.getHeader().length + 5 + r.getBody().length;
        byte[] totalBuf = new byte[totalLength];
        int offset = 0;
        // 写入总长度
        ByteUtil.int2bytes(totalLength, totalBuf, offset);
        offset += 4;


        totalBuf[offset++] = r.getProtocol();
        totalBuf[offset++] = r.getVersion();
        totalBuf[offset++] = r.getFlag();

        // 写入请求id
        ByteUtil.long2bytes(r.getId(), totalBuf, offset);
        offset += 8;

        totalBuf[offset++] = r.getSerialType();

        // 写入头长度
        ByteUtil.int2bytes(r.getHeader().length, totalBuf, offset);
        offset += 4;

        // 写入头数据
        System.arraycopy(r.getHeader(), 0, totalBuf, offset, r.getHeader().length);
        offset += r.getHeader().length;

        totalBuf[offset++] = r.getBodySerialType();

        // 写入body数据
        System.arraycopy(r.getBody(), 0, totalBuf, offset, r.getBody().length);



        buf.writeBytes(totalBuf);

    }


    private void writeResponse(Response r, ByteBuf buf) {

        // 总长度
        int totalLength = 4 + 12 + r.getHeader().length + 5 + r.getBody().length;
        byte[] totalBuf = new byte[totalLength];
        int offset = 0;
        // 写入总长度
        ByteUtil.int2bytes(totalLength, totalBuf, offset);
        offset += 4;


        totalBuf[offset++] = r.getProtocol();
        totalBuf[offset++] = r.getVersion();
        totalBuf[offset++] = r.getFlag();

        // 写入请求id
        ByteUtil.long2bytes(r.getId(), totalBuf, offset);
        offset += 8;

        totalBuf[offset++] = r.getSerialType();

        // 写入头长度
        ByteUtil.int2bytes(r.getHeader().length, totalBuf, offset);
        offset += 4;

        // 写入头数据
        System.arraycopy(r.getHeader(), 0, totalBuf, offset, r.getHeader().length);
        offset += r.getHeader().length;

        totalBuf[offset++] = r.getBodySerialType();

        // 写入body数据
        System.arraycopy(r.getBody(), 0, totalBuf, offset, r.getBody().length);

        buf.writeBytes(totalBuf);

    }
}
