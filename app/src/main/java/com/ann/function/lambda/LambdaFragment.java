package com.ann.function.lambda;

import android.os.Build;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.annotations.NonNull;

/**
 * Lambda 学习专区
 * Oracle学习地址 http://www.oracle.com/technetwork/articles/java/architect-lambdas-part2-2081439.html
 * <p>
 * <p>
 * <p>
 * Author:maxiaolong
 * Date:2018/7/21
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class LambdaFragment extends BaseFragment {


    private static final String TAG = LambdaFragment.class.getSimpleName();
    private StringBuilder s = new StringBuilder();
    private Button mClearBtn;
    private TextView mMsg;
    private Button mBtn;

    public LambdaFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LambdaFragment newInstance() {
        LambdaFragment fragment = new LambdaFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_lambda_layout, container, false);
        mMsg = rootView.findViewById(R.id.tv_msg);


        mClearBtn = rootView.findViewById(R.id.clear);
        mClearBtn.setOnClickListener(view -> {
            s.setLength(0);
            helloEventBus("");
        });
        mBtn = rootView.findViewById(R.id.btn);
        mBtn.setOnClickListener(view -> {

            Utils.msg("\n-------------------------1 源数据 ------------------------");
            print(people);
            Utils.msg("\n-------------------------2------------------------");
            doAction2();
            Utils.msg("\n-------------------------3------------------------");
            doAction3();
            Utils.msg("\n-------------------------4------------------------");
            doAction4();
            Utils.msg("\n-------------------------5 Age >= 21 filter ------------------------");
            doAction5();
            Utils.msg("\n-------------------------6 Age >= 40 filter + forEach ------------------------");
            doAction6();
            Utils.msg("\n-------------------------7 Predicate ------------------------");
            doAction7();
            Utils.msg("\n-------------------------8 mapToInt + sum ------------------------");
            doAction8();
            Utils.msg("\n-------------------------9 reduce ------------------------");
            doAction9();
            Utils.msg("\n-------------------------10 reduce ------------------------");
            doAction10();
            Utils.msg("\n-------------------------11 reduce ------------------------");
            doAction11();
            Utils.msg("\n-------------------------12 parallelStream ------------------------");
            doAction12();

        });
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        s.append(message).append("\n");
        mMsg.setText(s.toString());
    }


    List<Person> people = Arrays.asList(
            new Person("Ted", "Neward", 42),
            new Person("Charlotte", "Neward", 39),
            new Person("Michael", "Neward", 19),
            new Person("Matthew", "Neward", 13),
            new Person("Neal", "Ford", 45),
            new Person("Candy", "Ford", 39),
            new Person("Jeff", "Brown", 43),
            new Person("Betsy", "Brown", 39)
    );

    /**
     * 打印
     */
    private void print(List<Person> list) {
        for (Person p : list) {
            Utils.msg(p.toString());
        }
    }

    /**
     * Comparator接口被@FunctionalInterface修饰，表示这是表达式接口，匿名内部类的new可以用lambda表达式替换
     */
    private void doAction2() {
        Collections.sort(people, Person.BY_LAST_AND_AGE);
        print(people);
    }


    /**
     * ::符号，类似传递方法
     */
    private void doAction3() {
        Collections.sort(people, Person::compareLastAndAge);
        print(people);
    }


    /**
     *
     */
    private void doAction4() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(people, Person.BY_LAST.thenComparing(Person.BY_AGE));
            print(people);
        }

    }

    /**
     * filter 过滤
     */
    private void doAction5() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Person> collect = people.stream().filter(it -> it.getAge() >= 21).collect(Collectors.toList());
            print(collect);
        }
    }

    /**
     * filter + forEach
     */
    private void doAction6() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            people.stream().filter(it -> it.getAge() >= 40).forEach(p -> Utils.msg(p.toString()));
        }
    }


    /**
     * Predicate 能把filter的判断条件抽取出来
     */
    private void doAction7() {
        Predicate<Person> drinkingAge = (it) -> it.getAge() >= 21;
        Predicate<Person> brown = (it) -> it.getLastName().equals("Brown");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            people.stream()
                    .filter(drinkingAge.and(brown))
                    .forEach((it) -> Utils.msg("Have a beer, " + it.getFirstName()));
        }
    }

    /**
     * mapToInt + sum
     */
    private void doAction8() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int i = people.stream().mapToInt(Person::getAge).sum();
            Utils.msg("年龄总和为： " + i);
        }
    }


    /**
     * reduce
     */
    private void doAction9() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
            int sum = values.stream().reduce(0, (l, r) -> l + r);
            Utils.msg(sum + "");
        }
    }

    /**
     * reduce
     */
    private void doAction10() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String xml = "<people data='lastname'>" +
                    people.stream()
                            .map(it -> "<person>" + it.getLastName() + "</person>")
                            .reduce("", String::concat)
                    + "</people>";
            Utils.msg(xml);
        }
    }

    /**
     * reduce
     */
    private void doAction11() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String json =
                    people.stream()
                            .map(Person::toJSON)
                            .reduce("[", (l, r) -> l + (l.equals("[") ? "" : ",") + r)
                            + "]";
            Utils.msg(json);
        }
    }

    /**
     * parallelStream
     */
    private void doAction12() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            people.parallelStream()
                    .filter((it) -> it.getAge() >= 21)
                    .forEach((it) ->
                            Utils.msg("Have a beer " + it.getFirstName() + Thread.currentThread()));
        }
    }


}
