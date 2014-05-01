package cto.team.certificatechecker.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cto.team.certificatechecker.R;
import cto.team.certificatechecker.networking.request.AsyncTaskFtpRequest;

public class NetworkActivity extends ActionBarActivity {

	private Button button;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_main);

		button = (Button) findViewById(R.id.button);
		imageView = (ImageView) findViewById(R.id.imageView);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTaskFtpRequest(imageView).execute("");
			}
		});
	}

}