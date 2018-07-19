package com.ann.function.rx;

import android.os.Bundle;
import android.os.SystemClock;
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
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 学习专区
 * <p>
 * Author:maxiaolong
 * Date:2018/7/21
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
@SuppressWarnings("unused")
public class RxFragment extends BaseFragment {

    private static final String TAG = RxFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rx_layout, container, false);
        mMsg = rootView.findViewById(R.id.tv_msg);


        mClearBtn = rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.setLength(0);
                helloEventBus("");
            }
        });
        mBtn = rootView.findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.msg("\n-------------------------exe------------------------");
//                exe(RxFragment.this);


//                Utils.msg("\n-------------------------1------------------------");
//                rx1();
//                Utils.msg("\n-------------------------2------------------------");
//                rx2();
//                Utils.msg("\n-------------------------3------------------------");
//                rx3();
//                Utils.msg("\n-------------------------4------------------------");
//                rx4();
//                Utils.msg("\n-------------------------5------------------------");
//                rx5();
//                Utils.msg("\n-------------------------6------------------------");
//                rx6();
//                Utils.msg("\n-------------------------7------------------------");
//                rx7();
//                Utils.msg("\n-------------------------8------------------------");
//                rx8();
//                Utils.msg("\n-------------------------9------------------------");
//                rx9();
//                Utils.msg("\n-------------------------10------------------------");
//                rx10();
//                Utils.msg("\n-------------------------11------------------------");
//                rx11();
//                Utils.msg("\n-------------------------12------------------------");
//                rx12();
                Utils.msg("\n-------------------------13------------------------");
                rx13();
