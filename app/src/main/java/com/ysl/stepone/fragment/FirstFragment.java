package com.ysl.stepone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stepone.component.common.ActivityHooker;
import com.stepone.component.navigator.Navigator;
import com.stepone.uikit.view.STFragment;
import com.ysl.stepone.R;

/**
 * FileName: FirstFragment
 * Author: y.liang
 * Date: 2019-12-05 11:08
 */

public class FirstFragment extends STFragment {
    View bottomView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getSTActivity().setPageTitle("first view");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        bottomView = view.findViewById(R.id.bottom_view);
        if (bottomView != null) {
            bottomView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.startOpenPath("second").from(getActivity()).callForResult(new ActivityHooker.OnActivityResultCallback() {
                        @Override
                        public void onActivityResult(int resultCode, Intent data) {
                            if (data != null) {
                                String str = data.getStringExtra("title");
                                if (str != null) {
                                    getSTActivity().setPageTitle(str);
                                }
                            }
                        }
                    });
                }
            });
        }
        return view;
    }

    @Override
    public int onCreateView() {
        return R.layout.fragment_splash;
    }
}
