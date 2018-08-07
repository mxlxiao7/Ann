package com.ann.function.io.nio;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * NIO
 * Java NIO系统的核心在于：通道(Channel)和缓冲区(Buffer)。
 * 缓冲区（ Buffer）：
 * ByteBuffer，CharBuffer，ShortBuffer，IntBuffer，LongBuffer，FloatBuffer，DoubleBuffer
 * <p>
 * 【Channel】
 * FileChannel：从文件中读写数据
 * DatagramChannel：能通过UDP读写网络中的数据
 * SocketChannel：能通过TCP读写网络中的数据
 * ServerSocketChannel：可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel
 * <p>
 * Selector：Selector允许单线程处理多个 Channel。如果你的应用打开了多个连接（通道），但每个连接的流量都很低，使用Selector就会很方便。例如，在一个聊天服务器中。
 * <p>
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
public class NIOFragment extends BaseFragment {

    private static final String TAG = NIOFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;

    public NIOFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NIOFragment newInstance() {
        NIOFragment fragment = new NIOFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_nio_layout, container, false);
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
        mBtn4.setOnClickListener(view -> {
            try {
                action4();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mBtn5 = rootView.findViewById(R.id.btn5);
        mBtn5.setOnClickListener(view -> action5());

        mBtn6 = rootView.findViewById(R.id.btn6);
        mBtn6.setOnClickListener(view -> action6());

        mBtn7 = rootView.findViewById(R.id.btn7);
        mBtn7.setOnClickListener(view -> action7());

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
        isExit = true;
        Toast.makeText(getContext(), "onDetach()", Toast.LENGTH_LONG).show();
    }

    /**
     * 有以下种copy方式
     * 1、InputStream > OutputStream
     * 2、Channel
     * 3、Files.copy
     * <p>
     * copy assert 文件到/data/data/<Package Name>/files/
     */
    public void action0() {
        String path = "msg.txt";

        //1、复制
        try (
                InputStream in = getResources().getAssets().open(path);
                FileOutputStream out = getContext().openFileOutput(path, MODE_PRIVATE);
        ) {
            // 创建byte数组
            byte[] buffer = new byte[512];
            // 将文件中的数据读到byte数组中
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        //2、检查文件长度是否一致。
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
     * 使用FileChannel读取数据到Buffer中的示例
     * truncate():可以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除
     * force():将通道里尚未写入磁盘的数据强制写到磁盘上。
     */
    public void action1() {
        String path = "msg.txt";
        try (
                FileInputStream in = getContext().openFileInput(path);
        ) {

            FileChannel channel = in.getChannel();//获取channel
            ByteBuffer buf = ByteBuffer.allocate(10);//分配buffer大小

            int len;
            while ((len = channel.read(buf)) > 0) {
                Utils.msg("Read length = " + len);
                buf.flip();//从写模式转换为读模式
                while (buf.hasRemaining()) {//是否还有可读数据
                    Utils.msg(String.valueOf((char) buf.get()));
                }
                buf.clear();//清苦已经读取的数据
            }
        } catch (FileNotFoundException e) {
            Utils.msg(Log.getStackTraceString(e));
        } catch (IOException e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

    /**
     * Buffer
     * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，
     * 并提供了一组方法，用来方便的访问该块内存。
     */
    public void action2() {

        /**
         * 1、Buffer的基本用法
         */
        //1、使用Buffer读写数据一般遵循以下四个步骤：
        //2、写入数据到Buffer
        //3、调用flip()方法
        //4、从Buffer中读取数据
        //5、调用clear()方法或者compact()方法
        //当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
        //在读模式下，可以读取之前写入到buffer的所有数据。一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
        //有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据。
        //任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

        /**
         * 2、Buffer的capacity,position和limit
         */
        //capacity:
        //作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。

        //position:
        //当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
        //当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。

        //limit:
        //在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
        //当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）


        /**
         * 3、Buffer的类型
         */
        //ByteBuffer
        //MappedByteBuffer有些特别，在涉及它的专门章节中再讲。
        //CharBuffer
        //DoubleBuffer
        //FloatBuffer
        //IntBuffer
        //LongBuffer
        //ShortBuffer

        /**
         * 4、Buffer的分配
         */
        //这是一个分配48字节capacity的ByteBuffer
        ByteBuffer buf1 = ByteBuffer.allocate(48);
        //这是分配一个可存储1024个字符的CharBuffer：
        CharBuffer buf2å = CharBuffer.allocate(1024);

        /**
         * 5、向Buffer中写数据
         */
        //1、从Channel写到Buffer。
        //int bytesRead = inChannel.read(buf);
        //2、通过Buffer的put()方法写到Buffer里。
        //buf.put(127);

        /**
         * 6、flip()方法
         */
        //flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
        //换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等 —— 现在能读取多少个byte、char等。


        /**
         * 7、从Buffer中读取数据
         */
        //1、从Buffer读取数据到Channel。
        //int bytesWritten = inChannel.write(buf);
        //2、使用get()方法从Buffer中读取数据。
        //byte aByte = buf.get();

        /**
         * 8、clear()与compact()方法
         */
        //如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
        //如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
        //如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
        //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。


        /**
         * 9、mark()与reset()方法
         */
        //通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()
        //方法恢复到这个position。


        /**
         * 10、equals()与compareTo()方法
         */
        //equals():
        //当满足下列条件时，表示两个Buffer相等：
        //1、有相同的类型（byte、char、int等）。
        //2、Buffer中剩余的byte、char等的个数相等。
        //3、Buffer中所有剩余的byte、char等都相同。
        //如你所见，equals只是比较Buffer的一部分，不是每一个在它里面的元素都比较。实际上，它只比较Buffer中的剩余元素。

        //compareTo():
        //compareTo()方法比较两个Buffer的剩余元素(byte、char等)， 如果满足下列条件，则认为一个Buffer“小于”另一个Buffer：
        //1、第一个不相等的元素小于另一个Buffer中对应的元素 。
        //2、所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。

        /**
         * 11、rewind()方法
         */
        //Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，
        //仍然表示能从Buffer中读取多少个元素（byte、char等）。
    }

    /**
     * 数据传输
     * transferFrom
     * transferTo
     */
    public void action3() {
        String path = "msg.txt";
        String bak = "msg_1.txt";
        String bak2 = "msg_2.txt";

        //1、transferFrom
        try (
                FileInputStream fis = getContext().openFileInput(path);
                FileOutputStream fos = getContext().openFileOutput(bak, MODE_PRIVATE);
        ) {
            Utils.msg("源文件大小： " + fis.available());
            Utils.msg("transferFrom");

            FileChannel inFC = fis.getChannel();
            FileChannel outFC = fos.getChannel();

            long position = 0;
            long count = fis.available();
            outFC.transferFrom(inFC, position, count);

            Utils.msg("目标文件大小： " + fos.getChannel().size());
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }


        //2、transferTo
        try (
                FileInputStream fis = getContext().openFileInput(bak);
                FileOutputStream fos = getContext().openFileOutput(bak2, MODE_PRIVATE);
        ) {
            Utils.msg("源文件大小： " + fis.available());
            Utils.msg("transferTo");

            FileChannel inFC = fis.getChannel();
            FileChannel outFC = fos.getChannel();

            long position = 0;
            long count = fis.available();
            inFC.transferTo(position, count, outFC);

            Utils.msg("目标文件大小： " + fos.getChannel().size());
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

    private boolean isExit;

    /**
     * Pipe
     * 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
     */
    public void action4() throws IOException {

        //模拟两个线程一个写，一个读取
        isExit = false;

        //初始化Pipe
        Pipe pipe = Pipe.open();

        //获取写同道
        Pipe.SinkChannel sink = pipe.sink();
        sink.configureBlocking(true);

        new Thread(() -> {
            try {
                ByteBuffer buf = ByteBuffer.allocate(512);
                char msg = 'a';
                while (!isExit) {
                    buf.clear();
                    if (msg > 'z') {
                        msg = 'a';
                    }
                    buf.put((byte) msg);
                    //向同道写入数据
                    buf.flip();
                    sink.write(buf);

                    msg++;
                    Thread.sleep(500);
                }
                sink.close();
            } catch (Exception e) {
                Utils.msg(Log.getStackTraceString(e));
            }
        }).start();


        //获取读同道
        Pipe.SourceChannel source = pipe.source();
        source.configureBlocking(true);

        new Thread(() -> {
            ByteBuffer buf = ByteBuffer.allocate(512);
            try {
                while (!isExit && source.read(buf) > 0) {
                    buf.flip();
                    while (buf.hasRemaining()) {
                        char ch = (char) buf.get();
                        Utils.msg("读取数据： " + ch);
                    }
                    buf.clear();
                }

                source.close();
            } catch (Exception e) {
                Utils.msg(Log.getStackTraceString(e));
            }
        }).start();
    }

    /**
     * 启动服务端
     */
    public void action5() {
        Server.start();

        //避免客户端先于服务器启动前执行代码
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        Client.start();
    }

    /**
     * 发送数据
     */
    public void action6() {
        new Thread(() -> {
            //运行客户端
            char operators[] = {'+', '-', '*', '/'};
            Random random = new Random(System.currentTimeMillis());
            //随机产生算术表达式
            String expression = random.nextInt(10) + "" + operators[random.nextInt(4)] + (random.nextInt(10) + 1);
            try {
                Client.sendMsg(expression);
            } catch (Exception e) {
                Utils.msg(Log.getStackTraceString(e));
            }
        }).start();
    }

    /**
     * 停止服务端
     */
    public void action7() {
        new Thread(() -> {
            Client.stop();
            Server.stop();
        }).start();
    }

}