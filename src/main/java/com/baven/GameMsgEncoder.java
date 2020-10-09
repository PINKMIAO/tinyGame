package com.baven;

import com.baven.msg.GameMsgProtocol;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (null == msg || !(msg instanceof GeneratedMessageV3)){
            super.write(ctx, msg, promise);
            return;
        }

        int msgCode = -1;

        if (msg instanceof GameMsgProtocol.UserEntryResult) {
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE;
        } else if (msg instanceof GameMsgProtocol.UserMoveToResult) {
            msgCode = GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE;
        } else if (msg instanceof GameMsgProtocol.WhoElseIsHereResult) {
            msgCode = GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE;
        } else if (msg instanceof GameMsgProtocol.UserQuitResult) {
            msgCode = GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;
        } else {
            LOGGER.error("无法识别的消息类型，msgClazz = " + msg.getClass().getName());
            return;
        }

        byte[] msgBody = ((GeneratedMessageV3) msg).toByteArray();

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeShort((short)0);
        byteBuf.writeShort((short)msgCode);
        byteBuf.writeBytes(msgBody);

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
        super.write(ctx,frame,promise);

    }
}
