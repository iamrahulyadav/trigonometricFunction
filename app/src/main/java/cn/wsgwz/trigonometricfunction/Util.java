package cn.wsgwz.trigonometricfunction;

import android.content.Context;

/**
 * Created by admin on 2017/10/10 0010.
 */

public class Util {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static   double angleToRadian(int angle) throws Exception {
        if(!(0<=angle&&angle<=360)){
            throw new Exception("非法角度 "+angle);
        }
        return 2*Math.PI/360*angle;
    }
}
