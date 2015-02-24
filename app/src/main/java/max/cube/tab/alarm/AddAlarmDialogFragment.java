package max.cube.tab.alarm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

import max.cube.AlarmPopulator;
import max.cube.R;


public class AddAlarmDialogFragment extends DialogFragment {

    AlarmPopulator alarms;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int hour, minute;

        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        OnTimerSetListener callBack = new OnTimerSetListener(alarms);

        boolean h24 = DateFormat.is24HourFormat(getActivity());



        CustomTimePickerDialog timePicker = new CustomTimePickerDialog(
                getActivity(),
                R.style.TimePickerTheme,
                callBack,
                R.layout.custom_picker_dialog,
                R.id.timePicker,
                hour,
                minute,
              h24

        );

        callBack.setDialogView(timePicker.getView());

        return timePicker;
    }


}