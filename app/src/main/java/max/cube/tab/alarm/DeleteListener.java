package max.cube.tab.alarm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.cocosw.undobar.UndoBarController;

import java.util.LinkedList;

import max.cube.AlarmPopulator;
import max.cube.dao.Alarm;
import max.cube.dao.AlarmDao;

class DeleteListener implements AdapterView.OnItemLongClickListener {
    private final AlarmDao alarmDao;
    private final AlarmPopulator populator;
    private final Activity activity;
    private LinkedList<Alarm> deleted = new LinkedList<>();

    public DeleteListener(AlarmDao alarmDao, AlarmPopulator populator, Activity activity) {
        this.alarmDao = alarmDao;
        this.populator = populator;
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
                    alarmDao.delete(alarm);
                    return alarm;
                }

                @Override
                protected void onPostExecute(Alarm alarm) {
                    deleted.push(alarm);
                    populator.populateView();

                    UndoBarController.UndoBar undoBar = new UndoBarController.UndoBar(activity);

                    String name = alarm.getName();
                    undoBar.message((name == null || name.isEmpty() ? "Alarm" : name) + " deleted").listener(new UndoBarController.UndoListener() {
                        @Override
                        public void onUndo(@Nullable Parcelable parcelable) {
                            Alarm last = deleted.pollLast();

                            if (last == null) {
                                return;
                            }

                            populator.push(last);
                            populator.populateView();

                        }
                    });
                    undoBar.show();
                }
            };

            task.execute((Alarm) tag);

            return true;
        }

        return false;
    }
}
