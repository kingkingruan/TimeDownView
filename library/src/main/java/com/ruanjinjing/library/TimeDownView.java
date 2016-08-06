package com.ruanjinjing.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ruanjinjing on 15/12/3.
 */
public class TimeDownView extends TextView {

    private long timeLeft;
    private String preText, splitTag;
    private EndCallback endCallback;
    private MyHandler myHandler;

    public interface EndCallback {
        void end(TimeDownView txtView);
    }

    public TimeDownView(Context context) {
        super(context);
    }

    public TimeDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        myHandler = new MyHandler();
    }

    public void setEndCallback(EndCallback endCallback) {
        this.endCallback = endCallback;
    }

    public void startTimeDown(long timeLeft, String preText) {
        this.timeLeft = timeLeft;
        this.preText = preText;
        refresh();
        if (timeLeft > 0) {
            myHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    public void startTimeDown(long timeLeft, String preText, String splitTag) {
        this.timeLeft = timeLeft;
        this.preText = preText;
        this.splitTag = splitTag;
        refresh();
        if (timeLeft > 0) {
            myHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timeLeft -= 1000;
            refresh();
            if (timeLeft > 0) {
                myHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    }

    private void refresh() {
        if (timeLeft <= 0) {
            setText("");
            if (endCallback != null) {
                endCallback.end(this);
            }
            return;
        }
        long day = timeLeft / (24 * 60 * 60 * 1000);
        long hour = (timeLeft / (60 * 60 * 1000) - day * 24);
        long min = ((timeLeft / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeLeft / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String minStr = String.valueOf(min);
        if (min < 10) {
            minStr = "0" + min;
        }
        String sStr = String.valueOf(s);
        if (s < 10) {
            sStr = "0" + s;
        }
        String result;
        if (!TextUtils.isEmpty(splitTag)) {
            result = preText + (day * 24 + hour) + splitTag + minStr + splitTag + sStr;
        } else {
            if (day > 0)
                result = preText + day + "天" + hour + "小时" + minStr + "分" + sStr + "秒";
            else {
                result = preText + hour + "小时" + minStr + "分" + sStr + "秒";
            }
        }
        setText(result);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        myHandler.removeMessages(1);
    }
}

