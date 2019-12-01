package com.ysl.stepone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stepone.uikit.AbsSTActivity;
import com.stepone.uikit.AbsSTViewContainer;
import com.ysl.stepone.R;
import com.ysl.stepone.activity.SplashAcitivity;

/**
 * FileName: SplashViewContainer
 * Author: shiliang
 * Date: 2019-11-26 22:25
 */
public class SplashViewContainer extends AbsSTViewContainer {
    View bottomView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        bottomView = view.findViewById(R.id.bottom_view);
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SplashAcitivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getSTActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int onCreateView() {
        return R.layout.fragment_splash;
    }
}