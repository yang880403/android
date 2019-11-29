package com.stepone.uikit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FileName: AbsSTViewContainer
 * Author: y.liang
 * Date: 2019-11-27 13:34
 */

abstract public class AbsSTViewContainer extends Fragment {
    private AbsSTActivity mSTActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AbsSTActivity) {
            mSTActivity = ((AbsSTActivity) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSTActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResID = onCreateView();
        View view = inflater.inflate(layoutResID, container, false);
        return view;
    }

    abstract public int onCreateView();

    public AbsSTActivity getSTActivity() {
        return mSTActivity;
    }
}
