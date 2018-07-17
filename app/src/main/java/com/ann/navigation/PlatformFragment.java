package com.ann.navigation;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ann.BaseFragment;
import com.ann.R;
import com.ann.designpattern.create.factory.simple.FragmentFactory;
import com.ann.function.FunctionActivity;
import com.ann.function.threadmanager.ThreadManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;

@SuppressLint("InflateParams")
public class PlatformFragment extends BaseFragment {

    private PullToRefreshListView mListView;
    private PlatformAdapter mAdapter;

    public static PlatformFragment newInstance() {
        PlatformFragment fragmentOne = new PlatformFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", fragmentOne.getClass().getSimpleName());
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_platform, null);
        initView(view);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    public void initView(View view) {

        TextView title = view.findViewById(R.id.tv_title);
        title.setText(getString(R.string.navigation_title_platform_title));

        mListView = view.findViewById(R.id.ll_listview);
        mListView.setEmptyView(view.findViewById(R.id.empty));
        mListView.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Logger.d("onItemClick() : pos=" + position + ", id=" + id);
            String item = (String) parent.getAdapter().getItem(position);
            FunctionActivity.startActivity(getContext(), item);
        });

        mListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {

            @Override
            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state,
                                    PullToRefreshBase.Mode direction) {

                Logger.d("onPullEvent() : " + state.name());

//                if (state.equals(PullToRefreshBase.State.PULL_TO_REFRESH)) {
//                    refreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.pull_to_refresh));
//                    refreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.release_to_refresh));
//                    refreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.loading));
//
//                    String label = DateUtils.formatDateTime(
//                            getContext().getApplicationContext(),
//                            System.currentTimeMillis(),
//                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//                    // Update the LastUpdatedLabel
//                    refreshView.getLoadingLayoutProxy()
//                            .setLastUpdatedLabel(getString(R.string.updated) + " : " + label);
//                }
            }
        });

        mListView.setOnRefreshListener(refreshView -> {
            Logger.d("onRefresh()");
            mListView.postDelayed(() -> {
                loadData();

                refreshView.onRefreshComplete();
            }, 800);
        });

        mAdapter = new PlatformAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }

    /**
     * 加载数据
     */
    private void loadData() {

        ThreadManager.getPoolProxy().execute(() -> {
            ArrayList<String> data = new ArrayList(FragmentFactory.TITLES.length);
            Collections.addAll(data, FragmentFactory.TITLES);
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
        });



//        getActivity().runOnUiThread(() -> {
//            ArrayList<String> data = new ArrayList(FragmentFactory.TITLES.length);
//            Collections.addAll(data, FragmentFactory.TITLES);
//            mAdapter.setData(data);
//            mAdapter.notifyDataSetChanged();
//        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
