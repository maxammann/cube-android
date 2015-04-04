package max.cube.publisher;

import android.os.AsyncTask;

import max.cube.Cube;
import max.cube.dao.Alarm;
import max.cube.tab.alarm.AlarmsFragment;


public class DatabaseAlarmPublisher implements AlarmPublisher {

    private final Cube cube;
    private final AlarmsFragment fragment;

    public DatabaseAlarmPublisher(Cube cube, AlarmsFragment fragment) {
        this.cube = cube;
        this.fragment = fragment;
    }

    @Override
    public void populateView() {
        new PopulateTask(cube, fragment).execute();
    }

    @Override
    public void push(final Alarm alarm) {

        new AsyncTask<Alarm, Void, Void>() {

            @Override
            protected Void doInBackground(Alarm... params) {
                cube.getAlarmDao().insert(params[0]);
                return null;
            }
        }.execute(alarm);
    }

}

