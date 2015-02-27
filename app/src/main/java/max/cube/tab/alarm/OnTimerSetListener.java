package max.cube.tab.alarm;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import max.cube.publisher.AlarmPublisher;
import max.cube.R;
import max.cube.dao.Alarm;


public class OnTimerSetListener implements TimePickerDialog.OnTimeSetListener {

    private View dialogView;

    private final AlarmPublisher alarms;

    private boolean fired = false;

    public OnTimerSetListener(AlarmPublisher alarms) {
        this.alarms = alarms;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (fired) {
            return;
        }

        fired = true;

        Alarm alarm = new Alarm();
        EditText nameView = (EditText) dialogView.findViewById(R.id.alarm_name);
        String name = nameView.getText().toString();

        alarm.setName(name);

        alarm.setWake(minute * 60 + hourOfDay * 60 * 60);
        alarm.setEnabled(true);

        alarms.push(alarm);
        alarms.populateView();
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }
}
