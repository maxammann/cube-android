package max.cube.alarm;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import max.cube.Alarm;
import max.cube.R;
import max.cube.publisher.AlarmPublisher;

class DeleteListener implements AdapterView.OnItemLongClickListener {
    private final AlarmPublisher alarms;

    public DeleteListener(AlarmPublisher alarms) {
        this.alarms = alarms;
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
        Object tag = parent.getChildAt(position).getTag();

        if (tag instanceof Alarm) {
            Alarm alarm = ((Alarm) tag);

            alarms.delete(alarm);

            String name = alarm.getName();

            String message = (name == null || name.isEmpty() ? "Alarm" : name) + " deleted";
            Snackbar snackbar = Snackbar
                    .make(((FrameLayout) parent.getParent()).findViewById(R.id.add_alarm), message, Snackbar.LENGTH_LONG)
                    .setAction("Undo", new UndoListener(alarm));
            snackbar.show();

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
