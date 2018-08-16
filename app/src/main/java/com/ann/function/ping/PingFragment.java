package com.ann.function.ping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ann.BaseFragment;
import com.ann.R;
import com.ann.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 执行ping命令
 * <p>
 * Author:maxiaolong
 * Date:2018/8/16
 * Email:mxlxiao7@sina.com
 */
public class PingFragment extends BaseFragment {

    private static final String TAG = PingFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn0;


    public PingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PingFragment newInstance() {
        PingFragment fragment = new PingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ping_layout, container, false);
        mMsg = rootView.findViewById(R.id.tv_msg);

        mClearBtn = rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(view -> {
            s.setLength(0);
            helloEventBus("");
        });


        mBtn0 = rootView.findViewById(R.id.btn0);
        mBtn0.setOnClickListener(view -> start());

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }

    /**
     * 开始ping
     */
    private void start() {
        ping(ip);
    }


    private String ip = "wwww.baidu.com";
    private PingWorker mWorker;
    /**
     * 测试主域名是否可用
     *
     * @param ip
     * @return
     */
    private final int PING_TIME_OUT = 1000; // ping 超时时间

    /**
     * @param ip
     * @return
     */
    private boolean ping(String ip) {
        try {
            Integer status = executeCommandIp(ip, PING_TIME_OUT);
            if (status != null && status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
        return false;
    }

    /**
     * 执行域名是否可通
     *
     * @param command
     * @param timeout
     * @return
     * @throws Exception
     */
    private int executeCommandIp(final String command, final long timeout) throws Exception {
        Process process = Runtime.getRuntime().exec("ping -c 5 -w 100 " + command);
        mWorker = new PingWorker(process);
        mWorker.start();
        try {
            mWorker.join(timeout);
            if (mWorker.exit != null) {
                return mWorker.exit;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            mWorker.interrupt();
            Thread.currentThread().interrupt();
            throw ex;
        } finally {
            process.destroy();
        }
    }
}