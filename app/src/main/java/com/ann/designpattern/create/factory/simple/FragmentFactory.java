package com.ann.designpattern.create.factory.simple;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ann.function.io.IOFragment;
import com.ann.function.io.aio.AIOFragment;
import com.ann.function.io.bio.BIOFragment;
import com.ann.function.io.nio.NIOFragment;
import com.ann.function.io.okio.OKIOFragment;
import com.ann.function.lambda.LambdaFragment;
import com.ann.function.ping.PingFragment;
import com.ann.function.rx.RxFragment;
import com.ann.function.thread.ThreadFragment;

/**
 * 简单工厂类
 * <p>
 * Author:maxiaolong
 * Date:2018/7/13
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class FragmentFactory {

    public static final String[] TITLES = new String[]{
            "Algorithm",
            "Sort-Bubble",
            "Sort-Insertion",
            "Sort-BinaryInsertion",
            "Sort-Selection",
            "Sort-Shell",
            "Sort-MergeRecursive",
            "Sort-MergeNonRecursive",
            "Sort-SortHeap",
            "Sort-SortQuick",
            "Data-Structure",
            "DP-MethodFactory",
            "DP-AbstractFactory ",
            "DP-Builder",
            "DP-Adapter",
            "DP-Decorator",
            "DP-Proxy",
            "DP-Dynamic_Proxy",
            "DP-Facade",
            "DP-Bridge",
            "DP-Component",
            "DP-Chain",
            "DP-TemplateMethod",
            "Fun-IO",
            "Fun-Groovy",
            "Fun-Thread",
            "Fun-Thread-ForkJoinTask",
            "Fun-Thread-ThreadPool",
            "Fun-Thread-JniPosix",
            "Fun-Rx",
            "Fun-Lambda",
            "Fun-Decorator",
            "Fun-Try Catch Condition",
            "Fun-View-Step",
            "Fun-View-DataBinding",
            "Fun-Net-Okhttp",
            "Fun-Launch-Mode",
            "Fun-Broad-Cast",
            "Fun-Leaks",
            "Fun-Memory",
            "Fun-StaticInitIndex",
            "Fun-MultipleExtends",
            "Fun-Volatile",
            "Fun-Aidl",
            "Fun-Ping",
            "Fun-NDK",
            "Fun-NDK-Split-Merge",
            "Fun-NDK-Bsdiff-Patch",
            "Fun-NDK-Hotfix",
            "Fun-NDK-Openespaly",

    };


    public static final String[] SUB_TITLES = new String[]{
            "Fun-BIO",
            "Fun-NIO",
            "Fun-AIO",
            "Fun-OKIO",
    };


    /**
     * 根据名称创建 Fragment
     *
     * @return
     */
    public static Fragment createFragment(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        Fragment f = null;
        switch (key) {
//            case "Algorithm":
//                f = AlgorithmFragment.newInstance();
//                break;
//            case "Sort-Selection":
//                f = SortFragment.newInstance(new SortSelection());
//                break;
//            case "Sort-Insertion":
//                f = SortFragment.newInstance(new SortInsert());
//                break;
//            case "Sort-BinaryInsertion":
//                f = SortFragment.newInstance(new SortBinaryInsert());
//                break;
//            case "Sort-Shell":
//                f = SortFragment.newInstance(new SortShell());
//                break;
//            case "Sort-Bubble":
//                f = SortFragment.newInstance(new SortBubble());
//                break;
//            case "Sort-MergeRecursive":
//                f = SortFragment.newInstance(new SortMergeRecursive());
//                break;
//            case "Sort-MergeNonRecursive":
//                f = SortFragment.newInstance(new SortMergeNonRecursive());
//                break;
//            case "Sort-SortHeap":
//                f = SortFragment.newInstance(new SortHeap());
//                break;
//            case "Sort-SortQuick":
//                f = SortFragment.newInstance(new SortQuick());
//                break;
            case "Fun-IO":
                f = IOFragment.newInstance();
                break;
            case "Fun-BIO":
                f = BIOFragment.newInstance();
                break;
            case "Fun-NIO":
                f = NIOFragment.newInstance();
                break;
            case "Fun-AIO":
                f = AIOFragment.newInstance();
                break;
            case "Fun-OKIO":
                f = OKIOFragment.newInstance();
                break;
//            case "Groovy":
//                f = GroovyFragment.newInstance();
//                break;
            case "Fun-Thread":
                f = ThreadFragment.newInstance();
                break;
//            case "Thread-ForkJoinTask":
//                f = ForkJoinTaskFragment.newInstance();
//                break;
//            case "Thread-ThreadPool":
//                f = ThreadPoolFragment.newInstance();
//                break;
//            case "Thread-JniPosix":
//                f = JniPosixFragment.newInstance();
//                break;
            case "Fun-Rx":
                f = RxFragment.newInstance();
                break;
            case "Fun-Lambda":
                f = LambdaFragment.newInstance();
                break;
//            case "Decorator":
//                f = DecoratorFragment.newInstance();
//                break;
//            case "Data-Structure":
//                f = StructureFragment.newInstance();
//                break;
//            case "Try Catch Condition":
//                f = TryCatchFragment.newInstance();
//                break;
//            case "DP-AbstractFactory ":
//                f = AbstractFactoryFragment.newInstance();
//                break;
//            case "DP-MethodFactory ":
//                f = MethodFactoryFragment.newInstance();
//                break;
//            case "DP-Builder":
//                f = BuilderFragment.newInstance();
//                break;
//            case "DP-Adapter":
//                f = AdapterFragment.newInstance();
//                break;
//            case "DP-Decorator":
//                f = DecoratorFragment.newInstance();
//                break;
//            case "DP-Proxy":
//                f = ProxyFragment.newInstance();
//                break;
//            case "DP-Facade":
//                f = FacadeFragment.newInstance();
//                break;
//            case "DP-Bridge":
//                f = BridgeFragment.newInstance();
//                break;
//            case "DP-Component":
//                f = ComponentFragment.newInstance();
//                break;
//            case "View-Step":
//                f = ViewFragment.newInstance();
//                break;
//            case "View-DataBinding":
//                f = DataBindingFragment.newInstance();
//                break;
//            case "Net-Okhttp":
//                f = NetworkFragment.newInstance();
//                break;
//            case "DP-Chain":
//                f = ChainFragment.newInstance();
//                break;
//            case "Fun-Leaks":
//                f = LeakFragment.newInstance();
//                break;
//            case "Launch-Mode":
//                f = LaunchModeFragment.newInstance();
//                break;
//            case "Broad-Cast":
//                f = BroadCastFragment.newInstance();
//                break;
//            case "Fun-Memory":
//                f = MemoryFragment.newInstance();
//                break;
//            case "DP-Dynamic_Proxy":
//                f = DynamicProxyFragment.newInstance();
//                break;
//            case "DP-TemplateMethod":
//                f = TemplateMethodFragment.newInstance();
//                break;
//            case "Fun-StaticInitIndex":
//                f = StaticInitIndexFragment.newInstance();
//                break;
//            case "Fun-MultipleExtends":
//                f = MultipleExtendsFragment.newInstance();
//                break;
//            case "Fun-Volatile":
//                f = VolatileFragment.newInstance();
//                break;
//            case "FUN-Aidl":
//                f = AidlFragment.newInstance();
//                break;
            case "Fun-Ping":
                f = PingFragment.newInstance();
                break;
//            case "Fun-NDK":
//                f = NDKFragment.newInstance();
//                break;
//            case "FUN-NDK-Split-Merge":
//                f = SplitsMergeFragment.newInstance();
//                break;
//            case "FUN-NDK-Bsdiff-Patch":
//                f = PatchFragment.newInstance();
//                break;
//            case "FUN-NDK-Hotfix":
//                f = HotFixFragment.newInstance();
//                break;
//            case "FUN-NDK-Openespaly":
//                f = OpenESFragment.newInstance();
//                break;

            default:
                break;
        }
        return f;
    }

}
