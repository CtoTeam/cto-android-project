package cto.team.certificatechecker.networking.response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import cto.team.certificatechecker.R;

public class ProgressResponseListener implements ResponseListener {

	private ProgressDialog dialog;
	private Context context;

	public ProgressResponseListener(Context context) {
		this.context = context;
	}
	
	public void show() {
		Resources res = context.getResources();
		dialog = ProgressDialog.show(context,
				res.getString(R.string.request_progress_dialog_title),
				res.getString(R.string.request_progress_dialog_content));
	}

	@Override
	public void onComplete(String result) {
		dialog.dismiss();		
	}

	@Override
	public void onErrorResponse(String errorResponse) {
		dialog.dismiss();		
	}

}