//                Utils.msg("\n-------------------------14------------------------");
//                rx14();
//                Utils.msg("\n-------------------------15------------------------");
//                rx15();
//                Utils.msg("\n-------------------------16------------------------");
//                rx16();
//                Utils.msg("\n-------------------------17------------------------");
//                rx17();
//                Utils.msg("\n-------------------------18------------------------");
//                rx18();
//                Utils.msg("\n-------------------------19------------------------");
//                rx19();
//                Utils.msg("\n-------------------------20------------------------");
//                rx20();
//                Utils.msg("\n-------------------------20------------------------");
//                rx21();
//                Utils.msg("\n-------------------------20------------------------");
//                rx22();
//                Utils.msg("\n-------------------------20------------------------");
//                rx23();
//                Utils.msg("\n-------------------------20------------------------");
//                rx24();
//                Utils.msg("\n-------------------------20------------------------");
//                rx25();
//                Utils.msg("\n-------------------------20------------------------");
//                rx26();
//                Utils.msg("\n-------------------------20------------------------");
//                rx27();
//                Utils.msg("\n-------------------------20------------------------");
//                rx28();
//                Utils.msg("\n-------------------------20------------------------");
//                rx29();
//                Utils.msg("\n-------------------------20------------------------");
//                rx30();
//                Utils.msg("\n-------------------------20------------------------");
//                rx31();
//                Utils.msg("\n-------------------------20------------------------");
//                rx32();
//                Utils.msg("\n-------------------------20------------------------");
//                rx33();
            }
        });
        return rootView;
    }

    /**
     * 反射当前对象，并执行以 rx + [数字] 的方法
     */
    private void exe(Object obj) {

        if (obj == null || !(obj instanceof RxFragment)) {
            return;
        }

        Class<? extends RxFragment> cls = this.getClass();
        Method[] methods = cls.getMethods();

        do {
            if (methods == null || methods.length == 0) {
                break;
            }

            for (Method method : methods) {
                String name = method.getName();
                if (name != null && name.contains("rx")) {
                    try {
                        method.invoke(obj);
                    } catch (Exception e) {
                        Utils.msg(Log.getStackTraceString(e));
                    }
                }
            }
        } while (false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }

    /******************************基础使用 start*************************************/

    /**
     * 1.0写法,在1.0中这种写法可以配置背压，2.0中这种写法不支持背压
     */
    private void rx1() {
        //创建被观察者,不支持背压
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                Utils.msg("Observable emit 1");
                e.onNext("1");
                Utils.msg("Observable emit 2");
                e.onNext("2");
                Utils.msg("Observable emit 3");
                e.onNext("3");
                e.onComplete();
                Utils.msg("Observable emit 4");
                e.onNext("4");
            }
        }).subscribe(new Observer<String>() {

            Disposable mDisposable;
            int i = 0;

            @Override
            public void onSubscribe(Disposable d) {
                this.mDisposable = d;
                Utils.msg("onSubscribe ");
            }

            @Override
            public void onNext(String value) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
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
        });
    }

    /**
     * 2.0写法，支持背压
     */
    private void rx2() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) {
                Utils.msg("FlowableEmitter emit");
                emitter.onNext("Hello,I am China!");
            }
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<String>() {

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
        });
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
     * doOnNext 在订阅者接收到数据前干点事情
     */
    private void rx4() {
        //如果我们想在订阅者接收到数据前干点事情，比如记录日志:doOnNext 允许我们在每次输出一个元素之前做一些额外的事情。
        Utils.msg("----接收到数据前记录日志:----");
        Flowable.just("hello RxJava")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Utils.msg("预处理" + s);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Utils.msg("处理:" + s);
                    }
                });
    }
    /******************************基础使用 end*************************************/


    /******************************操作符 start*************************************/

    /**
     * 操作符: map
     */
    private void rx5() {
        Flowable.just("1")
                //这个第一个泛型为接收参数的数据类型，第二个泛型为转换后要发射的数据类型
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s + " 追加内容";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Utils.msg(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
    }


    /**
     * 操作符: map   连续变换
     */
    private void rx6() {
        Flowable.just("map")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Utils.msg(s);
                    }
                });
    }

    /**
     * flatMap 并不能保证事件的顺序
     */
    private void rx7() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < integer; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) {
                        Utils.msg("flatMap : accept : " + s);
                    }
                });
    }

    /**
     * flatMap
     * <p>
     * 实现多个网络请求依次依赖:
     * 例如用户注册成功后需要自动登录，我们只需要先通过注册接口注册用户信息，注册成功后马上调用登录接口进行自动登录即可。
     */
    private void rx8() {
        Observable.create((ObservableOnSubscribe<RxUser>) emitter -> {
            //模拟网络操作请求 RxUser
            RxUser data = new RxUser();
            emitter.onNext(data);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(rxUser -> {
                    //在这里做一些数据加工操作
                    Utils.msg("doOnNext : accept : " + rxUser.name);
                })
                .observeOn(Schedulers.io())
                .flatMap((Function<RxUser, ObservableSource<RxCar>>) rxUser -> {
                    //在这里做二级加载动作
                    return Observable.create(emitter -> emitter.onNext(rxUser.car));
                })
                .subscribe(
                        car -> Utils.msg("accept: success ：" + car.toString()),
                        throwable -> Utils.msg("accept: error :" + throwable.getMessage()));
    }


    /**
     * concatMap 保证了顺序
     */
    private void rx9() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) {
                        Utils.msg("concatMap : accept : " + s);
                    }
                });
    }


    /**
     * concat 操作符
     */
    private void rx10() {
        Flowable.concat(Flowable.just(1, 2, 3), Flowable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) {
                        Utils.msg("concat : " + integer);
                    }
                });
    }

    /**
     * concat 操作符
     * <p>
     * 模拟真实场景:
     * 很多时候（对数据操作不敏感时）都需要我们先读取缓存的数据，
     * 如果缓存没有数据,再通过网络请求获取，随后在主线程更新我们的UI。
     */
    private void rx11() {
        //读取本地数据的操作
        Flowable<RxUser> cache = Flowable.create(new FlowableOnSubscribe<RxUser>() {
            @Override
            public void subscribe(FlowableEmitter<RxUser> e) {
                Utils.msg("cache create当前线程:" + Thread.currentThread().getName());

                Logger.e("cache create当前线程:" + Thread.currentThread().getName());
                RxUser data = new RxUser();
                // 在操作符 concat 中，只有调用 onComplete 之后才会执行下一个 Observable
                if (data != null) { // 如果缓存数据不为空，则直接读取缓存数据，而不读取网络数据
                    e.onNext(data);
                } else {
                    e.onComplete();
                }
            }
        }, BackpressureStrategy.BUFFER);

        //读取网络数据的操作
        Flowable<RxUser> network = Flowable.create(new FlowableOnSubscribe<RxUser>() {
            @Override
            public void subscribe(FlowableEmitter<RxUser> e) {
                Utils.msg("network create当前线程:" + Thread.currentThread().getName());
                Logger.e("network create当前线程:" + Thread.currentThread().getName());
                RxUser data = new RxUser();
                e.onNext(data);
            }
        }, BackpressureStrategy.BUFFER);

        //执行
        Flowable.concat(cache, network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RxUser>() {
                    @Override
                    public void accept(RxUser rxUser) {
                        Utils.msg("accept: 读取数据成功:" + Thread.currentThread().getName());
                        Logger.e("accept: 读取数据成功:" + Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Utils.msg("accept: 读取数据失败：" + Thread.currentThread().getName());
                        Logger.e("accept: 读取数据失败：" + Thread.currentThread().getName());
                    }
                });
    }


    /**
     * zip 操作符
     * <p>
     * 合并事件，并以少的为主
     */
    private void rx12() {
        Flowable<String> a = Flowable.create(e -> {
            if (!e.isCancelled()) {
                e.onNext("A");
                Utils.msg("String emit : A ");
                e.onNext("B");
                Utils.msg("String emit : B ");
                e.onNext("C");
                Utils.msg("String emit : C ");
            }
        }, BackpressureStrategy.BUFFER);


        Flowable<Integer> b = Flowable.create(e -> {
            if (!e.isCancelled()) {
                e.onNext(1);
                Utils.msg("Integer emit : 1 ");
                e.onNext(2);
                Utils.msg("Integer emit : 2 ");
                e.onNext(3);
                Utils.msg("Integer emit : 3 ");
                e.onNext(4);
                Utils.msg("Integer emit : 4 ");
                e.onNext(5);
                Utils.msg("Integer emit : 5 ");
            }
        }, BackpressureStrategy.BUFFER);

        Flowable.zip(a, b, (s, integer) -> s + integer)
                .subscribe(s -> Utils.msg("zip : accept : " + s));
    }

    /**
     * zip 操作符
     * <p>
     * 合并事件，并以少的为主
     */
    private void rx13() {

        Flowable<RxUser> f1 = Flowable.create(new FlowableOnSubscribe<RxUser>() {
            @Override
            public void subscribe(FlowableEmitter<RxUser> emitter) {
                RxUser user = new RxUser();
                emitter.onNext(user);
            }
        }, BackpressureStrategy.BUFFER);

        Flowable<RxMain> f2 = Flowable.create(new FlowableOnSubscribe<RxMain>() {
            @Override
            public void subscribe(FlowableEmitter<RxMain> emitter) throws Exception {
                RxMain main = new RxMain();
                Thread.sleep(1000);
                emitter.onNext(main);
            }
        }, BackpressureStrategy.BUFFER);


        Flowable.zip(f1, f2, new BiFunction<RxUser, RxMain, String>() {
            @Override
            public String apply(RxUser rxUser, RxMain rxMain) {
                return "合并后的数据为：姓名:" + rxUser.name + " 内容:" + rxMain.feed;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Utils.msg("accept: 成功：" + s + "\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Utils.msg("accept: 失败：" + throwable + "\n");
                    }
                });

    }

    /**
     * filter 是用于过滤数据的，返回false表示拦截此数据。
     */
    private void rx14() {
        //大于5的数据
        Utils.msg("----大于5的数据----");
        Flowable.fromArray(1, 20, 5, 0, -1, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer >= 5;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Utils.msg(integer.toString());
                    }
                });
    }

    /**
     * take 是用于过滤数据的，只想要x个数据
     */
    private void rx15() {
        //如果我们只想要2个数据:
        Utils.msg("----想要2个数据:----");
        Flowable.fromArray(1, 2, 3, 4)
                .take(2)
                .subscribe(integer -> {
                    Utils.msg(integer.toString());
                });
    }

    /**
     * range
     */
    private void rx16() {
        Utils.msg("----接收到数据前记录日志:----");
        Flowable.range(1, 9)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Utils.msg("处理:" + integer);
                    }
                });
    }

    /**
     * buffer 作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable
     */
    private void rx17() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(integers -> {
                    Utils.msg("buffer size : " + integers.size());
                    Utils.msg("buffer value : ");
                    for (Integer i : integers) {
                        Utils.msg(i + "");
                    }
                }, throwable -> Utils.msg("accept: error :" + throwable.getMessage()));
    }

    /**
     * timer: 相当于一个定时任务,但需要注意的是，timer 和 interval 均默认在新线程。
     */
    private void rx18() {
        Utils.msg("timer start : " + System.currentTimeMillis());
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(time -> Utils.msg("timer :" + time + " at " + System.currentTimeMillis()),
                        throwable -> Utils.msg("timer accept: error :" + throwable.getMessage()));

    }

    /**
     * interval: 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     */
    private void rx19() {
        Utils.msg("interval start : " + System.currentTimeMillis());
        Disposable disposable = Flowable.interval(3, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(time -> Utils.msg("interval :" + time + " at " + System.currentTimeMillis()),
                        throwable -> Utils.msg("interval accept: error :" + throwable.getMessage()));

        //这里定时器会一直执行，当我们需要销毁的时候调用下面方法
        //disposable.dispose();
    }

    /**
     * skip: 接受一个 long 型参数 count ，代表跳过 count 个数目开始接收
     */
    private void rx20() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(
                        integer -> Utils.msg("skip : " + integer),
                        throwable -> Utils.msg("interval accept: error :" + throwable.getMessage()));
    }


    /**
     * Single: 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
     */
    private void rx21() {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Utils.msg("single : onSuccess : " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Utils.msg("single : onError : " + e.getMessage());
                    }
                });

    }

    /**
     * distinct: 去重操作符，简单的作用就是去重。
     */
    private void rx22() {
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(
                        integer -> Utils.msg("distinct : " + integer),
                        e -> Utils.msg("distinct : onError : " + e.getMessage()));

    }

    /**
     * debounce: 去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
     */
    private void rx23() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            // send events with simulated time wait
            emitter.onNext(1); // skip
            Thread.sleep(400);
            emitter.onNext(2); // deliver
            Thread.sleep(505);
            emitter.onNext(3); // skip
            Thread.sleep(100);
            emitter.onNext(4); // deliver
            Thread.sleep(605);
            emitter.onNext(5); // deliver
            Thread.sleep(510);
            emitter.onComplete();
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integer -> Utils.msg("debounce :" + integer),
                        e -> Utils.msg("debounce : onError : " + e.getMessage()));
    }

    /**
     * defer: 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable
     */
    private void rx24() {
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() {
                return Observable.just(1, 2, 3);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Utils.msg("defer : " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Utils.msg("defer : onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Utils.msg("defer : onComplete");
            }
        });
    }

    /**
     * last: 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    private void rx25() {

        //这里defaultItem是什么意思?
        Observable.just(1, 2, 3, 4, 5)
                .last(0)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) {
                        Utils.msg("last : " + integer);
                    }
                });
    }


    /**
     * merge: merge的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
     * 注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
     */
    private void rx26() {
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) {
                        Utils.msg("accept: merge :" + integer);
                    }
                });

    }

    /**
     * reduce: 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
     */
    private void rx27() {
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) {
                Utils.msg("accept: reduce : " + integer);
            }
        });
    }

    /**
     * scan: 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
     */
    private void rx28() {
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) {
                Utils.msg("accept: scan " + integer);
            }
        });
    }

    /**
     * window: 按照实际划分窗口，将数据发送给不同的 Observable
     */
    private void rx29() {
        Utils.msg("window");
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) {
                        Utils.msg("Sub Divide begin...");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(@NonNull Long aLong) {
                                        Utils.msg("Next:" + aLong);
                                    }
                                });
                    }
                });
    }


    /**
     * fromIterable
     */
    private void rx30() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        //from形式
        Flowable.fromIterable(list)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) {
                        Utils.msg(integer.toString());
                    }
                });


        Flowable.just(list)
                .subscribe(nums -> {
                    Observable.fromIterable(nums).subscribe(num -> Utils.msg(num.toString()));
                });
    }


    /******************************操作符 end*************************************/

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
    private void rx31() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) {
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
    private void rx32() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) {
                e.onNext("将会在2秒后显示  " + Utils.isMainThread());
                SystemClock.sleep(2000);
                e.onNext("RxJava你可以随意的切换线程" + Utils.isMainThread());
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        s += "    ";
                        s += Utils.isMainThread();
                        Utils.msg(s);
                    }
                });
    }

    /**
     *
     */
    private void rx33() {

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) {
                Utils.msg("FlowableOnSubscribe - subscribe()    " + Utils.isMainThread());
                e.onNext("");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) {
                        Utils.msg("doOnSubscribe() - accept()   " + Utils.isMainThread());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) {
                        Utils.msg("subscribe() - accept()   " + Utils.isMainThread());
                    }
                });

    }
}