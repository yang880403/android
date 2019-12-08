package com.stepone.navigator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * FileName: ActivityHooker
 * Author: shiliang
 * Date: 2019-12-03 23:44
 */
public class ActivityHooker {

    public static void startActivityForResult(FragmentActivity activity, final Intent intent, final OnActivityResultCallback onActivityResultCallback) {
        final OnActivityResultDispatcherFragment fragment = getDispatcherFragment(activity);
        if (fragment != null) {
            /**
             * 将任务放到looper中，以确保fragment事务的执行顺序
             */
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    fragment._startForResult(intent, onActivityResultCallback);
                }
            });
        }
    }

    private static OnActivityResultDispatcherFragment getDispatcherFragment(@NonNull FragmentActivity activity) {
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(OnActivityResultDispatcherFragment.TAG);
        if (fragment == null) {
            fragment = new OnActivityResultDispatcherFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, OnActivityResultDispatcherFragment.TAG)
                    .commit();
        }

        return (OnActivityResultDispatcherFragment)fragment;
    }

    public interface OnActivityResultCallback {
        void onActivityResult(int resultCode, Intent data);
    }

    public static class OnActivityResultDispatcherFragment extends Fragment {
        final static String TAG = "OnActivityResultDispatchFragment";

        final SparseArray<OnActivityResultCallback> mCallbacks = new SparseArray<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        void _startForResult(Intent intent, OnActivityResultCallback onActivityResultCallback) {
            int requestCode = 0xFFFF;
            if (onActivityResultCallback != null) {
                //requestCode取值范围只能是[0, 2^16-1]
                requestCode = onActivityResultCallback.hashCode() % 0xFFFF;
                mCallbacks.put(requestCode, onActivityResultCallback);
            }
            if (isAdded()) {
                startActivityForResult(intent, requestCode);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            OnActivityResultCallback onActivityResultCallback = mCallbacks.get(requestCode);
            if (onActivityResultCallback != null) {
                mCallbacks.remove(requestCode);
                onActivityResultCallback.onActivityResult(resultCode, data);
            }
        }
    }
}
