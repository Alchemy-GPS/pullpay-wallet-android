package org.payio.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.payio.wallet.mvp.login.LoginActivity;
import org.payio.wallet.mvp.main.MainActivity;
import org.payio.wallet.params.User;
import org.payio.wallet.utils.SharedPreference;

import static org.payio.wallet.params.TransParam.LAUNCH_RXTRA_BUNDLE;

public class WelcomeActivity extends Activity {
    private long startTime;

    private long delayTime = 600;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startTime = System.currentTimeMillis();

        bundle = getIntent().getBundleExtra(LAUNCH_RXTRA_BUNDLE);
        boolean hasLogin = SharedPreference.get(this).getBoolean(User.USER_IS_LOGIN, false);

        if (hasLogin) {
            delayStartMainActivity();
        } else {
            delayStartLoginActivity();
        }
    }

    private void delayStartMainActivity() {
        long endTime = System.currentTimeMillis();
        if (endTime - startTime < delayTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMainActivity();
                }
            }, delayTime - (endTime - startTime));
        } else {
            startMainActivity();
        }
    }

    private void delayStartLoginActivity() {
        long endTime = System.currentTimeMillis();
        if (endTime - startTime < delayTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoginActivity();
                }
            }, delayTime - (endTime - startTime));
        } else {
            startLoginActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        if (bundle != null) {
            intent.putExtra(LAUNCH_RXTRA_BUNDLE, bundle);
        }
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }
}
