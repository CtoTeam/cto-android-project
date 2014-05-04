package cto.team.certificatechecker.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class InvalidCertDialog extends DialogFragment {
	public String message;
 @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	 builder.setMessage(message).setPositiveButton("אישור", new OnClickListener() {	
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	});
	 
	 return builder.create();
		// TODO Auto-generated method stub
	
	
	}
}
