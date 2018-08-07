package com.ann.function.io.bio;

import android.util.Log;

import com.ann.utils.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO服务端源码
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
public class Server {

    //默认的端口号
    private static int DEFAULT_SERVER_PORT = 12345;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";


    //单例的ServerSocket
    private static ServerSocket server;

    private static volatile boolean isExit = true;

    //线程池 懒汉式的单例
    private static ExecutorService sExecutorService;

    //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
    public static void start() throws IOException {
        //使用默认值
        start(DEFAULT_SERVER_PORT);
    }

    //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
    public synchronized static void start(int port) throws IOException {
        if (server != null) {
            return;
        }

        sExecutorService = Executors.newFixedThreadPool(60);
        isExit = false;

        //通过构造函数创建ServerSocket
        //如果端口合法且空闲，服务端就监听成功
        server = new ServerSocket(port);

        Utils.msg("服务器已启动，端口号：" + port);

        try {

            //通过无线循环监听客户端连接
            //如果没有客户端接入，将阻塞在accept操作上。
            while (!isExit) {
                Socket socket = server.accept();
                //当有新的客户端接入时，会执行下面的代码
                //然后创建一个新的线程处理这条Socket链路
                sExecutorService.execute(new ServerHandler(socket));
            }
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        } finally {
            //一些必要的清理工作
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    Utils.msg(Log.getStackTraceString(e));
                }
                server = null;
                Utils.msg("服务器已关闭");
            }
        }
    }

    /**
     * 优雅的停止服务
     */
    public static void shutDown() {
        if (isExit) {
            return;
        }

        isExit = true;
        try {
            new Socket(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT);
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        sExecutorService.shutdown();
        sExecutorService = null;
    }

}
