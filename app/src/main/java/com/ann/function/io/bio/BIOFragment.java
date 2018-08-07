package com.ann.function.io.bio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ann.BaseFragment;
import com.ann.R;
import com.ann.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Random;

/**
 * BIO
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
public class BIOFragment extends BaseFragment {

    private static final String TAG = BIOFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;

    public BIOFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BIOFragment newInstance() {
        BIOFragment fragment = new BIOFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_bio_layout, container, false);
        mMsg = rootView.findViewById(R.id.tv_msg);

        mClearBtn = rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(view -> {
            s.setLength(0);
            helloEventBus("");
        });

        mBtn0 = rootView.findViewById(R.id.btn0);
        mBtn0.setOnClickListener(view -> action0());

        mBtn1 = rootView.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(view -> action1());

        mBtn2 = rootView.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(view -> action2());
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }


    @Override
    public void onDetach() {
        super.onDetach();
        action2();
        Toast.makeText(getContext(), "onDetach()", Toast.LENGTH_LONG).show();
    }

    /**
     * 启动服务端
     */
    public void action0() {
        //运行服务器
        new Thread(() -> {
            try {
                Server.start();
            } catch (IOException e) {
                Utils.msg(Log.getStackTraceString(e));
            }
        }).start();
    }

    /**
     * 客户端发送
     */
    public void action1() {
        new Thread(() -> {
            //运行客户端
            char operators[] = {'+', '-', '*', '/'};
            Random random = new Random(System.currentTimeMillis());
            //随机产生算术表达式
            String expression = random.nextInt(10) + "" + operators[random.nextInt(4)] + (random.nextInt(10) + 1);
            Client.send(expression);
        }).start();
    }

    /**
     * 停止
     */
    public void action2() {
        new Thread(() -> Server.shutDown()).start();
    }
}