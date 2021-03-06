package com.laifusongcai.android.wanandroid.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laifusongcai.android.wanandroid.R;
import com.laifusongcai.android.wanandroid.contract.main.MainContract;

/**
 * @author laifusongcai
 * @date 18-6-25
 */
public class HomeFragment extends Fragment implements MainContract.View{

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        return view;
    }
}
