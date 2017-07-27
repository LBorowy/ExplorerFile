package pl.lborowy.nextapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by RENT on 2017-07-27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final int ENTER_ANIM = android.R.anim.slide_in_left;
    private static final int EXIT_ANIM = android.R.anim.slide_out_right;
    protected String TAG;

    protected void openFragment(Fragment fragment, boolean canGoBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(ENTER_ANIM, EXIT_ANIM, ENTER_ANIM, EXIT_ANIM);
        if (canGoBack) {
            transaction.add(R.id.mainActivity_fragmentContainer, fragment);
            transaction.addToBackStack(null);
        } else
            transaction.replace(R.id.mainActivity_fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TAG = getClass().getSimpleName();
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.i(TAG, "onAttachFragment");
    }

}
