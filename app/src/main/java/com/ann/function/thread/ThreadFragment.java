package com.ann.function.thread;

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
import com.ann.function.thread.countdownlatch.CountDownLatchDemo;
import com.ann.function.thread.cylicbarrier.CylicBarrierDemo;
import com.ann.function.thread.deadlock.DeadLockDemo;
import com.ann.function.thread.exchanger.ExchangerDemo;
import com.ann.function.thread.pc.PCDemo;
import com.ann.function.thread.phaser.PhaserDemo;
import com.ann.function.thread.reentrantlock.ReentrantLockDemo;
import com.ann.function.thread.semaphore.SemaphoreDemo;
import com.ann.function.thread.terminationlocker.TerminateLockDemo;
import com.ann.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 线程学习类
 * 1、中断锁
 * 2、死锁
 * 3、ReentrantLock，ReentrantReadWriteLock，Condition
 * 4、生产者-消费者模式
 * 5、CountDownLatch
 * 6、Semaphore
 * 7、Phaser
 * 8、CyclicBarrier
 * 9、Exchanger
 * <p>
 * Author:maxiaolong
 * Date:2018/7/25
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class ThreadFragment extends BaseFragment {

    private static final String TAG = ThreadFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;
    private Button mBtn9;

    public ThreadFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ThreadFragment newInstance() {
        ThreadFragment fragment = new ThreadFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_thread_layout, container, false);
        mMsg = rootView.findViewById(R.id.tv_msg);


        mClearBtn = rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(view -> {
            s.setLength(0);
            helloEventBus("");
        });
        mBtn1 = rootView.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(view -> TerminateLockDemo.main());

        mBtn2 = rootView.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(view -> DeadLockDemo.main());

        mBtn3 = rootView.findViewById(R.id.btn3);
        mBtn3.setOnClickListener(view -> ReentrantLockDemo.main());

        mBtn4 = rootView.findViewById(R.id.btn4);
        mBtn4.setOnClickListener(view -> PCDemo.main());

        mBtn5 = rootView.findViewById(R.id.btn5);
        mBtn5.setOnClickListener(view -> {
            new Thread(() -> {
                try {
                    CountDownLatchDemo.main();
                } catch (InterruptedException e) {
                    Utils.msg(Log.getStackTraceString(e));
                }
            }).start();
        });

        mBtn6 = rootView.findViewById(R.id.btn6);
        mBtn6.setOnClickListener(view -> SemaphoreDemo.main());

        mBtn7 = rootView.findViewById(R.id.btn7);
        mBtn7.setOnClickListener(view -> PhaserDemo.main());

        mBtn8 = rootView.findViewById(R.id.btn8);
        mBtn8.setOnClickListener(view -> CylicBarrierDemo.main());

        mBtn9 = rootView.findViewById(R.id.btn9);
        mBtn9.setOnClickListener(view -> ExchangerDemo.main());
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }
}