package max.cube.discovery;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;

import lm.discovery.Discovery;
import max.cube.Cube;

public class DiscoveryTask extends AsyncTask<Void, Void, InetAddress> {

    private Cube cube;

    public DiscoveryTask(Cube cube) {
        this.cube = cube;
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
                Log.i("DISCOVERY", "Failed to discover server!");
            }
        }
    }

    @Override
    protected void onPostExecute(InetAddress inetAddress) {
        cube.setRemoteAddress(inetAddress);
        Toast.makeText(cube, inetAddress.getHostAddress(), Toast.LENGTH_LONG).show();
    }
}
