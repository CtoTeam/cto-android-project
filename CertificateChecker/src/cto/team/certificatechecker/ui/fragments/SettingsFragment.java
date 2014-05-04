package cto.team.certificatechecker.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import cto.team.certificatechecker.R;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.fragment_settings);
	}
}
