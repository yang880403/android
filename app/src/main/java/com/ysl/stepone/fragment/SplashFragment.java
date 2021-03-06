package com.ysl.stepone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stepone.navigator.Navigator;
import com.ysl.stepone.R;

/**
 * FileName: SplashFragment
 * Author: shiliang
 * Date: 2019-11-26 22:25
 */
public class SplashFragment extends BaseFragment {
    View bottomView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("TT", context.toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSTActivity().setPageTitle("splash view");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        bottomView = view.findViewById(R.id.bottom_view);
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.startOpenPath("first").from(getActivity()).pushRequest().push();
            }
        });
        return view;
    }

    @Override
    public int onCreateView() {
        return R.layout.fragment_splash;
    }
}
