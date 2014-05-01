package cto.team.certificatechecker.networking.response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.content.Context;
import cto.team.certificatechecker.models.NetworkModel;

public abstract class ModelResponseListener<T extends NetworkModel> extends ProgressResponseListener {

	private Class<T> modelClass;
	
	public ModelResponseListener(Context context, Class<T> modelClass) {
		super(context);
		this.modelClass = modelClass;
	}
	
	public abstract void onComplete(T result);

	@Override
	public void onComplete(JsonObject result) {
		super.onComplete(result);
		T fromJson = new Gson().fromJson(result, modelClass);
		onComplete(fromJson);
	}
}
