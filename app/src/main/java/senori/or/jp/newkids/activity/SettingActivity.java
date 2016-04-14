package senori.or.jp.newkids.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import senori.or.jp.newkids.R;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.tools);

    }

}
