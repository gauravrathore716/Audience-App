package com.b2c.audience.DatePickerCustom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.b2c.audience.R;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, OnDateChangedListener {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TITLE_SHOWN = "title_enabled";

    private final DatePicker mDatePicker;
    private final OnDateSetListener mCallBack;
    private final DateFormat mTitleDateFormat;

    private boolean mIsDayShown = true;
    private boolean mIsTitleShown = true;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {
        /**
         * @param view        The view associated with this listener.
         * @param year        The year that was set
         * @param monthOfYear The month that was set (0-11) for compatibility
         *                    with {@link Calendar}.
         * @param dayOfMonth  The day of the month that was set.
         */
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }

    DatePickerDialog(Context context,
                     int theme,
                     int spinnerTheme,
                     OnDateSetListener callBack,
                     Calendar defaultDate,
                     Calendar minDate,
                     Calendar maxDate,
                     boolean isDayShown,
                     boolean isTitleShown) {
        super(context, theme);

        mCallBack = callBack;
        mTitleDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        mIsDayShown = isDayShown;
        mIsTitleShown = isTitleShown;

        updateTitle(defaultDate);

        setButton(BUTTON_POSITIVE, context.getText(android.R.string.ok),
                this);
        setButton(BUTTON_NEGATIVE, context.getText(R.string.annuler),
                (OnClickListener) null);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_dialog_container, null);
        setView(view);
        mDatePicker = new DatePicker((ViewGroup) view, spinnerTheme);
        mDatePicker.setMinDate(minDate.getTimeInMillis());

                Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.YEAR, -12);
        mDatePicker.setMaxDate(c2.getTimeInMillis());
//        mDatePicker.setMaxDate(System.currentTimeMillis());
        mDatePicker.init(defaultDate.get(Calendar.YEAR), defaultDate.get(Calendar.MONTH), defaultDate.get(Calendar.DAY_OF_MONTH), isDayShown, this);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, mDatePicker.getYear(),
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar updatedDate = Calendar.getInstance();
        updatedDate.set(Calendar.YEAR, year);
        updatedDate.set(Calendar.MONTH, monthOfYear);
        updatedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateTitle(updatedDate);
    }

    private void updateTitle(Calendar updatedDate) {
        if(mIsTitleShown) {
            final DateFormat dateFormat = mTitleDateFormat;
            setTitle(dateFormat.format(updatedDate.getTime()));
        } else {
            setTitle(" ");
        }
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        state.putBoolean(TITLE_SHOWN, mIsTitleShown);
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int year = savedInstanceState.getInt(YEAR);
        int month = savedInstanceState.getInt(MONTH);
        int day = savedInstanceState.getInt(DAY);
        mIsTitleShown = savedInstanceState.getBoolean(TITLE_SHOWN);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        updateTitle(c);
        mDatePicker.init(year, month, day, mIsDayShown, this);
    }
}
