package cto.team.certificatechecker.networking.response;

public interface ResponseListener {

	public void onComplete(String result);
	public void onErrorResponse(String errorResponse);
}
