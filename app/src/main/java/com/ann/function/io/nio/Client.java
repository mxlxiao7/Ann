package com.ann.function.io.nio;

/**
 * NIO客户端-处理器
 *
 * @version 1.0
 */
public class Client {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 12345;
    private static ClientHandle clientHandle;

    /**
     * 启动
     */
    public static void start() {
        start(DEFAULT_HOST, DEFAULT_PORT);
    }

    /**
     * 启动
     */
    public static synchronized void start(String ip, int port) {
        stop();
        clientHandle = new ClientHandle(ip, port);
        new Thread(clientHandle, "Server").start();
    }

    /**
     * 发送消息
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static boolean sendMsg(String msg) throws Exception {
        if (msg.equals("q")) return false;
        clientHandle.sendMsg(msg);
        return true;
    }

    /**
     * 停止
     */
    public static void stop() {
        if (clientHandle != null) {
            clientHandle.stop();
            clientHandle = null;
        }
    }

}
