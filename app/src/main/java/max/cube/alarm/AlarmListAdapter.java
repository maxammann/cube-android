package max.cube.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import max.cube.Alarm;
import max.cube.R;
import max.cube.Weekday;
import max.cube.publisher.AlarmPublisher;


public class AlarmListAdapter extends ArrayAdapter<Alarm> {

    private final AlarmPublisher alarmPublisher;

    public AlarmListAdapter(Context context, AlarmPublisher alarmPublisher) {
        super(context, R.layout.alarm_item);
        this.alarmPublisher = alarmPublisher;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newView(getItem(position), parent);
        } else {
            v = convertView;
        }
        bindView(v, getItem(position));
        return v;
    }


    public View newView(Alarm item, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        view.setTag(item);
        return view;
    }

    public void bindView(View view, final Alarm item) {
        view.setTag(item);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        String name = item.getName();
        nameView.setText(name == null || name.isEmpty() ? "Alarm" : name);

        final TextView alarm = (TextView) view.findViewById(R.id.time);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        alarm.setText(format.format(item.getWake() * 1000));


        Switch switchView = (Switch) view.findViewById(R.id.switch_alarm);

        switchView.setChecked(item.isEnabled());

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Switch) v).isChecked()) {
                    item.setEnabled(true);
                } else {
                    item.setEnabled(false);
                }

                alarmPublisher.update(item);
            }
        });



        setupWeekdayButton(view, R.id.monday, Weekday.MONDAY, item);
        setupWeekdayButton(view, R.id.tuesday, Weekday.TUESDAY, item);
        setupWeekdayButton(view, R.id.wednesday, Weekday.WEDNESDAY, item);
        setupWeekdayButton(view, R.id.thursday, Weekday.THURSDAY, item);
        setupWeekdayButton(view, R.id.friday, Weekday.FRIDAY, item);
        setupWeekdayButton(view, R.id.saturday, Weekday.SATURDAY, item);
        setupWeekdayButton(view, R.id.sunday, Weekday.SUNDAY, item);
    }

    private void setupWeekdayButton(View view, int id, final Weekday weekday, final Alarm alarm) {
        ToggleButton button = ((ToggleButton) view.findViewById(id));
        button.setChecked(alarm.isWeekday(weekday.getOrdinal()));

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarm.setWeekday(weekday.getOrdinal(), true);
                } else {
                    alarm.setWeekday(weekday.getOrdinal(), false);
                }

                alarmPublisher.update(alarm);
            }
        });
    }
}
