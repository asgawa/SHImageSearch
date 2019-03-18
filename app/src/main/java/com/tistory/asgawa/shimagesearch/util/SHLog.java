package com.tistory.asgawa.shimagesearch.util;

import android.util.Log;

/**
 name: Debug
 type: class
 function: offer improved logcat debugging system
 */

public class SHLog {
    private String tag = "SHLog";

    @SuppressWarnings("UnusedDeclaration")
    private SHLog() { } //disable

    public SHLog(String tag) {
        if ((tag != null) && (!tag.isEmpty())) {
            this.tag = tag;
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void in() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack != null && stack.length > 1) {
            d("[%s(%s:%d)] IN", stack[1].getClassName(), stack[1].getMethodName(), stack[1].getLineNumber());
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void out() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack != null && stack.length > 1) {
            d("[%s(%s:%d)] OUT", stack[1].getClassName(), stack[1].getMethodName(), stack[1].getLineNumber());
        }
    }

    public void v(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = String.format(msg, args);
            Log.v(tag, str);
        }
    }

    public void d(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = String.format(msg, args);
            Log.d(tag, str);
        }
    }

    public void i(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = String.format(msg, args);
            Log.i(tag, str);
        }
    }

    public void w(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = String.format(msg, args);
            Log.w(tag, str);
        }
    }

    public void e(String msg, Object ... args) {
        if (msg != null && msg.length() > 0) {
            String str = String.format(msg, args);
            Log.e(tag, str);
        }
    }
}