package com.ann.function.io.aio;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ann.utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * AIO Server
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
@SuppressLint("NewApi")
public class Server {

    public final static int PORT = 8001;
    public final static String IP = "127.0.0.1";
    private static AsynchronousServerSocketChannel server;


    //使用这个通道(server)来进行客户端的接收和处理
    public synchronized static void start() {
        if (server != null) {
            return;
        }

        try {
            //同样是利用工厂方法产生一个通道，异步通道 AsynchronousServerSocketChannel
            server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(IP, PORT));
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
        }
        Utils.msg("服务器已启动，端口号：" + PORT);

        //注册事件和事件完成后的处理器，这个CompletionHandler就是事件完成后的处理器
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                Utils.msg("处理线程：" + Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    Utils.msg("服务器收到消息：" + new String(buffer.array()));
                    //将数据写回客户端
                    buffer.flip();
                    writeResult = result.write(buffer);
                } catch (Exception e) {
                    Utils.msg(Log.getStackTraceString(e));
                } finally {
                    server.accept(null, this);
                    try {
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        Utils.msg(Log.getStackTraceString(e));
                    }
                }

            }

            @Override
            public void failed(Throwable e, Object attachment) {
                Utils.msg(Log.getStackTraceString(e));
            }

        });
    }


    /**
     * 优雅的停止服务
     */
    public static void stop() {
        if (server == null || !server.isOpen()) {
            return;
        }

        try {
            server.close();
            server = null;
            Utils.msg("服务器已关闭");
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }
}
