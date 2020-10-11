package com.baven;

import com.baven.cmdHandler.UserEntryCmdHandler;
import com.baven.cmdHandler.UserMoveToCmdHandler;
import com.baven.cmdHandler.WhoElseIsHereCmdHandler;
import com.baven.model.User;
import com.baven.model.UserManager;
import com.baven.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏消息处理器
 */
public class TestMsgHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        BroadCaster.addChannel(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);

        BroadCaster.removeChannel(ctx.channel());

        Integer userId = (Integer)ctx.channel().attr(AttributeKey.valueOf("userId")).get();

        if (null == userId) {
            return;
        }

        UserManager.removeUserById(userId);

        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);

        GameMsgProtocol.UserQuitResult newResult = resultBuilder.build();
        BroadCaster.broadcast(newResult);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到了msg, msg => " + msg);

        if (msg instanceof GameMsgProtocol.UserEntryCmd) {
            (new UserEntryCmdHandler()).handle(ctx, (GameMsgProtocol.UserEntryCmd) msg);
        } else if (msg instanceof GameMsgProtocol.WhoElseIsHereCmd) {
            (new WhoElseIsHereCmdHandler()).handle(ctx, (GameMsgProtocol.WhoElseIsHereCmd) msg);
        } else if (msg instanceof GameMsgProtocol.UserMoveToCmd) {
            (new UserMoveToCmdHandler()).handle(ctx, (GameMsgProtocol.UserMoveToCmd) msg);
        }
    }

}
