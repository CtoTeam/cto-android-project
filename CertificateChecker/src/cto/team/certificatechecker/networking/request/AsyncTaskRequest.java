package cto.team.certificatechecker.networking.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cto.team.certificatechecker.networking.response.ResponseListener;

public class AsyncTaskRequest extends AsyncTask<String, String, String> {
	
	private ResponseListener responseListener;
	
	public AsyncTaskRequest(ResponseListener responseListener) {
		this.responseListener = responseListener;
	}

	@Override
	protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(params[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
        	responseListener.onErrorResponse(e.getMessage());
        }
        return responseString;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JsonObject resultAsJsonObject = new JsonParser().parse(result).getAsJsonObject();
		responseListener.onComplete(resultAsJsonObject);
	}
	
}
