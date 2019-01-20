package com.example.handlerthreadbasics;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

//http://developer.android.com/reference/android/os/HandlerThread.html
public class MyHandlerThread extends HandlerThread implements Handler.Callback {

    protected Handler theHandlerUI;
    protected Handler theHandlerOnThread;

    public MyHandlerThread(Handler aHandler) {
        super("MyHandlerThread");
        theHandlerUI = aHandler;
    }

    /*
     http://developer.android.com/reference/android/os/HandlerThread.html#onLooperPrepared()
	 Call back method that can be explicitly overridden if needed to execute some setup before Looper loops.
	 */
    @Override
    protected void onLooperPrepared() {
        //Handler(Looper looper, Handler.Callback callback)
        theHandlerOnThread = new Handler(getLooper(), this);
    }

    public static final int MSG_EMPTY_ID = 100;

    public void calculate() {
        theHandlerOnThread.sendEmptyMessage(MSG_EMPTY_ID);
    }

    public static final int MSG_RESPONSE_ID = 101;

    /*
    http://developer.android.com/reference/android/os/Handler.Callback.html
    https://developer.android.com/reference/android/os/Message.html
     */
    @Override
    public boolean handleMessage(Message aMessage) {
        String astrResponse;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (aMessage.what) {
            case MSG_EMPTY_ID:
                astrResponse="handleMessage:MSG_EMPTY_ID";
                break;
            default:
                return false;
        }
        if(null!=theHandlerUI){
            //obtainMessage(int what, Object obj)
            Message aMessageResponse=theHandlerUI.obtainMessage(MSG_RESPONSE_ID,astrResponse);
            theHandlerUI.sendMessage(aMessageResponse);
        }
        return true;   //return true if the message was handled
    }
}
