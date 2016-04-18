package senori.or.jp.sharering.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JupiteR on 2016-04-05.
 */
public class Pre {
    private SharedPreferences sharedPreferences;

    public Pre(Context context) {

        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);

    }

    public void setUser(String key, String data) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(key, data);
        ed.commit();
    }

    public String getUser(String key) {
        return sharedPreferences.getString(key, null);
    }

}
