package com.jianggest.boyassitant;

import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private String mMessage;
	private String mMessageNumber, mCallNumber;

	public AlarmReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mMessage = intent.getStringExtra("message");
		mMessageNumber = intent.getStringExtra("messageNumber");
		mCallNumber = intent.getStringExtra("callNumber");
		cancelAlarm(context);
		Toast.makeText(context, "����ʱ�䵽:" + mMessage, Toast.LENGTH_LONG).show();
		makeCall(context);
		sendSMS();
		
	}

	private void cancelAlarm(Context context) {
		Intent intent = new Intent("android.jianggest.alarmreceiver");
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Activity.ALARM_SERVICE);
		am.cancel(pi);
	}

	private void makeCall(Context mcontext) {
		if (mCallNumber != null) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + mCallNumber));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mcontext.startActivity(intent);
		}
	}

	/**
	 * ֱ�ӵ��ö��Žӿڷ�����
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public void sendSMS() {
		// ��ȡ���Ź�����
		if (mMessageNumber != null) {
			android.telephony.SmsManager smsManager = android.telephony.SmsManager
					.getDefault();
			// ��ֶ������ݣ��ֻ����ų������ƣ�
			List<String> divideContents = smsManager.divideMessage(mMessage);
			for (String text : divideContents) {
				smsManager.sendTextMessage(mMessageNumber, null, text, null,
						null);
			}
		}
	}

}
