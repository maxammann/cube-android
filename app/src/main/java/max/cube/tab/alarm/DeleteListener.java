package max.cube.tab.alarm;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;

import max.cube.Cube;
import max.cube.publisher.AlarmPublisher;
import max.cube.dao.Alarm;

class DeleteListener implements AdapterView.OnItemLongClickListener {
    private final AlarmPublisher alarms;
    private final Cube cube;

    private final Activity activity;


    public DeleteListener(AlarmPublisher alarms, Cube cube, Activity activity) {
        this.alarms = alarms;
        this.cube = cube;
        this.activity = activity;
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
        Object tag = view.getTag();

        if (tag instanceof Alarm) {

            AsyncTask<Alarm, Void, Alarm> task = new AsyncTask<Alarm, Void, Alarm>() {
                @Override
                protected Alarm doInBackground(Alarm... params) {
                    Alarm alarm = params[0];
                    cube.getAlarmDao().delete(alarm);
                    return alarm;
                }

                @Override
                protected void onPostExecute(final Alarm alarm) {
                    alarms.populateView();

                    String name = alarm.getName();

                    String message = (name == null || name.isEmpty() ? "Alarm" : name) + " deleted";
                    Snackbar snackbar = Snackbar
                            .make(parent, message, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new UndoListener(alarm));
                    snackbar.show();
                }
            };

            task.execute((Alarm) tag);

            return true;
        }

        return false;
    }

    private class UndoListener implements View.OnClickListener {
        private final Alarm alarm;

        private UndoListener(Alarm alarm) {
            this.alarm = alarm;
        }

        @Override
        public void onClick(View v) {
            alarms.push(alarm);
            alarms.populateView();
        }
    }
}
