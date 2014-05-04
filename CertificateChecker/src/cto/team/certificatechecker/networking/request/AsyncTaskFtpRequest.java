package cto.team.certificatechecker.networking.request;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cto.team.certificatechecker.ui.activities.InvalidCertDialog;

public class AsyncTaskFtpRequest extends AsyncTask<String, String, Bitmap> {
	
	private FTPClient ftpClient;
	private ImageView imageView;
	private ProgressDialog dialog;
	private int isValid;
	private RelativeLayout relativeLayout;
	private FragmentManager fragmentManager;
	
	public AsyncTaskFtpRequest(ProgressDialog dialog, ImageView imageView, RelativeLayout relativeLayout, int isValid, FragmentManager fragmentManager) {
		this.ftpClient = new FTPClient();
		this.imageView = imageView;
		this.dialog = dialog;
		this.relativeLayout = relativeLayout;
		this.isValid = isValid;
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		try {
		ftpClient.connect("ftp.podserver.info");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
	           ftpClient.login("podi_14742696", "LeadLead");
	           ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	           ftpClient.enterLocalPassiveMode();
	           ftpClient.changeWorkingDirectory("/htdocs");

	           InputStream imageInputStream = ftpClient.retrieveFileStream(params[0] + ".png");
	           
	           Bitmap image = BitmapFactory.decodeStream(imageInputStream);
	           
	           return image;
	        }
		
		return null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
	
	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
		relativeLayout.setVisibility(View.VISIBLE);
		if (this.isValid == 0)
		{
			InvalidCertDialog icd = new InvalidCertDialog();
			icd.message = "אזהרה! תעודה גנובה";
			icd.show(fragmentManager, null);		
		}
		
		dialog.dismiss();
		
	};
}