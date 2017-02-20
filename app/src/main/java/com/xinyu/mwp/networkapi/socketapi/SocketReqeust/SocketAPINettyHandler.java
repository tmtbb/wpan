package com.xinyu.mwp.networkapi.socketapi.SocketReqeust;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yaowang on 2017/2/18.
 */

public class SocketAPINettyHandler extends SimpleChannelInboundHandler<SocketDataPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, SocketDataPacket socketDataPacket) throws Exception {

        SocketAPIRequestManage.getInstance().notifyResponsePacket(socketDataPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
