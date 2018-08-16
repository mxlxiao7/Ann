package com.ann.function.ping;

import android.util.Log;

import com.ann.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ping线程
 * <p>
 * Author:maxiaolong
 * Date:2018/8/16
 * Email:mxlxiao7@sina.com
 */
class PingWorker extends Thread {
    public final Process process;
    public Integer exit;
    public String ip;

    public PingWorker(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        try {
            exit = process.waitFor();
            if (exit == 0) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String str;
                StringBuffer ipInfo = new StringBuffer();
                //读出所有信息并显示
                while ((str = buf.readLine()) != null) {
                    ipInfo.append(str);
                    Utils.msg(str);
                }
                /**
                 *  PING sni1st.dtwscache.ourwebcdn.com (14.215.228.4) 56(84) bytes of data.64 bytes from 14.215.228.4: icmp_seq=1 ttl=57 time=16.6 ms--- sni1st.dtwscache.ourwebcdn.com ping statistics ---1 packets transmitted, 1 received, 0% packet loss, time 0msrtt min/avg/max/mdev = 16.656/16.656/16.656/0.000 ms
                 */
//                Utils.msg("IpInfo----->" + ipInfo);
                Pattern mPattern = Pattern.compile("\\((.*?)\\)");
                Matcher matcher = mPattern.matcher(ipInfo.toString());
                if (matcher.find()) {
                    ip = matcher.group(1);
                }
            } else {
                ip = " process.waitFor()===" + exit;
            }
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
            ip = "java.io.IOException: Stream closed";
            return;
        } catch (InterruptedException e) {
            Utils.msg(Log.getStackTraceString(e));
            ip = "java.io.InterruptedException: Stream closed";
            return;
        }
    }
}
