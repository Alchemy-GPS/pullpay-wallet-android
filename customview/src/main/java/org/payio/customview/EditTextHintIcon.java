/**
 * Copyright (c) 2016-2020, 董林栋 (www.implistarry.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.payio.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditTextHintIcon extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private float hintImageSize = 0;
    private float hintTextSize = 0;
    private int hingTextColor = 0xFF000000;
    private int hintGravity;
    private Drawable mDrawable;
    private Paint paint;
    private String hintText;
    private CharSequence content;
    private Handler mHandler;
    private boolean hasFocus;
    private int defaultPaddingLeft;

    public EditTextHintIcon(Context context) {
        super(context);
    }

    public EditTextHintIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitResource(context, attrs);
        InitPaint();
    }

    public EditTextHintIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitResource(context, attrs);
        InitPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditTextHintIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        InitResource(context, attrs);
        InitPaint();
    }


    private void InitResource(Context context, AttributeSet attrs) {
        mHandler = new Handler();
        @SuppressLint("CustomViewStyleable")
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextHintIcon);
        float density = context.getResources().getDisplayMetrics().density;
        hintImageSize = mTypedArray.getDimension(R.styleable.EditTextHintIcon_imageSize, 18 * density + 0.5F);
        mDrawable = mTypedArray.getDrawable(R.styleable.EditTextHintIcon_hintImage);
        if (mDrawable != null)
            mDrawable.setBounds(0, 0, (int) hintImageSize, (int) hintImageSize);
        hintGravity = mTypedArray.getInt(R.styleable.EditTextHintIcon_hintGravity, 1);
        hintText = mTypedArray.getString(R.styleable.EditTextHintIcon_hintText);
        hingTextColor = mTypedArray.getColor(R.styleable.EditTextHintIcon_hintTextColor, 0xFF848484);
        hintTextSize = mTypedArray.getDimension(R.styleable.EditTextHintIcon_hintTextSize, 14 * density + 0.5F);
        defaultPaddingLeft = getPaddingLeft();

        Log.e("EditTextHintIcon", "defaultPaddingLeft == " + defaultPaddingLeft);

        mTypedArray.recycle();
    }

    private void InitPaint() {
        if (paint == null)
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(hingTextColor);
        paint.setTextSize(hintTextSize);

        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasFocus) {
            drawFocusHint(canvas);
        } else {
            drawHint(canvas);
        }
    }

    private void drawHint(Canvas canvas) {
        if (this.getText().toString().length() == 0) {
            float textWidth = paint.measureText(hintText);
            float textHeight = getFontLeading(paint);
            float dx = hintImageSize + textWidth + 10;
            float dy = (getHeight() - hintImageSize) / 2;
            float transX;
            if (hintGravity == 1) {
                transX = (getWidth() - dx) / 2;
            } else {
                transX = defaultPaddingLeft;
            }

            canvas.save();

            canvas.translate(transX, getScrollY() + dy);
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
            canvas.drawText(hintText, getScrollX() + hintImageSize + 20, (getHeight() - (paint.getFontMetrics().descent - paint.getFontMetrics().ascent)) / 2 - paint.getFontMetrics().ascent - dy, paint);
            canvas.restore();
        } else {
            drawFocusHint(canvas);
        }
    }

    private void drawFocusHint(Canvas canvas) {
        float textWidth = paint.measureText(hintText);
        float textHeight = getFontLeading(paint);
        float dx = hintImageSize + textWidth + 10;
        float dy = (getHeight() - hintImageSize) / 2;

        canvas.save();

        canvas.translate(defaultPaddingLeft, getScrollY() + dy);

        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }

//        canvas.drawText(hintText, getScrollX() + hintImageSize + 20, getScrollY() + (getHeight() - (getHeight() - textHeight) / 2) - paint.getFontMetrics().bottom - dy-6, paint);
        if (TextUtils.isEmpty(content)) {
            canvas.drawText(hintText, getScrollX() + hintImageSize + 20, (getHeight() - (paint.getFontMetrics().descent - paint.getFontMetrics().ascent)) / 2 - paint.getFontMetrics().ascent - dy, paint);
        } else {
            canvas.drawText("", getScrollX() + hintImageSize + 20, (getHeight() - (paint.getFontMetrics().descent - paint.getFontMetrics().ascent)) / 2 - paint.getFontMetrics().ascent - dy, paint);
        }

        canvas.restore();

        setPadding((int) (hintImageSize + defaultPaddingLeft + 10), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    public void setHintText(String hint) {
        this.hintText = hint;
        updateUI();
    }

    public void setHintTextSize(int hintSize) {
        this.hintTextSize = hintSize;
        InitPaint();
        updateUI();
    }

    public void setHintTxtColor(int hintColor) {
        this.hingTextColor = hintColor;
        InitPaint();
        updateUI();
    }

    public void setHintIcon(Drawable hintIcon) {
        this.mDrawable = hintIcon;
        if (mDrawable != null)
            mDrawable.setBounds(0, 0, (int) hintImageSize, (int) hintImageSize);
        updateUI();
    }

    public void setHintIconSize(int hintIconSize) {
        this.hintImageSize = hintIconSize;
        updateUI();
    }

    private void updateUI() {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if (mDrawable == null) {
//            try {
//                mDrawable = getContext().getResources().getDrawable(R.drawable.search);
//                mDrawable.setBounds(0, 0, (int) hintImageSize, (int) hintImageSize);
//            } catch (Exception e) {
//
//            }
//        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            mDrawable = null;
        }
        super.onDetachedFromWindow();
    }

    public float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }

    OnFocusListener listener;
    public interface OnFocusListener{
        void onFocusChange(View v, boolean hasFocus);
    }

    public void setOnFocusListener(OnFocusListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        updateUI();
        if (listener != null) {
            listener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.content = s;
        updateUI();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

