package hzyj.come.p2p.app.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import hzyj.come.p2p.app.MyApplication;


/**
 * Created by WangBin on 2017/3/14.
 */

public class ToastUtil {



    private static Handler handler = new Handler(Looper.getMainLooper());
        private static Toast toast = null;

        /**
         * Toast发送消息，默认Toast.LENGTH_SHORT
         * @param msg
         */
     

        public static void showMessage( final String msg) {

            showMessage(MyApplication.APP, msg, Toast.LENGTH_SHORT);
        }
        public static void showMessage(final Context act, final String msg) {
            showMessage(act, msg, Toast.LENGTH_SHORT);
        }

        public static void showMessageLong(final Context act, final String msg) {
            showMessage(act, msg, Toast.LENGTH_LONG);
        }

        /**
         * Toast发送消息，默认Toast.LENGTH_SHORT
         * @param msg
         */
        public static void showMessage(final Context act, final int msg) {
            showMessage(act, msg, Toast.LENGTH_SHORT);
        }

        /**
         * Toast发送消息，默认Toast.LENGTH_LONG

         */
        public static void showMessageLong(final Context act, final int msg) {
            showMessage(act, msg, Toast.LENGTH_LONG);
        }

    private static void showMessage(final Context act, final int msg, final int time) {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (toast != null) {
                        toast.setText(msg);
                        toast.setDuration(time);
                        toast.show();
                    } else {
                        toast = Toast.makeText(act, msg, time);
                        toast.show();
                    }

                }
            });
        }else{
            if (toast != null) {
                toast.setText(msg);
                toast.setDuration(time);
                toast.show();
            } else {
                toast = Toast.makeText(act, msg, time);
                toast.show();
            }
        }
    }
    private static void showMessage(final Context act, final String msg, final int time) {
        if(Looper.myLooper()!= Looper.getMainLooper()){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast != null)
                {
                    toast.setText(msg);
                    toast.setDuration(time);
                    toast.show();
                } else
                {
                    toast = Toast.makeText(act, msg, time);
                    toast.show();
                }

            }
        });
        }else {
            if (toast != null)
            {
                toast.setText(msg);
                toast.setDuration(time);
                toast.show();
            } else
            {
                toast = Toast.makeText(act, msg, time);
                toast.show();
            }
        }
    }

        /**
         * 关闭当前Toast
         */
        public static void cancelCurrentToast() {
            if (toast != null) {
                toast.cancel();
            }
        }

}
