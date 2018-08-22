package hzyj.come.p2p.mvp.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import hzyj.come.p2p.R;

/**
 * Created by EverGlow on 2018/7/25 15:08
 */


public class DrawableTextView extends AppCompatTextView {

    private Context mContext;
    private int mDrawableleft;
    private int mDrawabletop;
    private int mDrawableright;
    private int mDrawablebottom;
    private int mDrawableWidth;
    private int mDrawableHeight;

    public DrawableTextView(Context context) {
        this(context, null);
    }
    
    
    

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = context.obtainStyledAttributes(attrs, R.styleable.drawableText);
                mDrawableleft = typedArray.getResourceId(R.styleable.drawableText_leftDrawable, 0);
                mDrawabletop = typedArray.getResourceId(R.styleable.drawableText_topDrawable, 0);
                mDrawableright = typedArray.getResourceId(R.styleable.drawableText_rightDrawable, 0);
                mDrawablebottom = typedArray.getResourceId(R.styleable.drawableText_bottomDrawable, 0);
                mDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.drawableText_drawableWidth, 30);
                mDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.drawableText_drawableHeight, 30);
            } finally {
                if (typedArray != null) {
                    typedArray.recycle();
                }
            }
        }
        init();
    }

    private void init() {

        Drawable drawableLeft = null;
        if (mDrawableleft != 0) {
            drawableLeft = ContextCompat.getDrawable(mContext, mDrawableleft);
            drawableLeft.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        Drawable drawableTop = null;
        if (mDrawabletop != 0) {
            drawableTop = ContextCompat.getDrawable(mContext, mDrawabletop);
            drawableTop.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        Drawable drawableRight = null;
        if (mDrawableright != 0) {
            drawableRight = ContextCompat.getDrawable(mContext, mDrawableright);
            drawableRight.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        Drawable drawableBottom = null;
        if (mDrawablebottom != 0) {
            drawableBottom = ContextCompat.getDrawable(mContext, mDrawablebottom);
            drawableBottom.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

}
