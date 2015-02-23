package max.cube.tab.alarm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import max.cube.AlarmPopulator;
import max.cube.R;
import max.cube.dao.Alarm;


public class AddAlarmFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private View dialogView;

    ViewGroup root;
    AlarmPopulator alarms;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int hour, minute;

        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(
                getActivity(),
                R.style.TimePickerTheme,
                this,
                hour, minute,
                DateFormat.is24HourFormat(getActivity())
        );

        Context themeContext = timePicker.getContext();
        LayoutInflater inflater = LayoutInflater.from(themeContext);
        dialogView = inflater.inflate(R.layout.custom_picker_dialog, null);
        timePicker.setView(dialogView);

        return timePicker;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Alarm alarm = new Alarm();
        EditText nameView = (EditText) this.dialogView.findViewById(R.id.alarm_name);
        String name = nameView.getText().toString();

        alarm.setName(name);

        alarm.setWake(minute * 60 + hourOfDay * 60 * 60);

        alarms.push(alarm);
        alarms.populateView();
    }
}