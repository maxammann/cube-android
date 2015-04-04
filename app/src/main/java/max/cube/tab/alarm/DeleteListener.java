package max.cube.tab.alarm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.cocosw.undobar.UndoBarController;

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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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

                    new UndoBarController.UndoBar(activity).token(new AlarmParcelable(alarm))
                            .message((name == null || name.isEmpty() ? "Alarm" : name) + " deleted").listener(new UndoListener())
                            .show();
                }
            };

            task.execute((Alarm) tag);

            return true;
        }

        return false;
    }

    private class UndoListener implements UndoBarController.UndoListener {
        @Override
        public void onUndo(@Nullable Parcelable parcelable) {

            if (parcelable instanceof AlarmParcelable) {
                alarms.push(((AlarmParcelable) parcelable).getAlarm());
                alarms.populateView();
            }
        }
    }
}
