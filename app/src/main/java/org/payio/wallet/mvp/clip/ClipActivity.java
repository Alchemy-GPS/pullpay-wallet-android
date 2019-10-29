package org.payio.wallet.mvp.clip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import org.payio.customview.clipview.ClipViewLayout;
import org.payio.wallet.R;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.FileUtils;
import org.payio.wallet.utils.Log;

import java.io.File;

import static org.payio.wallet.params.TransParam.CLIP_HEAD_IMAGE_PATH;

public class ClipActivity extends Activity implements View.OnClickListener {
    private ClipViewLayout mClipLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        RelativeLayout mTitleBack = findViewById(R.id.clip_head_title_back);
        RelativeLayout mClip = findViewById(R.id.clip_head_clip);
        mClipLayout = findViewById(R.id.clip_layout);

        mClip.setOnClickListener(this);
        mTitleBack.setOnClickListener(this);

        mClipLayout.setImageSrc(getIntent().getData());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clip_head_clip) {
            clip();
        } else if (v.getId() == R.id.clip_head_title_back) {
            this.finish();
        }
    }

    private void clip() {
        Bitmap cropBitmap = mClipLayout.clip();
        if (cropBitmap == null) {
            Log.e("cropBitmap == null");
            return;
        }

        File clipIcon = FileUtils.createFileInExternalFiles(ClipActivity.this, "clip_head.jpg");
        String clipIconPath = clipIcon.getAbsolutePath();

        CommonUtil.saveBitmapFile(cropBitmap, clipIcon);
        Intent intent = new Intent();
        intent.putExtra(CLIP_HEAD_IMAGE_PATH, clipIconPath);
        setResult(Activity.RESULT_OK, intent);
        ClipActivity.this.finish();
    }
}
