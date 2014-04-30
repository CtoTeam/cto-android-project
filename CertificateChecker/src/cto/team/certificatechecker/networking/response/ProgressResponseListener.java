package cto.team.certificatechecker.networking.response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cto.team.certificatechecker.R;
import cto.team.certificatechecker.models.NetworkModel;

public abstract class ProgressResponseListener<T extends NetworkModel> implements ResponseListener {

	private ProgressDialog dialog;
	private Context context;
	private Class<T> modelClass;

	public ProgressResponseListener(Context context, Class<T> modelClass) {
		this.context = context;
		this.modelClass = modelClass;
	}
	
	public void showProgressBar() {
		Resources res = context.getResources();
		dialog = ProgressDialog.show(context,
				res.getString(R.string.request_progress_dialog_title),
				res.getString(R.string.request_progress_dialog_content));
	}
	
	private void dismissProgressBar() {
		if(dialog != null) {
			dialog.dismiss();
		}
	}
	
	public abstract void onComplete(T modelResult);

	@Override
	public void onComplete(JsonObject result) {
		T fromJson = new Gson().fromJson(result, modelClass);
		onComplete(fromJson);
		dismissProgressBar();
	}

	@Override
	public void onErrorResponse(String errorResponse) {
		dismissProgressBar();		
	}

}