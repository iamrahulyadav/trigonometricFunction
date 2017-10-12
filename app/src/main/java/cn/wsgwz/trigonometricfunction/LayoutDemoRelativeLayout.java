package cn.wsgwz.trigonometricfunction;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by admin on 2017/10/12 0012.
 */

public class LayoutDemoRelativeLayout extends FrameLayout {
    private static final String TAG = LayoutDemoRelativeLayout.class.getSimpleName();

    private ConstraintLayout constraintLayoutParent;

    private RelativeLayout actionBarRL;

    private LinearLayout hideLL;

    private ScrollView scrollView;

    private float lastY;

    private  Rect scrollViewRect = new Rect();
    private float scrollViewTop;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev) {
        //Log.d(TAG, "onInterceptTouchEvent "+String.valueOf(ev.getY()));
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                Log.d(TAG, "onInterceptTouchEvent  MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();

                //Log.d(TAG,y+" "+lastY);


                scrollViewRect.set(scrollView.getLeft(), scrollView.getTop(),scrollView.getRight(),scrollView.getBottom());
                scrollView.layout(scrollViewRect.left,(int) y,scrollViewRect.right,scrollViewRect.bottom);

                lastY = y;

                if((y-lastY)>10){ //下划
                   //return true;
                }else if((lastY-y)>10) {//上划
                   //return true;
                }




                break;



            case MotionEvent.ACTION_UP:

                    TranslateAnimation innerAnim = new TranslateAnimation(0, 0, scrollView.getTop(), scrollViewTop);
                    innerAnim.setDuration(200);
                    scrollView.startAnimation(innerAnim);
                    scrollView.layout(scrollViewRect.left, (int) scrollViewTop, scrollViewRect.right, scrollViewRect.bottom);



                Log.d(TAG, "onInterceptTouchEvent  MotionEvent.ACTION_UP");
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "onInterceptTouchEvent  MotionEvent.ACTION_POINTER_DOWN"+ev.getPointerId(ev.getActionIndex()));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onInterceptTouchEvent  MotionEvent.ACTION_POINTER_UP");
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG," onLayout");

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_demo_layout, this);

        constraintLayoutParent = view.findViewById(R.id.parent);

        actionBarRL = view.findViewById(R.id.actionBarRL);

        hideLL = view.findViewById(R.id.hideLL);

        scrollView = view.findViewById(R.id.scrollView);

      /*  scrollView.setOnGenericMotionListener(new OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
                scrollViewTop = scrollView.getTop();
                Log.d(TAG,"scrollView onGenericMotion");
                return false;
            }
        });*/
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollViewTop = scrollView.getTop();
                Log.d(TAG,"scrollView onGlobalLayout");
            }
        });

    }

    public LayoutDemoRelativeLayout(Context context) {
        super(context);
    }

    public LayoutDemoRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutDemoRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayoutDemoRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



}
