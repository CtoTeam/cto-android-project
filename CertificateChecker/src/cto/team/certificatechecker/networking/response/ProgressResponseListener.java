package cto.team.certificatechecker.networking.response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.JsonObject;

import cto.team.certificatechecker.R;

public abstract class ProgressResponseListener implements ResponseListener {

	private ProgressDialog dialog;
	private Context context;

	public ProgressResponseListener(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public void showProgressBar() {
		Resources res = getContext().getResources();
		dialog = ProgressDialog.show(context,
				res.getString(R.string.request_progress_dialog_title),
				res.getString(R.string.request_progress_dialog_content));
	}
	
	protected void dismissProgressBar() {
		if(dialog != null) {
			dialog.dismiss();
		}
	}
	
	@Override
	public void onComplete(JsonObject result) {
		dismissProgressBar();
	}

	@Override
	public void onErrorResponse(String errorResponse) {
		dismissProgressBar();
	}

}