package max.cube.publisher;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import max.cube.Alarm;
import max.cube.Matrix;
import max.cube.R;
import max.cube.alarm.AlarmListAdapter;
import max.cube.alarm.AlarmsFragment;

abstract class MatrixTask<T> extends AsyncTask<Void, Void, Void> {

    private final TextView start_message;
    private final ProgressBar progress;
    private final View add_alarm;
    private final String host;
    private final int port;
    private final AlarmListAdapter alarmListAdapter;

    private T result = null;

    MatrixTask(String host, int port, AlarmsFragment fragment, AlarmListAdapter alarmListAdapter) {
        this.host = host;
        this.port = port;
        this.alarmListAdapter = alarmListAdapter;
        this.progress = (ProgressBar) fragment.getRoot().findViewById(R.id.progress);
        this.start_message = (TextView) fragment.getRoot().findViewById(R.id.start_message);
        this.add_alarm = fragment.getRoot().findViewById(R.id.add_alarm);
    }

    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
        start_message.setVisibility(View.GONE);
        add_alarm.setVisibility(View.GONE);
    }

    protected abstract T operate(List<Alarm> alarms, Matrix matrix) throws IOException;

    protected abstract void post(T result);


    @Override
    protected Void doInBackground(Void... params) {
        ArrayList<Alarm> alarms = new ArrayList<>();
        for (int i = 0; i < alarmListAdapter.getCount(); i++) {
            alarms.add(alarmListAdapter.getItem(i));
        }

        try {
            Matrix matrix = new Matrix(host, port);
            result = operate(Collections.unmodifiableList(alarms), matrix);
        } catch (IOException e) {
            Log.e("CUBE", "Failed to synchronize!", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void parm) {
        post(result);

        start_message.setVisibility(!alarmListAdapter.isEmpty() ? View.GONE : View.VISIBLE);

        progress.setVisibility(View.GONE);
        add_alarm.setVisibility(View.VISIBLE);
    }
}
