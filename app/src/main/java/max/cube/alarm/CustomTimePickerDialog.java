package max.cube.alarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

public class CustomTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener {
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String IS_24_HOUR = "is24hour";

    public final TimePicker mTimePicker;
    private final View view;
    private final TimePickerDialog.OnTimeSetListener mTimeSetCallback;


    static int resolveDialogTheme(Context context, int resid) {
        if (resid == 0) {
            final TypedValue outValue = new TypedValue();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.getTheme().resolveAttribute(android.R.attr.timePickerDialogTheme, outValue, true);
            }
            return outValue.resourceId;
        } else {
            return resid;
        }
    }

    public CustomTimePickerDialog(Context context, int theme, TimePickerDialog.OnTimeSetListener callBack, int dialogView, int timePicker,
                                  int hour, int minute, boolean h24) {
        super(context, resolveDialogTheme(context, theme));

        mTimeSetCallback = callBack;

        Context themeContext = getContext();
        LayoutInflater inflater = LayoutInflater.from(themeContext);

        this.view = inflater.inflate(dialogView, null);
        setView(view);

        setButton(DialogInterface.BUTTON_POSITIVE, themeContext.getString(android.R.string.ok), this);
        setButton(DialogInterface.BUTTON_NEGATIVE, themeContext.getString(android.R.string.cancel), this);

        // because we're overriding the view
        TimePicker mTimePicker = (TimePicker) view.findViewById(timePicker);
        mTimePicker.setIs24HourView(h24);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setOnTimeChangedListener(this);
        this.mTimePicker = mTimePicker;
    }


    public View getView() {
        return view;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        /* do nothing */
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mTimeSetCallback != null) {
                    mTimeSetCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute());
                }
                break;
        }
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(HOUR, mTimePicker.getCurrentHour());
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        return state;
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int hour = savedInstanceState.getInt(HOUR);
        final int minute = savedInstanceState.getInt(MINUTE);
        mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
    }
}
