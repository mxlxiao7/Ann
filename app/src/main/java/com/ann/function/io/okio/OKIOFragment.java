package com.ann.function.io.okio;

import android.annotation.SuppressLint;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static android.content.Context.MODE_PRIVATE;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * OKIO
 * <p>
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
@SuppressLint("NewApi")
public class OKIOFragment extends BaseFragment {

    private static final String TAG = OKIOFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;

    private static final String path = "okio_msg.txt";


    public OKIOFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OKIOFragment newInstance() {
        OKIOFragment fragment = new OKIOFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_okio_layout, container, false);
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

        mBtn3 = rootView.findViewById(R.id.btn3);
        mBtn3.setOnClickListener(view -> action3());

        mBtn4 = rootView.findViewById(R.id.btn4);
        mBtn4.setOnClickListener(view -> action4(null));

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }


    /**
     * 文件读写
     * <p>
     * copy assert 文件到/data/data/<Package Name>/files/
     */
    public void action0() {
        String msg = "";

        //1、读取
        try (
                InputStream in = getResources().getAssets().open(path);
                BufferedSource buffer = Okio.buffer(Okio.source(in));
        ) {
            msg = buffer.readUtf8();
            Utils.msg("读取内容：" + msg);
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        //2、写入
        try (
                FileOutputStream out = getContext().openFileOutput(path, MODE_PRIVATE);
                BufferedSink buffer = Okio.buffer(Okio.sink(out));
        ) {
            buffer.writeUtf8(msg);
            buffer.flush();
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        //3、比较
        try (
                InputStream in = getResources().getAssets().open(path);
                FileInputStream in2 = getContext().openFileInput(path);
        ) {
            int len1 = in.available();
            int len2 = in2.available();

            Utils.msg("源文件长度：" + len1);
            Utils.msg("复制文件长度：" + len2);
            Utils.msg("复制是否成功：" + (len2 == len1));
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }

    }


    /**
     * 文件内容的追
     */
    public void action1() {
        try {
            File file = new File(path);
            //1.将文件读入，并构建写缓冲池
            BufferedSink sink = Okio.buffer(Okio.appendingSink(file));
            //2.追加文本
            sink.writeUtf8("Hello, ");
            //3.关闭
            sink.close();
            //4.再次追加文本，需要重新构建缓冲池对象
            sink = Okio.buffer(Okio.appendingSink(file));
            //5.追加文本
            sink.writeUtf8("java.io file!");
            //6.关闭缓冲池
            sink.close();
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

    /**
     * 通过路径来读写文件
     */
    public void action2() {
        try {
            File file = new File(path);
            Path path = file.toPath();
            //1.构建写缓冲池
            BufferedSink sink = Okio.buffer(Okio.sink(path));
            //2.写缓冲
            sink.writeUtf8("Hello, java.nio file!");
            //3.关闭缓冲
            sink.close();

            //1.构建读缓冲源
            BufferedSource source = Okio.buffer(Okio.source(path));
            //2.读文本
            source.readUtf8();
            //3.关闭缓冲源
            source.close();
        } catch (Throwable e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

    /**
     * 写buffer
     */
    public void action3() {
        try {
            //1.构建buffer对象
            Buffer data = new Buffer();
            //2.向缓冲中写入文本
            data.writeUtf8("Hello ");
            //3.可以连续追加，类似StringBuffer
            data.writeUtf8("Okio !  ");

            //4.构建字节数组流对象
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //5.构建写缓冲池
            Sink sink = Okio.sink(out);
            //6.向池中写入buffer
            sink.write(data, data.size());

            action4(out.toByteArray());
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }


    /**
     * 读buffer
     */
    public void action4(byte[] data) {
        try {
            if (data == null) {
                data = ("Hello Okio !").getBytes(UTF_8);
            }

            //1.构建字节数组流
            InputStream in = new ByteArrayInputStream(data);
            //2.缓冲源
            Source source = Okio.source(in);
            //3.buffer
            Buffer buf = new Buffer();
            //4.将数据读入buffer
            source.read(buf, data.length);

            Utils.msg("消息：" + buf.readUtf8());
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

}