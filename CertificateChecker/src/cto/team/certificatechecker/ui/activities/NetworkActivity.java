package cto.team.certificatechecker.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cto.team.certificatechecker.R;
import cto.team.certificatechecker.models.Weather;
import cto.team.certificatechecker.networking.response.ModelResponseListener;
import cto.team.certificatechecker.networking.utils.ServerAPI;

public class NetworkActivity extends ActionBarActivity {

	private Button button;
	private TextView textView;
	private ModelResponseListener<Weather> responseListener;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        responseListener = new ModelResponseListener<Weather>(this, Weather.class) {
			@Override
			public void onComplete(Weather weather) {
				String text = "";
				text += "id: " + weather.id+ "\n";
				text += "base: " + weather.base + "\n";
				text += "name: " + weather.name + "\n";
				text += "cod: " + weather.cod + "\n";
				text += "dt: " + weather.dt + "\n";
				
				textView.setText(text);
			}
		};
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ServerAPI.getWehather(responseListener);
			}
		});
    }

}