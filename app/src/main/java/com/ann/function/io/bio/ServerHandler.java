package com.ann.function.io.bio;

import android.util.Log;

import com.ann.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 独立线程
 * <p>
 * 用于处理一个客户端的Socket链路
 */
public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {

            String expression;
            String result;
            while (true) {
                //通过BufferedReader读取一行
                //如果已经读到输入流尾部，返回null,退出循环
                //如果得到非空值，就尝试计算结果并返回
                if ((expression = in.readLine()) == null) break;
                Utils.msg("服务器收到消息：" + expression);
                result = "A : " + expression;
                out.println(result);
            }
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    Utils.msg(Log.getStackTraceString(e));
                }
            }
        }
    }
}
