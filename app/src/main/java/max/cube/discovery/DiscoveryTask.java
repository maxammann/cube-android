package max.cube.discovery;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;

import lm.discovery.Discovery;
import max.cube.MainActivity;

public class DiscoveryTask extends AsyncTask<Void, Void, InetAddress> {

    private MainActivity mainActivity;

    public DiscoveryTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected InetAddress doInBackground(Void... params) {
        Discovery discovery = new Discovery(5000);

        while (true) {
            try {
                Log.i("DISCOVERY", "Trying to discover...");

                InetAddress address = discovery.discover();
                if (address != null) {
                    return address;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(InetAddress inetAddress) {
        mainActivity.setRemoteAddress(inetAddress);
        Toast.makeText(mainActivity, inetAddress.getHostAddress(), Toast.LENGTH_LONG).show();
    }
}
