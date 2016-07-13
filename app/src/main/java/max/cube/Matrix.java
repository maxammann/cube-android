package max.cube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import max.cube.dao.Alarm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Represents a Matrix
 */
public class Matrix {

    private String host;
    private int port;

    public Matrix(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void setAlarms(Iterable<Alarm> alarms) throws IOException {
        JSONArray json = new JSONArray();

        for (Alarm alarm : alarms) {
            JSONObject alarmObject = new JSONObject();
            try {
                alarmObject.put("name", alarm.getName());
                alarmObject.put("wake_time", alarm.getWake());
                alarmObject.put("enabled", alarm.getEnabled());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json.put(alarmObject);
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(new URL("http", host, port, "alarm/set"))
                .post(RequestBody.create(JSON, json.toString()))
                .build();


        client.newCall(request).execute();
    }
}
