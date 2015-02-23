package max.cube;

import max.cube.dao.Alarm;

public interface AlarmPopulator {

    void populateView();

    void push(Alarm alarm);
}
