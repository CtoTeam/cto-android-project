package cto.team.certificatechecker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import cto.team.certificatechecker.R;


public class SharedPreferencesWrapper {
    
	private static SharedPreferences getDefaultSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private static String getStringFromResource(Context context, int id) {
		return context.getResources().getString(id);
	}
	
	public static boolean sendSms(Context context) {
		String preferenceKeySendSms = getStringFromResource(context, R.string.preferences_key_send_sms);
		boolean sendSms = getDefaultSharedPreferences(context).getBoolean(preferenceKeySendSms, false);
		
		return sendSms;
	}
}
