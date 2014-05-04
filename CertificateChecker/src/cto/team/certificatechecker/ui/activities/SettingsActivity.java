package cto.team.certificatechecker.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import cto.team.certificatechecker.ui.fragments.SettingsFragment;

public class SettingsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}
}
