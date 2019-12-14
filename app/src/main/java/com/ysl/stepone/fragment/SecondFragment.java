package com.ysl.stepone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ysl.stepone.R;

import static android.app.Activity.RESULT_CANCELED;

/**
 * FileName: SecondFragment
 * Author: y.liang
 * Date: 2019-12-05 11:09
 */

public class SecondFragment extends BaseFragment {
    View bottomView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSTActivity().setPageTitle("second view");
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
                    Intent intent = new Intent();
                    intent.putExtra("title", "123");
                    getActivity().setResult(RESULT_CANCELED, intent);
                    getActivity().finish();
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
