package com.stepone.uikit.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FileName: STFragment
 * Author: y.liang
 * Date: 2019-11-27 13:34
 */

/**
 * 帮助开发者更好的基于Fragment进行APP开发
 * 1、提供抽象的onCreateView方法，方便开发者通过传递layoutID来配置页面，同时也允许开发者使用原来的方法
 * 2、提供通用的方法，方便开发者直接调用STActivity的方法
 */
abstract public class STFragment extends Fragment {
    private STActivity mSTActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof STActivity) {
            mSTActivity = ((STActivity) context);
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
        return inflater.inflate(layoutResID, container, false);
    }

    abstract public int onCreateView();

    public STActivity getSTActivity() {
        return mSTActivity;
    }
}
