package com.stepone.component.common;

import android.content.Intent;
import android.os.Bundle;
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

    public static void startActivityForResult(FragmentActivity activity, Intent intent, OnActivityResultCallback onActivityResultCallback) {
        OnActivityResultDispatcherFragment fragment = getDispatcherFragment(activity);
        if (fragment != null) {
            fragment._startForResult(intent, onActivityResultCallback);
        }
    }

    private static OnActivityResultDispatcherFragment getDispatcherFragment(@NonNull FragmentActivity activity) {
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(OnActivityResultDispatcherFragment.TAG);
        if (fragment == null) {
            fragment = new OnActivityResultDispatcherFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, OnActivityResultDispatcherFragment.TAG)
                    .commitAllowingStateLoss();
        }

        return (OnActivityResultDispatcherFragment)fragment;
    }

    public interface OnActivityResultCallback {
        void onActivityResult(int resultCode, Intent data);
    }

    private static class OnActivityResultDispatcherFragment extends Fragment {
        final static String TAG = "OnActivityResultDispatchFragment";

        final SparseArray<OnActivityResultCallback> mCallbacks = new SparseArray<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        void _startForResult(Intent intent, OnActivityResultCallback onActivityResultCallback) {
            int requestCode = -1;
            if (onActivityResultCallback != null) {
                requestCode = onActivityResultCallback.hashCode();
                mCallbacks.put(requestCode, onActivityResultCallback);
            }
            startActivityForResult(intent, requestCode);
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
