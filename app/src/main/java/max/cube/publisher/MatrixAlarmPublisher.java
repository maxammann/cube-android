package max.cube.publisher;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
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
//        this.alarmListAdapter.add(new Alarm("test",0,true, new boolean[7]));
    }

    @Override
    public void populateView() {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);
        new ReceiveMatrixTask(host, port).execute();
    }

    @Override
    public void push(final Alarm alarm) {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);
        new UpdateMatrixTask(host, port, alarm, UpdateAction.ADD).execute();
    }

    @Override
    public void delete(Alarm alarm) {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);
        new UpdateMatrixTask(host, port, alarm, UpdateAction.REMOVE).execute();
    }

    @Override
    public void update(Alarm alarm) {
        String host = context.getResources().getString(R.string.host);
        int port = context.getResources().getInteger(R.integer.port);
        new UpdateMatrixTask(host, port, alarm, UpdateAction.UPDATE).execute();
    }

    public AlarmListAdapter getAlarmListAdapter() {
        return alarmListAdapter;
    }

    private class ReceiveMatrixTask extends MatrixTask<List<Alarm>> {
        private ReceiveMatrixTask(String host, int port) {
            super(host, port, fragment, alarmListAdapter);
        }

        @Override
        protected List<Alarm> operate(List<Alarm> alarms, Matrix matrix) throws IOException {
            try {
                return matrix.receiveAlarms();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void post(List<Alarm> alarms) {
            if (alarms == null) {
                Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show();
                return;
            }
            alarmListAdapter.clear();
            alarmListAdapter.addAll(alarms);
            alarmListAdapter.notifyDataSetChanged();
        }
    }

    private enum UpdateAction {
        ADD,
        REMOVE,
        UPDATE
    }

    private class UpdateMatrixTask extends MatrixTask<Boolean> {
        private final Alarm alarm;
        private final UpdateAction action;

        public UpdateMatrixTask(String host, int port, Alarm alarm, UpdateAction action) {
            super(host, port, MatrixAlarmPublisher.this.fragment, MatrixAlarmPublisher.this.alarmListAdapter);
            this.alarm = alarm;
            this.action = action;
        }

        @Override
        protected Boolean operate(List<Alarm> alarms, Matrix matrix) throws IOException {
            try {
                alarms = new ArrayList<>(alarms);
                switch (action) {
                    case ADD:
                        alarms.add(alarm);
                        break;
                    case REMOVE:
                        alarms.remove(alarm);
                        break;
                    case UPDATE:
                        break;
                }
                matrix.sendAlarms(alarms);
            } catch (IOException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void post(Boolean success) {
            if (success) {
                switch (action) {
                    case ADD:
                        alarmListAdapter.add(alarm);
                        break;
                    case REMOVE:
                        alarmListAdapter.remove(alarm);
                        break;
                    case UPDATE:
                        break;
                }
                alarmListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show();
            }
        }
    }
}

