package com.baven.cmdHandler;

import com.baven.BroadCaster;
import com.baven.model.User;
import com.baven.model.UserManager;
import com.baven.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd>{

    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd msg) {
        GameMsgProtocol.UserEntryCmd cmd = msg;
        int userId = cmd.getUserId();
        String userAvatar = cmd.getHeroAvatar();

        GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(userAvatar);

        User user = new User();
        user.setUserId(userId);
        user.setHeroAvatar(userAvatar);
        UserManager.addUser(user);

        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);

        GameMsgProtocol.UserEntryResult newResult = resultBuilder.build();
        BroadCaster.broadcast(newResult);
    }

}
