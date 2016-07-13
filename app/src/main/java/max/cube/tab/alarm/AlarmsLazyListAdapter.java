package max.cube.tab.alarm;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import de.greenrobot.dao.GreenDaoListAdapter;
import de.greenrobot.dao.query.LazyList;
import max.cube.Cube;
import max.cube.R;
import max.cube.dao.Alarm;
import max.cube.dao.AlarmDao;
import max.cube.publisher.AlarmPublisher;
import max.cube.publisher.DatabaseAlarmPublisher;
import max.cube.synchronize.SynchronizeRunnable;


public class AlarmsLazyListAdapter extends GreenDaoListAdapter<Alarm> {

    private final Cube cube;
    private final AlarmDao alarmDao;

    public AlarmsLazyListAdapter(Context context, LazyList<Alarm> lazyList, Cube cube, AlarmDao alarmDao) {
        super(context, lazyList);
        this.cube = cube;
        this.alarmDao = alarmDao;
    }

    @Override
    public View newView(Context context, Alarm item, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Alarm item) {
        view.setTag(item);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        String name = item.getName();
        nameView.setText(name == null || name.isEmpty() ? "Alarm" : name);

        final TextView alarm = (TextView) view.findViewById(R.id.time);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        alarm.setText(format.format(item.getWake().longValue() * 1000));


        Switch switchView = (Switch) view.findViewById(R.id.switch_alarm);

        switchView.setChecked(item.getEnabled() == null ? false : item.getEnabled());

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Switch) v).isChecked()) {
                    item.setEnabled(true);
                } else {
                    item.setEnabled(false);
                }

                new UpdateTask().execute(item);

            }
        });
    }

    private class UpdateTask extends AsyncTask<Alarm, Void, Void> {
        @Override
        protected Void doInBackground(Alarm... params) {
            alarmDao.update(params[0]);

            new SynchronizeRunnable(alarmDao.queryBuilder().listLazy(), cube.getRemoteHost(), cube.getRemotePort()).call();
            return null;
        }
    }
}
