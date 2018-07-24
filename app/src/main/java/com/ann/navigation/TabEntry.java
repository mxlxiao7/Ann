package com.ann.navigation;

import android.support.v4.app.Fragment;

public class TabEntry {

    public Fragment fragment;

    public Class<? extends Fragment> cls;

    public TabEntry() {
    }

    public TabEntry(Fragment fragment, Class<? extends Fragment> cls) {
        this.fragment = fragment;
        this.cls = cls;
    }
}
