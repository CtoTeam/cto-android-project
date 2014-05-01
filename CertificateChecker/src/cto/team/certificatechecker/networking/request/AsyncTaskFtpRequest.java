package cto.team.certificatechecker.networking.request;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTaskFtpRequest extends AsyncTask<String, String, Bitmap> {
	
	private FTPClient ftpClient;
	private ImageView imageView;
	private ProgressDialog dialog;
	
	public AsyncTaskFtpRequest(ProgressDialog dialog, ImageView imageView) {
		this.ftpClient = new FTPClient();
		this.imageView = imageView;
		this.dialog = dialog;
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