package org.payio.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TitleTextView extends TextView {

    public TitleTextView(Context context) {
        super(context);
        init(context, null, 0);
    }
    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }
    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "title.otf");
        setTypeface(typeface);
    }
}
