package senori.or.jp.sharering.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import senori.or.jp.sharering.R;


public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.tools);

    }

}
