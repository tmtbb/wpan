package com.xinyu.mwp.networkapi.socketapi.SocketReqeust;


import com.xinyu.mwp.networkapi.NetworkAPIConfig;
import com.xinyu.mwp.networkapi.socketapi.SocketAPIFactoryImpl;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by yaowang on 2017/2/18.
 */

public class SocketAPINettyBootstrap {

    private static final SocketAPINettyBootstrap bootstrap = new SocketAPINettyBootstrap();


    private SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public static SocketAPINettyBootstrap getInstance() {
        return bootstrap;
    }


    public void writeAndFlush(Object object) {
        socketChannel.writeAndFlush(object);
    }

    public void connect() {
        new Thread() {
            @Override
            public void run() {
                SocketAPINettyBootstrap.getInstance().startConnect();
            }
        }.start();
    }

    private Boolean startConnect() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        NetworkAPIConfig networkAPIConfig = SocketAPIFactoryImpl.getInstance().getConfig();

        bootstrap.remoteAddress(networkAPIConfig.getSocketServerIp(), networkAPIConfig.getSocketServerPort());
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                socketChannel.pipeline().addLast(new SocketAPIEncoder());
                socketChannel.pipeline().addLast(new SocketAPIDecoder());
                socketChannel.pipeline().addLast(new SocketAPINettyHandler());
            }
        });
        ChannelFuture future = null;
        try {
            future = bootstrap.connect(new InetSocketAddress(networkAPIConfig.getSocketServerIp(), networkAPIConfig.getSocketServerPort())).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  成功---------");
                return true;
            } else {
                System.out.println("connect server  失败---------");
                return false;
            }
        } catch (Exception e) {
            System.out.println("无法连接----------------");
            return false;
        }

    }

    /**
     * 关闭通道
     */
    public void closeChannel() {
        if (socketChannel != null) {
            socketChannel.close();
        }
    }

    /**
     * @return 返回通道连接状态
     */
    public boolean isOpen() {
        if (socketChannel != null) {
            System.out.println(socketChannel.isOpen());
            return socketChannel.isOpen();
        }
        return false;
    }

    public interface OnConnectListener {

        void onExist();

        void onSuccess();

        void onFailure();
    }

}
