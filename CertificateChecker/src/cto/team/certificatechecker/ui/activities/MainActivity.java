package cto.team.certificatechecker.ui.activities;

import java.sql.Date;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.JsonObject;

import cto.team.certificatechecker.R;
import cto.team.certificatechecker.networking.response.ProgressResponseListener;
import cto.team.certificatechecker.networking.utils.ServerUtils;

public class MainActivity extends ActionBarActivity {

	private TextView soldierNameTextView;
	private TextView soldierIdTextView;
	private TextView certNumberTextView;
	private TextView certDateTextView;
	private TableLayout authorizationsTableLayout;
	
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
		
	    soldierNameTextView = (TextView)findViewById(R.id.soldierName);
    	soldierIdTextView = (TextView)findViewById(R.id.soldierId);
    	certNumberTextView = (TextView)findViewById(R.id.certNumber);
    	certDateTextView = (TextView)findViewById(R.id.certDate);
    	authorizationsTableLayout = (TableLayout)findViewById(R.id.authorizationsTableContent);
    	
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
	    // When an NFC tag is being written, call the write tag function when an intent is
	    // received that says the tag is within range of the device and ready to be written to
	    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	    String ndefmsg = intent.getParcelableExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	    
	    byte[] iddd = tag.getId();
    	
//    	Map<String,String> params = new HashMap<String, String>();
//    	params.put("nfcid", "12345");
	    // "http://www.hemed.podserver.info/?nfcid=12345"
	    
    	ServerUtils.runGetRequest("api.openweathermap.org/data/2.5/weather?q=London,uk", null, new ProgressResponseListener(this) {
			@Override
        	public void onComplete(JsonObject result) {
        		// TODO Auto-generated method stub
        		super.onComplete(result);

        		soldierNameTextView.setText("נדב קרמר");
        		soldierIdTextView.setText("5791171");
        		certNumberTextView.setText("87654321");
        		certDateTextView.setText("30/4/2014");
        		
        		for (int i = 0; i < 20; i++)
        		{
        			authorizationsTableLayout.addView(generateRow("12-345-67", "מאזדה", "צריפין",new Date(2012,9,24), new Date(2014,9,24)));
        		}
        	}
        });
	}
    
    private TableRow generateRow(String carNumber, String carType, String base,Date startionDate, Date expirationDate)
    {
    	TableRow row = new TableRow(getApplicationContext());
    	row.addView(generateColumn(base,COLUMN_WIDTH, false));
    	row.addView(generateColumn(DateFormat.format("dd/MM/yyyy", expirationDate).toString(),DATE_COLUMN_WIDTH ,true));
    	row.addView(generateColumn(DateFormat.format("dd/MM/yyyy", startionDate).toString(),DATE_COLUMN_WIDTH, true));
    	row.addView(generateColumn(carType,COLUMN_WIDTH, false));
    	row.addView(generateColumn(carNumber,COLUMN_WIDTH, true));
    	
    	return row;
    }
    
    private TextView generateColumn(String value,int width,  boolean isEven)
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
