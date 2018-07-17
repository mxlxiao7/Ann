package com.ann.function.rx;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
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
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by maxiaolong on 2016/11/28.
 */
public class RxFragment extends BaseFragment {

    private static final String TAG = RxFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView textView1;
    private TextView mMsg;
    private Button mBtn;

    public RxFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RxFragment newInstance() {
        RxFragment fragment = new RxFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_rx_layout, container, false);
        mMsg = (TextView) rootView.findViewById(R.id.tv_msg);


        mClearBtn = (Button) rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.setLength(0);
                helloEventBus("");
            }
        });
        mBtn = (Button) rootView.findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.msg("\n-------------------------1------------------------");
                rx1();
                Utils.msg("\n-------------------------2------------------------");
                rx2();
                Utils.msg("\n-------------------------3------------------------");
                rx3();
                Utils.msg("\n-------------------------4------------------------");
                rx4();
                Utils.msg("\n-------------------------5------------------------");
                rx5();
                Utils.msg("\n-------------------------6------------------------");
                rx6();
                Utils.msg("\n-------------------------7------------------------");
                rx7();
                Utils.msg("\n-------------------------8------------------------");
                rx8();
                Utils.msg("\n-------------------------9------------------------");
                rx9();
                Utils.msg("\n-------------------------10------------------------");
                rx10();
                Utils.msg("\n-------------------------11------------------------");
//                rx11();
                Utils.msg("\n-------------------------12------------------------");
                rx12();
            }
        });
        return rootView;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }

    /**
     * 1.0写法
     */
    private void rx1() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Disposable是1.x的Subscription改名的，因为Reactive-Streams规范用这个名称，为了避免重复
                //这个回调方法是在2.0之后新添加的
                //可以使用d.dispose()方法来取消订阅
                Utils.msg("onSubscribe ");
            }

            @Override
            public void onNext(String value) {
                Utils.msg("onNext " + value);
            }

            @Override
            public void onError(Throwable e) {
                Utils.msg("onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Utils.msg("onComplete complete");
            }
        };

        //创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("Hello World!");
            }
        });

        observable.subscribe(observer);
    }

    /**
     * 2.0写法
     */
    private void rx2() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Utils.msg("onSubscribe ");
                //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Utils.msg("onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                Utils.msg("onError ");
            }

            @Override
            public void onComplete() {
                Utils.msg("onComplete ");
            }
        };

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("Hello,I am China!");
            }
        }, BackpressureStrategy.BUFFER).subscribe(subscriber);
    }

    /**
     *
     */
    private void rx3() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Utils.msg("onSubscribe ");
                //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                //调用request()方法，会立即触发onNext()方法
                //在onComplete()方法完成，才会再执行request()后边的代码
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Utils.msg("onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                Utils.msg("onError ");
            }

            @Override
            public void onComplete() {
                Utils.msg("onComplete ");
            }
        };


        Flowable.just("Hello,I am China!").subscribe(subscriber);
        //.subscribeWith(subscriber)//在1.x中此方法返回Subscription，而在2.x中是没有返回值的
        //所以增加subscribeWith()方法，用来返回一个Disposable对象
        //使得用户可以CompositeDisposable.add()方法添加对象。1.x为CompositeSubscription
        //其他subscribe()重载方法返回Disposable
    }

    /**
     * 简洁用法
     */
    private void rx4() {
        Flowable.just("hello RxJava 2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Utils.msg(s);
                    }
                });
    }

    /******************************操作符start*************************************/

    /**
     *  map         变换
     *  flatMap     变换生成新的Flowable
     *  lift        不推荐使用，最好使用lift的包装方法map，flatMap
     *  filter      过滤
     *  take        取前几个数
     *  range(a,b)  取从a开始数b个数
     *  compose
     *  concat
     *  merge
     *
     */

    /**
     * 操作符: map变换
     */
    private void rx5() {
        Flowable.just("1")
                //这个第一个泛型为接收参数的数据类型，第二个泛型为转换后要发射的数据类型
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + " 追加内容";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Utils.msg(s);
                    }
                });
    }


    /**
     * map 操作符进阶
     */
    private void rx6() {
        Flowable.just("map")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Utils.msg(s);
                    }
                });
    }

    /**
     * map 操作符进阶
     */
    private void rx7() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        //普通形式
