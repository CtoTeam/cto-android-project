package cto.team.certificatechecker.networking.utils;

import cto.team.certificatechecker.models.Weather;
import cto.team.certificatechecker.networking.response.ModelResponseListener;

public class ServerAPI {
	
	public static void getWehather(ModelResponseListener<Weather> responseListener) {
		responseListener.showProgressBar();
		ServerUtils.runGetRequest("http://api.openweathermap.org/data/2.5/weather?q=London,uk", null, responseListener);
	}
}