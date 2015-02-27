package max.cube.publisher;

import android.os.AsyncTask;

import max.cube.MainActivity;
import max.cube.dao.Alarm;
import max.cube.tab.alarm.AlarmsFragment;


public class DatabaseAlarmPublisher implements AlarmPublisher {

    private final MainActivity activity;
    private final AlarmsFragment fragment;

    public DatabaseAlarmPublisher(MainActivity activity, AlarmsFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public void populateView() {
        new PopulateTask(activity, fragment).execute();
    }

    @Override
    public void push(final Alarm alarm) {

        new AsyncTask<Alarm, Void, Void>() {

            @Override
            protected Void doInBackground(Alarm... params) {
                activity.getAlarmDao().insert(params[0]);
                return null;
            }
        }.execute(alarm);
    }

}

