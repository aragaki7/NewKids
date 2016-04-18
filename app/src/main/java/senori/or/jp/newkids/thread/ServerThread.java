package senori.or.jp.newkids.thread;

import android.os.AsyncTask;

/**
 * Created by JupiteR on 2016-04-02.
 */
public class ServerThread extends AsyncTask<Object, Integer, Object> {
    private OnConnect onConnect;

    public ServerThread(OnConnect onConnect) {
        this.onConnect = onConnect;
    }

    @Override
    protected Object doInBackground(Object... params) {
        onConnect.doInBackground();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onConnect.onPostExecute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onConnect.onPreExecute();
    }

    public interface OnConnect {
        void onPreExecute();

        void doInBackground();

        void onPostExecute();
    }
}
