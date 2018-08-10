package com.mlx.android.learnandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorzontalScrollViewEx extends ViewGroup {

    private static final String TAG = "HorzontalScrollViewEx";

    private int mChildrenSize;
    private int mChildrenWidth;
    private int mChildrenIndex;
    /**
     * 分别记录上次滑动的坐标
     */
    private int mLastX = 0;
    private int mLastY = 0;
    /**
     * 分别记录上次滑动的坐标（onInterceptTochEvent）
     */
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorzontalScrollViewEx(Context context) {
        super(context);
    }

    public HorzontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorzontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                intercepted = Math.abs(deltaX) > Math.abs(deltaY);
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        Log.d(TAG, "intercepted=" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = ((int) event.getX());
        int y = ((int) event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int delatX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-delatX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildrenWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildrenIndex = xVelocity > 0 ? mChildrenIndex - 1 : mChildrenIndex + 1;
                } else {
                    mChildrenIndex = (scrollX + mChildrenWidth / 2) / mChildrenWidth;
                }
                mChildrenIndex = Math.max(0, Math.min(mChildrenIndex, mChildrenSize - 1));
                int dx = mChildrenIndex * mChildrenWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
