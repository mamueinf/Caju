package com.mm.caju;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mm.caju.caju_seqMdl.TimeSlot;

/**
 * This
 *
 */
public class CajuMovIconView extends ImageView {

    private TimeSlot timeslot;

    public CajuMovIconView(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public CajuMovIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CajuMovIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

}