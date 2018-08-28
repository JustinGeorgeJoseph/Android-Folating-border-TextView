/*
 * The MIT License
 *
 * Copyright (c) 2018. Justin George(justingeorgejoseph@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the “Software”), to deal in the
 * Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE
 *
 */

package com.justin.floatingdashbordertextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class FloatingDashBorderTextView extends android.support.v7.widget.AppCompatTextView {

    //Paint dashes based on the Phase value
    private int mPhase = 1;
    //Specify the dash width around the textView
    private int mDashWidth = 5;
    //Color for the dashes
    private int mDashColor = Color.BLUE;
    //Specify the dash length, this will be used for the distance between each dashes.
    private float mDashLength = 10.0f;
    //Specify the distance between each dashe
    private float mDashDistance = 10.0f;
    //Float the dashes around the textView. Default value is true
    private boolean mFloatDash = true;
    //float the dashes in clockwise or Anti-Clockwise.
    private boolean mFloatClockWise = false;
    //Paint to draw the dash line around the textView
    private Paint mDashPaint = null;

    public FloatingDashBorderTextView(Context context) {
        super(context);
    }

    public FloatingDashBorderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context, attrs, 0);
    }

    public FloatingDashBorderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context, attrs, defStyleAttr);
    }

    /**
     * Initialize the view
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initializeView(final Context context, final AttributeSet attrs, final int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.FloatingDashBorderTextView, 0, 0);

        try {
            mDashWidth = (int) typedArray.getDimension(R.styleable.FloatingDashBorderTextView_dashWidth, 5);
            mDashColor = typedArray.getInteger(R.styleable.FloatingDashBorderTextView_dashColor, Color.BLUE);
            mDashLength = typedArray.getDimension(R.styleable.FloatingDashBorderTextView_dashLength, 10.0f);
            mDashDistance = typedArray.getDimension(R.styleable.FloatingDashBorderTextView_dashDistance, 10.0f);
            mFloatDash = typedArray.getBoolean(R.styleable.FloatingDashBorderTextView_floatDash, true);
            mFloatClockWise = typedArray.getBoolean(R.styleable.FloatingDashBorderTextView_floatClockWise, false);
        } finally {
            typedArray.recycle();
        }

        mDashPaint = new Paint();
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setStrokeWidth(mDashWidth);
        mDashPaint.setColor(mDashColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mDashPaint.setPathEffect(new DashPathEffect(new float[]{mDashLength,mDashDistance}, mPhase));
        canvas.drawPath(getPathValue(), mDashPaint);
        if (mFloatDash)
        postInvalidate();
        super.onDraw(canvas);
    }

    private Path getPathValue() {
        if (mFloatClockWise) {
            mPhase--;
        } else {
            mPhase++;
        }

        Path path = new Path();
        path.moveTo(0,((int)mDashWidth/2));
        path.lineTo(getMeasuredWidth()-((int)mDashWidth/2),((int)mDashWidth/2)+2);
        path.lineTo(getMeasuredWidth()-((int)mDashWidth/2),getMeasuredHeight()-((int)mDashWidth/2));
        path.lineTo(((int)mDashWidth/2),getMeasuredHeight()-((int)mDashWidth/2));
        path.lineTo(((int)mDashWidth/2),0);
        return path;
    }
}
