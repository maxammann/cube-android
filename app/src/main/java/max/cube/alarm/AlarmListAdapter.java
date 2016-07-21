package max.cube.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import max.cube.Alarm;
import max.cube.R;
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
        return LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
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
    }
}
