package cto.team.certificatechecker.networking.request;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import cto.team.certificatechecker.R;

public class AsyncTaskFtpRequest extends AsyncTask<String, String, Bitmap> {
	
	private FTPClient ftpClient;
	private ImageView imageView;
	private Context context;
	private ProgressDialog dialog;
	
	public AsyncTaskFtpRequest(Context context, ImageView imageView) {
		this.context = context;
		this.ftpClient = new FTPClient();
		this.imageView = imageView;
	}
	
	@Override
	protected void onPreExecute() {
		Resources res = context.getResources();
		dialog = ProgressDialog.show(context,
				res.getString(R.string.request_progress_dialog_title),
				res.getString(R.string.request_progress_dialog_content));
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		try {
		ftpClient.connect("ftp.podserver.info");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
	           boolean status = ftpClient.login("podi_14742696", "LeadLead");
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
		dialog.dismiss();
		imageView.setImageBitmap(result);
	};
}