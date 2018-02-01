package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/16 0016.
 */

public class CalendarView extends Dialog implements OnDateSelectedListener {

    private Context mContext;
    private View mView;
    private MaterialCalendarView calendarView;
    private SimpleDateFormat dateFormat;

    public CalendarView(@NonNull Context mContext, View mView) {
        super(mContext,R.style.DialogTheme);
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowAnimation();
        initViews();
        initData();
        initListeners();

    }

    private void setWindowAnimation() {
        getWindow().setWindowAnimations(R.style.Dialog_Bottom);
    }

    private void initViews() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_calendar_view,null);
        setContentView(view);

        calendarView = findViewById(R.id.calendarView);
    }

    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            String dateStr = getText();
            if (!TextUtils.isEmpty(dateStr)) {
                Date date = dateFormat.parse(dateStr);
                calendar.setTime(date);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!CalendarDay.today().equals(CalendarDay.from(calendar))){
                calendarView.addDecorator(new MyDecorator(mContext));
            }
            calendarView.setSelectedDate(calendar);
            calendarView.setCurrentDate(calendar);
        }
    }

    private void initListeners() {
        calendarView.setOnDateChangedListener(this);
    }

    public void show() {
        super.show();
        WindowManager.LayoutParams param = getWindow().getAttributes();
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.gravity = Gravity.BOTTOM;
        param.y = 0;
        getWindow().setAttributes(param);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        setText(dateFormat.format(date.getDate()));
        this.dismiss();
    }

    private void setText(String dateStr) {
        if(mView != null){
            if(mView instanceof TextView){
                ((TextView) mView).setText(dateStr);
            }else if(mView instanceof EditText){
                ((EditText) mView).setText(dateStr);
            }else if(mView instanceof Button){
                ((Button) mView).setText(dateStr);
            }
        }
    }

    private String getText() {
        String dateStr = "";
        if(mView != null){
            if(mView instanceof TextView){
                dateStr = ((TextView) mView).getText().toString();
            }else if(mView instanceof EditText){
                dateStr = ((EditText) mView).getText().toString();
            }else if(mView instanceof Button){
                dateStr = ((Button) mView).getText().toString();
            }
        }
        return dateStr;
    }

    class MyDecorator implements DayViewDecorator{

        private final Drawable drawable;
        public MyDecorator(Context context) {
            drawable = context.getResources().getDrawable(R.drawable.shape_calendar_view_decorator);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(CalendarDay.from(Calendar.getInstance()));
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(drawable);
        }
    }
}
