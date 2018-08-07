package com.ann.function.io.aio;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ann.utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AIO Client
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
@SuppressLint("NewApi")
public class Client {

    public final static int PORT = 8001;
    public final static String IP = "127.0.0.1";
    private static AsynchronousSocketChannel client;
    private static boolean isConnected;


    /**
     * 启动
     */
    public synchronized static void start() {
        if (client != null) {
            return;
        }

        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        InetSocketAddress serverAddress = new InetSocketAddress(IP, PORT);
        CompletionHandler<Void, ? super Object> handler = new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                isConnected = true;
            }

            @Override
            public void failed(Throwable e, Object attachment) {
                Utils.msg(Log.getStackTraceString(e));
            }

        };
        client.connect(serverAddress, null, handler);
    }

    /**
     * 停止
     */
    public static void stop() {
        if (client.isOpen()) {
            try {
                client.close();
                client = null;
                Utils.msg("客户端关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     */
    public static void send(String msg) {
        if (!isConnected) {
            Utils.msg("没有连接");
            return;
        }

        Utils.msg("发送消息为：" + msg);

        //发送消息
        client.write(
                ByteBuffer.wrap(msg.getBytes()),
                null,
                new CompletionHandler<Integer, Object>() {

                    @Override
                    public void completed(Integer result, Object attachment) {

                        final ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                buffer.flip();
                                Utils.msg("客户端收到消息：" + new String(buffer.array()));

                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                            }

                        });
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                    }

                });
    }

}
