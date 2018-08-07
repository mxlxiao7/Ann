package com.ann.function.io.bio;

import android.util.Log;

import com.ann.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 客户端
 */
public class Client {

    //默认的端口号
    private static int DEFAULT_SERVER_PORT = 12345;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String expression) {
        send(DEFAULT_SERVER_PORT, expression);
    }

    public static void send(int port, String expression) {
        Utils.msg("发送消息为：" + expression);

        try (
                Socket socket = new Socket(DEFAULT_SERVER_IP, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {

            out.println(expression);
            Utils.msg("客户端收到消息：" + in.readLine());

        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

}
