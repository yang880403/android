package com.ysl.stepone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stepone.uikit.AbsSTActivity;
import com.stepone.uikit.AbsSTFragment;
import com.ysl.stepone.R;

/**
 * FileName: SplashFragment
 * Author: shiliang
 * Date: 2019-11-26 22:25
 */
public class SplashFragment extends AbsSTFragment {
    View bottomView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        bottomView = view;
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbsSTActivity act = (AbsSTActivity) getActivity();
                act.startLoading("123");
            }
        });
        return view;
    }
}
