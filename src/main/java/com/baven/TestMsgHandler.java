package com.baven;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class TestMsgHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到了 msg, msg =>" + msg);

//        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
//        ByteBuf byteBuf = frame.content();
        ByteBuf byteBuf = (ByteBuf) msg;

        byte[] byteArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byteArray);

        System.out.print("收到的字节 = ");
        for (byte b : byteArray) {
            System.out.print(b);
            System.out.print(", ");
        }
        System.out.println();

    }
}
