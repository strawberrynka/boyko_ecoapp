package com.example.ecoapp.presentation.adapters;

import android.content.Context;


import com.example.ecoapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CalendarDecorator implements DayViewDecorator {
    //    private final Drawable progressBarDrawable;
    private final CalendarDay targetDate;
    private Context ctx;
    private String type;

    public CalendarDecorator(Context context, CalendarDay targetDate, String type) {
        ctx = context;
        this.targetDate = targetDate;
        this.type = type;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(targetDate);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (type.equals("full")) view.setBackgroundDrawable(ctx.getDrawable(R.drawable.habit_progress_green));
        else if (type.equals("normal")) view.setBackgroundDrawable(ctx.getDrawable(R.drawable.habit_progress_gray));
        else if (type.equals("lose")) view.setBackgroundDrawable(ctx.getDrawable(R.drawable.habit_progress_gray));
    }
}
