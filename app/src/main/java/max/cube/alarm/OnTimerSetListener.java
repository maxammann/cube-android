package max.cube.alarm;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import max.cube.publisher.AlarmPublisher;
import max.cube.R;
import max.cube.Alarm;

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

        EditText nameView = (EditText) dialogView.findViewById(R.id.alarm_name);
        String name = nameView.getText().toString();

        alarms.push(new Alarm(name,minute * 60 + hourOfDay * 60 * 60, true, new boolean[7]));
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }
}
