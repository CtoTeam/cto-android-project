package cto.team.certificatechecker.networking.utils;

import java.util.Map;

import cto.team.certificatechecker.networking.request.AsyncTaskRequest;
import cto.team.certificatechecker.networking.response.ResponseListener;

public class ServerUtils {
	
	public static void runGetRequest(String url, Map<String, String> params, ResponseListener responseListener) {
		AsyncTaskRequest request = new AsyncTaskRequest(responseListener);
		request.execute(url);
	}
	
}
