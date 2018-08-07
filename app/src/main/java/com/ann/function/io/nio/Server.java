package com.ann.function.io.nio;

/**
 * NIO服务端
 *
 * @version 1.0
 */
public class Server {
    private static int DEFAULT_PORT = 12345;
    private static ServerHandle serverHandle;

    /**
     * 启动
     */
    public static void start() {
        start(DEFAULT_PORT);
    }

    /**
     * 启动
     *
     * @param port
     */
    public static synchronized void start(int port) {
        stop();
        serverHandle = new ServerHandle(port);
        new Thread(serverHandle, "Server").start();
    }

    /**
     * 停止
     */
    public static void stop() {
        if (serverHandle != null) {
            serverHandle.stop();
            serverHandle = null;
        }
    }

}
