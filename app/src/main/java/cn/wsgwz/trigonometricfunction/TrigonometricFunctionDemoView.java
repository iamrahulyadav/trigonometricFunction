package cn.wsgwz.trigonometricfunction;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewParent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/10/10 0010.
 */

public class TrigonometricFunctionDemoView extends SurfaceView implements Handler.Callback,SurfaceHolder.Callback{
    private static final String TAG = TrigonometricFunctionDemoView.class.getSimpleName();

    private final int screenWidth;
    private final int screenHeight;

    private final static int CIRCLE_ANGLE = 360;
    private final static int CIRCLE_ANGLE_INIT = 0;
    private final static int CIRCLE_ANGLE_CONST = 2;


    private Handler handler;


    private static final int MSG_WHAT_CIRCLE = 1001;


    private Timer circleAngleTimer;
    private int circleAngle = CIRCLE_ANGLE_INIT;

    private SurfaceHolder surfaceHolder;

    private Canvas canvas;
    @Override
    public boolean handleMessage(Message message) {
        switch (message.what){
            case MSG_WHAT_CIRCLE:

                break;
        }
        return false;
    }



    private void draw() throws Exception {
        canvas = surfaceHolder.lockCanvas();

        draw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(Util.dip2px(getContext(),2));


        double initCenterPointX =  screenWidth/2;
        double initCenterPointY =  screenHeight/2;

        //canvas.drawLine(0, (float) initCenterPointY,screenWidth, (float) initCenterPointY,paint);






        if(circleAngle<=180){
            //等腰三角形 相等的两个角的角度
            double trigonometricEdgeAngle = (180 - circleAngle)/2;


            Log.d(TAG,"trigonometricEdgeAngle "+trigonometricEdgeAngle);

            double ABC_B = (Math.cos(Util.angleToRadian((int) trigonometricEdgeAngle))*initCenterPointX)*2;

            Log.d(TAG,"ABC_B "+ABC_B);

            double ABC_A_EdgeAngle = 90-trigonometricEdgeAngle;
            Log.d(TAG,"ABC_A_EdgeAngle "+ABC_A_EdgeAngle);

            double ABC_C =  (Math.sin(Util.angleToRadian((int) ABC_A_EdgeAngle))*ABC_B);
            Log.d(TAG,"ABC_C "+ABC_C);

            double ABC_A = (Math.cos(Util.angleToRadian((int) ABC_A_EdgeAngle))*ABC_B);
            Log.d(TAG,"ABC_A "+ABC_A);


            float startY  = (float)( initCenterPointY-ABC_A );
            canvas.drawLine( (float) ABC_C, startY,(float) initCenterPointX, (float) initCenterPointY,paint);


            /*paint.setStrokeWidth(Util.dip2px(getContext(),1));


            canvas.drawLine( (float) ABC_C, startY,0, startY,paint);

            canvas.drawLine( (float) ABC_C, startY,0, (float) initCenterPointY,paint);*/



            Log.d(TAG,"startY "+startY);
        }else if(circleAngle<=360){
            //等腰三角形 相等的两个角的角度
            double trigonometricEdgeAngle = (180 - (circleAngle-180))/2;


            Log.d(TAG,"trigonometricEdgeAngle "+trigonometricEdgeAngle);

            double ABC_B = (Math.cos(Util.angleToRadian((int) trigonometricEdgeAngle))*initCenterPointX)*2;

            Log.d(TAG,"ABC_B "+ABC_B);

            //A B斜边 C
            double ABC_A_EdgeAngle = 90-trigonometricEdgeAngle;
            Log.d(TAG,"ABC_A_EdgeAngle "+ABC_A_EdgeAngle);

            double ABC_C =  (Math.sin(Util.angleToRadian((int) ABC_A_EdgeAngle))*ABC_B);
            Log.d(TAG,"ABC_C "+ABC_C);

            double ABC_A = (Math.cos(Util.angleToRadian((int) ABC_A_EdgeAngle))*ABC_B);
            Log.d(TAG,"ABC_A "+ABC_A);



            float startY  = (float)( initCenterPointY+ABC_A );

            float startX = (float) (screenWidth-ABC_C);
            canvas.drawLine( startX, startY,(float) initCenterPointX, (float) initCenterPointY,paint);


          /*  paint.setStrokeWidth(Util.dip2px(getContext(),1));


            canvas.drawLine( startX, startY,screenWidth, startY,paint);

            canvas.drawLine( startX, startY,screenWidth, (float) initCenterPointY,paint);*/


            Log.d(TAG,"startY "+startY);
        }



        surfaceHolder.unlockCanvasAndPost(canvas);

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private void init(Context context,@Nullable AttributeSet attrs){
        if(attrs!=null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TrigonometricFunctionDemoView);

            int other = ta.getInt(R.styleable.TrigonometricFunctionDemoView_other,-1);
            Log.d(TAG,"initAttr "+other);
        }
        //if(context instanceof Activity){


       // }

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        handler = new Handler(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        circleAngleTimer = new Timer();
        circleAngleTimer.schedule(new CircleAngleTimerTask(),1000,50);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        circleAngleTimer.cancel();

    }


    private class CircleAngleTimerTask extends TimerTask{
        @Override
        public void run() {

                    circleAngle+=CIRCLE_ANGLE_CONST;
                    if(circleAngle>CIRCLE_ANGLE){
                        //circleAngleTimer.cancel();
                        circleAngle = CIRCLE_ANGLE_INIT;
                        //return;
                    }

                    try {
                        draw();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

    }




    public TrigonometricFunctionDemoView(Context context) {
        super(context);
        Activity activity = (Activity) context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        init(context,null);
    }

    public TrigonometricFunctionDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Activity activity = (Activity) context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        init(context,attrs);
    }

    public TrigonometricFunctionDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Activity activity = (Activity) context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TrigonometricFunctionDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Activity activity = (Activity) context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        init(context,attrs);
    }

}
