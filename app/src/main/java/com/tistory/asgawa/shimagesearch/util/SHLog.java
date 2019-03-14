package com.tistory.asgawa.shimagesearch.util;

import android.util.Log;

/**
 name: Debug
 type: class
 function: offer improved logcat debugging system
 */

public class SHLog {
    private String mTag = "SHLog";

    private SHLog() {
    }

    public SHLog(String tag) {
        if ((tag != null) && (!tag.isEmpty())) {
            mTag = tag;
        }
    }

    public void in() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack != null && stack.length > 1) {
            d("[%s(%s:%d)] IN", stack[1].getClassName(), stack[1].getMethodName(), stack[1].getLineNumber());
        }
    }

    public void out() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack != null && stack.length > 1) {
            d("[%s(%s:%d)] OUT", stack[1].getClassName(), stack[1].getMethodName(), stack[1].getLineNumber());
        }
    }

    public void v(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.v(mTag, str);
        }
    }

    public void d(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.d(mTag, str);
        }
    }

    public void i(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.i(mTag, str);
        }
    }

    public void w(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.w(mTag, str);
        }
    }

    public void e(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.e(mTag, str);
        }
    }

    public void a(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = null;
            str = String.format(msg, args);
            Log.wtf(mTag, str);
        }
    }
}