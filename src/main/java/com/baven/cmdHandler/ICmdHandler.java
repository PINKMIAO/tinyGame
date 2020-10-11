package com.baven.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

public interface ICmdHandler<Tcm extends GeneratedMessageV3> {

    void handle(ChannelHandlerContext ctx, Tcm cmd);

}
