package saveinstance.test.com.testsaveinstance;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @author ht
 * @date Created on 2017/8/7
 * @description
 */

public class FragmentStateLossActivity extends Activity {
    private boolean mStateSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateSaved = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 不调用super对我们意义不大，还是会崩溃，而且会丢失现场
        super.onSaveInstanceState(outState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mStateSaved = true;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mStateSaved = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mStateSaved = true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        mStateSaved = false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!mStateSaved) {
            return super.onKeyDown(keyCode, event);
        } else {
            // State already saved, so ignore the event
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        if (!mStateSaved) {
            super.onBackPressed();
        }
    }
}