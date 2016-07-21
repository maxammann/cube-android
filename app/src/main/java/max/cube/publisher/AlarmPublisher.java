package max.cube.publisher;

import max.cube.Alarm;

public interface AlarmPublisher {

    void populateView();

    void push(Alarm alarm);

    void delete(Alarm alarm);

    void update(Alarm alarm);
}