//        Flowable.fromIterable(list)
//                .subscribe(num -> Utils.msg(num.toString()));

        //from形式
        Flowable.fromIterable(list)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Utils.msg(integer.toString());
                    }
                });


//        Flowable.just(list)
//                .subscribe(nums -> {
//                    Observable.fromIterable(nums).subscribe(num -> Utils.msg(num.toString()));
//                });
    }


    /**
     * map 操作符进阶（改进）
     * Flowable.flatMap 可以把一个 Flowable 转换成另一个 Flowable :
     * 和 map 不同之处在于 flatMap 返回的是一个 Flowable 对象。这正是我们想要的，我们可以把从List发射出来的一个一个的元素发射出去。
     */
    private void rx8() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(1);
        list.add(5);

        Flowable.just(list)
                .flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Utils.msg(integer.toString());
                    }
                });
    }

    /**
     * 更多操作符
     * filter 是用于过滤数据的，返回false表示拦截此数据。
     */
    private void rx9() {
        //大于5的数据
        Utils.msg("----大于5的数据----");
        Flowable.fromArray(1, 20, 5, 0, -1, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 5;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Utils.msg(integer.toString());
                    }
                });


        //如果我们只想要2个数据:
        Utils.msg("----想要2个数据:----");
//        Flowable.fromArray(1, 2, 3, 4)
//                .take(2)
//                .subscribe(integer -> {
//                    Utils.msg(integer.toString());
//                });


        //如果我们想在订阅者接收到数据前干点事情，比如记录日志:doOnNext 允许我们在每次输出一个元素之前做一些额外的事情。
        Utils.msg("----接收到数据前记录日志:----");
        Flowable.just(1, 2, 3)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Utils.msg("预处理" + integer);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Utils.msg("处理:" + integer);
                    }
                });
    }
    /******************************操作符end*************************************/

    /**
     * onComplete 和 onError 方法
     * <p>
     * 这样的设计有以下几个优点:
     * <p>
     * 1.只要发生错误，onError()一定会被调用。
     * 这极大的简化了错误处理。只需要在一个地方处理错误即可以。
     * <p>
     * 2.操作符不需要处理异常。
     * 将异常处理交给订阅者来做，一旦有调用链中有一个抛出了异常，就会直接执行onError()方法，停止数据传送。
     * <p>
     * 3.你能够知道什么时候订阅者已经接收了全部的数据。
     */
    private void rx10() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("exception:" + (1 / 0));
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        Utils.msg(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Utils.msg("onError");
                    }

                    @Override
                    public void onComplete() {
                        Utils.msg("on complete");
                    }
                });
    }

    /******************************线程调度start*************************************/

    /**
     * 调度器:
     * 在Android中写多线程不是一件容易的事，尤其是嵌套数据获取，比如要获取用的资料，其中有一项是头像，
     * 但得到的一般是头像的url地址，你还需要在资料获取成功后，在发送一次请求，这样就导致代码看起来很乱。
     * 使用RxJava你可以随意的切换线程。
     * <p>
     * 上述代码中，Flowable总共发射了两个数据，但中间延时了3秒，如果在主线程中延时，那将会导致UI卡顿，这是绝对不能容忍的。
     * 所以在订阅之前，我们使用 subscribeOn(Schedulers.io()) 指定了发送数据是在io线程(某个子线程)，然后调用 observeOn(AndroidSchedulers.mainThread()) 指定订阅者在主线程执行。
     */
    private void rx11() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("将会在3秒后显示  " + Utils.isMainThread());
                SystemClock.sleep(3000);
                e.onNext("RxJava你可以随意的切换线程" + Utils.isMainThread());
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        s += "    ";
                        s += Utils.isMainThread();
                        Utils.msg(s);
                    }
                });
    }

    /**
     *
     */
    private void rx12() {

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                Utils.msg("FlowableOnSubscribe - subscribe()    " + Utils.isMainThread());
                e.onNext("");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        Utils.msg("doOnSubscribe() - accept()   " + Utils.isMainThread());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Utils.msg("subscribe() - accept()   " + Utils.isMainThread());
                    }
                });
    }
}