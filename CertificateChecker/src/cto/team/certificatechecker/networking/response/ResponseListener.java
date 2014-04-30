package cto.team.certificatechecker.networking.response;

import com.google.gson.JsonObject;

public interface ResponseListener {
	public void onComplete(JsonObject result);
	public void onErrorResponse(String errorResponse);
}
