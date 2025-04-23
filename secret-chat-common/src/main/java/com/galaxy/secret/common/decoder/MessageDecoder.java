package com.galaxy.secret.common.decoder;

import com.galaxy.secret.common.util.ByteUtil;
import com.galaxy.secret.model.Request;
import com.galaxy.secret.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        try {

        // while (byteBuf.isReadable()) {
            byte[] totalLengthByte = new byte[4];
            byteBuf.readBytes(totalLengthByte, 0, 4);
            int totalLength = ByteUtil.bytes2int(totalLengthByte);
            if (totalLength <= 0) {
                return;
            }

            byte[] totalContent = new byte[totalLength];
            byteBuf.readBytes(totalContent, 0, totalLength);

            if ((totalContent[2] & 1) == 1) {
                list.add(toRequest(totalContent));

            } else if ((totalContent[2] & 2) == 2) {
                list.add(toResponse(totalContent));
            }
        // }

        } catch (Exception e) {

        }

    }

    private Request toRequest(byte[] totalContent) {
        Request r = new Request();
        int offset = 0;
        r.setProtocol(totalContent[offset++]);
        r.setVersion(totalContent[offset++]);
        r.setFlag(totalContent[offset++]);
        r.setId(ByteUtil.bytes2long(totalContent, offset));
        offset += 8;
        r.setSerialType(totalContent[offset++]);

        int headLength = ByteUtil.bytes2int(totalContent, offset);
        offset += 4;

        byte[] head = new byte[headLength];
        System.arraycopy(totalContent, offset, head, 0, headLength);
        offset += headLength;
        r.setHeader(head);

        r.setBodySerialType(totalContent[offset++]);

        int bodyLength = totalContent.length - 17 - headLength;
        byte[] body = new byte[bodyLength];
        System.arraycopy(totalContent, offset, body, 0, bodyLength);
        r.setBody(body);

        return r;

    }


    private Response toResponse(byte[] totalContent) {
        Response r = new Response();
        int offset = 0;
        r.setProtocol(totalContent[offset++]);
        r.setVersion(totalContent[offset++]);
        r.setFlag(totalContent[offset++]);
        r.setId(ByteUtil.bytes2long(totalContent, offset));
        offset += 8;
        r.setSerialType(totalContent[offset++]);

        int headLength = ByteUtil.bytes2int(totalContent, offset);
        offset += 4;

        byte[] head = new byte[headLength];
        System.arraycopy(totalContent, offset, head, 0, headLength);
        offset += headLength;
        r.setHeader(head);

        r.setBodySerialType(totalContent[offset++]);

        int bodyLength = totalContent.length - 17 - headLength;
        byte[] body = new byte[bodyLength];
        System.arraycopy(totalContent, offset, body, 0, bodyLength);
        r.setBody(body);

        return r;

    }
}
