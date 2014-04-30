package cto.team.certificatechecker.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

import cto.team.certificatechecker.R;
import cto.team.certificatechecker.networking.response.ProgressResponseListener;
import cto.team.certificatechecker.networking.utils.ServerUtils;

public class MainActivity extends BaseActivity {

	private Button button1;
	private TextView textView1;
	private ProgressResponseListener progressResponseListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		progressResponseListener = new ProgressResponseListener(this) {
			@Override
			public void onComplete(JsonObject result) {
				super.onComplete(result);
				textView1.setText(result.toString());
			}
		};
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressResponseListener.show();
				ServerUtils.runGetRequest("http://api.openweathermap.org/data/2.5/weather?q=London,uk", null, progressResponseListener);
			}
		});

	}
}
