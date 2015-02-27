package max.cube.publisher;

import max.cube.dao.Alarm;

public interface AlarmPublisher {

    void populateView();

    void push(Alarm alarm);
}
