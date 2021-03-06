package org.payio.wallet.mvp.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.payio.wallet.R;
import org.payio.wallet.utils.AppUtil;


public class AboutActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        RelativeLayout mTitleBack = findViewById(R.id.about_title_back);
        mTitleBack.setOnClickListener(this);

        TextView mAppName = findViewById(R.id.about_app_name);
        TextView mAppVersion = findViewById(R.id.about_app_version);

        mAppName.setText(AppUtil.getAppName(this));

        mAppVersion.setText(AppUtil.getAppVersionName(this));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.about_title_back) {
            this.finish();
        }
    }
}
