package cto.team.certificatechecker.ui.activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import cto.team.certificatechecker.R;
import cto.team.certificatechecker.models.CarPermission;
import cto.team.certificatechecker.models.SoldierDetails;
import cto.team.certificatechecker.networking.request.AsyncTaskFtpRequest;
import cto.team.certificatechecker.networking.response.ModelResponseListener;
import cto.team.certificatechecker.networking.utils.ServerUtils;
import cto.team.certificatechecker.utils.SharedPreferencesWrapper;

public class MainActivity extends ActionBarActivity {

	private TextView soldierNameTextView;
	private TextView soldierIdTextView;
	private TextView certNumberTextView;
	private TextView certDateTextView;
	private TableLayout authorizationsTableLayout;
	private ImageView soldierImage;
	private RelativeLayout container; 
	
	private static final int COLUMN_WIDTH = 20;
	private static final int DATE_COLUMN_WIDTH = 30;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        } 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent intent = new Intent(this, SettingsActivity.class);
        	startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    	
    @Override
    protected void onResume() {
		super.onResume();
		
		soldierNameTextView = (TextView) findViewById(R.id.soldierName);
		soldierIdTextView = (TextView) findViewById(R.id.soldierId);
		certNumberTextView = (TextView) findViewById(R.id.certNumber);
		certDateTextView = (TextView) findViewById(R.id.certDate);
		authorizationsTableLayout = (TableLayout) findViewById(R.id.authorizationsTableContent);
    	
    	soldierImage = (ImageView)findViewById(R.id.soldierImage);
    	container = (RelativeLayout)findViewById(R.id.fragmentContainer);
    	
    	
		// Construct the data to write to the tag
		// Should be of the form [relay/group]-[rid/gid]-[cmd]
		
		//String nfcMessage = relay_type + "-" + id + "-" + cmd;
		 
		// When an NFC tag comes into range, call the main activity which handles writing the data to the tag
		NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		 
		Intent nfcIntent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//nfcIntent.putExtra("nfcMessage", nfcMessage);
		PendingIntent pi = PendingIntent.getActivity(this, 0, nfcIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);  
		 
		
		String[][] mTechLists = new String[][] { new String[] { NfcB.class.getName() } };
		
		try
		{
			//nfcAdapter.enableForegroundDispatch(this, pi, new IntentFilter[] {tagDetected}, mTechLists);
			nfcAdapter.enableForegroundDispatch(this, pi, new IntentFilter[] {tagDetected}, null);
		}
		catch (Exception e) {
			String str = e.getMessage();
		}    	
    }
    	
	@Override
	public void onNewIntent(Intent intent) {

		Resources res = this.getResources();
		final ProgressDialog dialog = ProgressDialog.show(this,
				res.getString(R.string.request_progress_dialog_title),
				res.getString(R.string.request_progress_dialog_content));
		
	    // When an NFC tag is being written, call the write tag function when an intent is
	    // received that says the tag is within range of the device and ready to be written to
	    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	    
	    byte[] certIdBytes = tag.getId();
    	String certId = certIdBytes[0] + "," + certIdBytes[1] + "," + certIdBytes[2] + "," + certIdBytes[3];
//    	params.put("nfcid", "12345");
	    // "http://www.hemed.podserver.info/?nfcid=12345"
    	
    	ModelResponseListener<SoldierDetails> modelResponseListener = new ModelResponseListener<SoldierDetails>(this, SoldierDetails.class) {
	    	@Override
        	public void onComplete(SoldierDetails result) {
	    		if(result == null) {
	    			dialog.dismiss();
	    			
	    			container.setVisibility(View.INVISIBLE);
	    			
					InvalidCertDialog icd = new InvalidCertDialog();
					icd.message = "�����! ����� �� �����";
					
					try {
						boolean sendSms = SharedPreferencesWrapper.sendSms(getApplicationContext());
						if (sendSms) {
							SmsManager smsManager = SmsManager.getDefault();
							smsManager.sendTextMessage("0506541177", null, "���� ����� ������ �� ����� ������ �����", null, null);
							Toast.makeText(getApplicationContext(),
									"SMS Sent!", Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "SMS faild",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					
					icd.show(getFragmentManager(), null);
					
					return;
	    		}
	    		
	    		new AsyncTaskFtpRequest(getApplicationContext(), dialog, soldierImage, container, result.IsStolen, getFragmentManager()).execute(Integer.toString(result.SoldierID));
	    		
        		soldierNameTextView.setText(result.Name);
        		soldierIdTextView.setText(Integer.toString(result.SoldierID));
        		certNumberTextView.setText(result.CertID);
        		certDateTextView.setText(result.ExpirationDate);
        		
        		authorizationsTableLayout.removeAllViews();
        		for (int i = 0; i < result.CarPermissions.length; i++)
        		{
        			CarPermission permission = result.CarPermissions[i];
        			authorizationsTableLayout.addView(generateRow(permission.CarID, permission.CarType, permission.Base, permission.StartDate, permission.ExpirationDate));
        		}
        	}
		};
		
	    ServerUtils.runGetRequest("http://www.hemed.podserver.info/?nfcid=" + certId, null, modelResponseListener);
	}
    
    private TableRow generateRow(String carNumber, String carType, String base,String startDate, String expirationDate)
    {
    	TableRow row = new TableRow(getApplicationContext());
    	row.addView(generateColumn(base, false));
    	row.addView(generateColumn(expirationDate,true));
    	row.addView(generateColumn(startDate, false));
    	row.addView(generateColumn(carType, true));
    	row.addView(generateColumn(carNumber, false));
    	
    	return row;
    }
    
    private TextView generateColumn(String value,boolean isEven)
    {
    	TextView column = new TextView(getApplicationContext());
    	column.setText(value);
    	column.setLayoutParams(findViewById(R.id.baseColumn).getLayoutParams());
    	column.setTextColor(Color.parseColor("#000000"));
    	column.setBackgroundColor(Color.parseColor(isEven ? "#dcdcdc" : "#d3d3d3"));
    	column.setGravity(Gravity.CENTER);
    	column.setTextSize(12f);
    	
		return column;
    }
}
