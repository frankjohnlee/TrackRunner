package com.wordpress.frankhaolunliblog.laprunner;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by Frank2 on 7/8/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);
    }

}
