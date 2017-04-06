package com.ex.administrator.zhanhui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.administrator.zhanhui.R;

import org.xutils.x;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class OneOrderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_one, null);
        x.view().inject(this, view);
        return view;
    }
}
