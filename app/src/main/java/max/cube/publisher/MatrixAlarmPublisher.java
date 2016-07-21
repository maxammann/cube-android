package max.cube.publisher;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import max.cube.Alarm;
import max.cube.Matrix;
import max.cube.R;
import max.cube.alarm.AlarmListAdapter;
import max.cube.alarm.AlarmsFragment;


public class MatrixAlarmPublisher implements AlarmPublisher {

    private final Context context;
    private final AlarmsFragment fragment;
    private final AlarmListAdapter alarmListAdapter;

    public MatrixAlarmPublisher(Context context, AlarmsFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.alarmListAdapter = new AlarmListAdapter(context, this);
    }

    @Override
    public void populateView() {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);
        new MatrixTask(host, port, fragment, alarmListAdapter) {
            @Override
            protected void operate(List<Alarm> alarms, Matrix matrix) throws IOException {
                List<Alarm> alarms1 = matrix.receiveAlarms();

                System.out.println(alarms);
            }

            @Override
            protected void post() {

            }
        }.execute();
    }

    @Override
    public void push(final Alarm alarm) {
        alarmListAdapter.add(alarm);
        update(alarm);
    }

    @Override
    public void delete(Alarm alarm) {
        alarmListAdapter.remove(alarm);
        update(alarm);
    }

    @Override
    public void update(Alarm alarm) {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);

        new MatrixTask(host, port, fragment, alarmListAdapter) {
            @Override
            protected void operate(List<Alarm> alarms, Matrix matrix) throws IOException {
                matrix.sendAlarms(alarms);
            }

            @Override
            protected void post() {
                alarmListAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public AlarmListAdapter getAlarmListAdapter() {
        return alarmListAdapter;
    }
}

