package com.ann.designpattern.create.factory.simple;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.ann.function.rx.RxFragment;

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
            "Groovy",
            "Thread",
            "Thread-DeadLock",
            "Thread-ForkJoinTask",
            "Thread-ThreadPool",
            "Thread-JniPosix",
            "Rx",
            "Lambda",
            "Decorator",
            "Try Catch Condition",
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
            "View-Step",
            "View-DataBinding",
            "Net-Okhttp",
            "Fun-Leaks",
            "Launch-Mode",
            "Broad-Cast",
            "Fun-Memory",
            "Fun-StaticInitIndex",
            "Fun-MultipleExtends",
            "Fun-Volatile",
            "FUN-Aidl",
            "Fun-NDK",
            "FUN-NDK-Split-Merge",
            "FUN-NDK-Bsdiff-Patch",
            "FUN-NDK-Hotfix",
            "FUN-NDK-Openespaly"

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
//            case "Groovy":
//                f = GroovyFragment.newInstance();
//                break;
//            case "Thread":
//                f = ThreadFragment.newInstance();
//                break;
//            case "Thread-DeadLock":
//                f = DeadLockFragment.newInstance();
//                break;
//            case "Thread-ForkJoinTask":
//                f = ForkJoinTaskFragment.newInstance();
//                break;
//            case "Thread-ThreadPool":
//                f = ThreadPoolFragment.newInstance();
//                break;
//            case "Thread-JniPosix":
//                f = JniPosixFragment.newInstance();
//                break;
            case "Rx":
                f = RxFragment.newInstance();
//                break;
//            case "Lambda":
//                f = LambdaFragment.newInstance();
//                break;
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