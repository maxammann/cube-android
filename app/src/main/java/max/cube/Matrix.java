package max.cube;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Represents a Matrix
 */
public class Matrix {

    private String host;
    private int port;
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public Matrix(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void sendAlarms(List<Alarm> alarms) throws IOException {
        JSONArray json = new JSONArray();

        for (Alarm alarm : alarms) {
            JSONObject alarmObject = new JSONObject();
            try {
                alarmObject.put("name", alarm.getName());
                alarmObject.put("wake_time", alarm.getWake());
                alarmObject.put("enabled", alarm.isEnabled());

                JSONArray weekdays = new JSONArray();

                for (int i = 0; i < 7; i++) {
                    weekdays.put(alarm.isWeekday(i));
                }

                alarmObject.put("weekdays", weekdays);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json.put(alarmObject);
        }

        Request request = new Request.Builder()
                .url(new URL("http", host, port, "alarm/set"))
                .post(RequestBody.create(JSON, json.toString()))
                .build();


        CLIENT.newCall(request).execute();
    }

    public List<Alarm> receiveAlarms() throws IOException {
        Request request = new Request.Builder()
                .url(new URL("http", host, port, "alarm/get"))
                .get()
                .build();


        Response response = CLIENT.newCall(request).execute();

        ArrayList<Alarm> alarms = new ArrayList<>();

        JsonReader jsonReader = new JsonReader(response.body().charStream());

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            String name = "None";
            long wakeTime = -1;
            boolean enabled = false;
            boolean[] weekdays = new boolean[7];

            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();

                switch (key) {
                    case "name":
                        name = jsonReader.nextString();
                        break;
                    case "wake_time":
                        wakeTime = jsonReader.nextLong();
                        break;
                    case "enabled":
                        enabled = jsonReader.nextBoolean();
                        break;
                    case "weekdays":
                        int i = 0;
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            weekdays[i] = jsonReader.nextBoolean();
                            i++;
                        }
                        jsonReader.endArray();
                        break;
                }
            }

            alarms.add(new Alarm(name, wakeTime, enabled, weekdays));
            jsonReader.endObject();
        }
        jsonReader.endArray();

        jsonReader.close();
        return alarms;
    }
}
