//
// This is a activity to set the an alarm to send a message or make call
//
// Author: Jianggest@gmail.com
// Time : 2014年7月21日20:59:31
//
package com.jianggest.boyassitant;

import java.nio.channels.SelectableChannel;
import java.util.Calendar;

import com.jianggest.boyassitant.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Tips extends Activity {

	private final static String TAG = "Tips";
	private Calendar c = null;
	Button btnDateButton, btnTimeButton, btnConfirme;
	private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMintes = 0,
			mSecond = 0;
	private String mMessageString;
	EditText edtMessage;
	CheckBox sendMessageCheckBox, makecallCheckBox;
	String mMessagePhoneNumber=null, mCallPhoneNumber=null;
	final static int MESSAGE_FLAG = 1;
	final static int CALL_FLAG = 2;
	final static int CANCEL_FALG = 3;
	TextView txtMessageNumber,txtCallNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips_layout);

		txtMessageNumber = (TextView)findViewById(R.id.txtMessagenumber);
		txtCallNumber = (TextView)findViewById(R.id.txtPhonenumber);
		initDateTime();
		setDate();
		setTime();
		getMessage();
		setConfirme();
		setCheckBox();
	}

	//set the sendMessage or makeCall check box .
	private void setCheckBox() {
		sendMessageCheckBox = (CheckBox) findViewById(R.id.ckSendMessage);
		sendMessageCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						getPhoneNumber(MESSAGE_FLAG, arg1);

					}
				});

		makecallCheckBox = (CheckBox) findViewById(R.id.ckCall);
		makecallCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						getPhoneNumber(CALL_FLAG, arg1);

					}
				});
	}
	
	//show a pop up dialog to input the phone number.
	private void getPhoneNumber(final int fromFlag, boolean isSet) {
		final EditText inputServer = new EditText(this);
		if (fromFlag == 1) {
			if(mMessagePhoneNumber!=null)
				inputServer.setText(mMessagePhoneNumber);
			else if(mCallPhoneNumber!=null)
				inputServer.setText(mCallPhoneNumber);
		}else if(fromFlag == 2){
			if(mCallPhoneNumber!=null)
				inputServer.setText(mCallPhoneNumber);
			else
				inputServer.setText(mMessagePhoneNumber);
		}
		
		if (isSet) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String inpuString = getResources().getString(R.string.InputNumber);
			String okString = getResources().getString(R.string.ok);
			String calcelString = getResources().getString(R.string.cancel);
			builder.setTitle(inpuString)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(inputServer).setNegativeButton(calcelString, null);
			builder.setPositiveButton(okString,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							if (fromFlag == 1) {
								mMessagePhoneNumber = inputServer.getText().toString();
								showNumber(fromFlag,mMessagePhoneNumber);
							} else if (fromFlag == 2) {
								mCallPhoneNumber = inputServer.getText().toString();
								showNumber(fromFlag,mCallPhoneNumber);
							}
							
						}
					});
			builder.show();
		}
		
		
	}

	private void showNumber(int flag,String number){
		if(flag == 1){
			txtMessageNumber.setText(number);
		}else if(flag == 2){
			txtCallNumber.setText(number);
		}
		
	}
	
	private void getMessage() {
		edtMessage = (EditText) findViewById(R.id.edtMessage);
		mMessageString = edtMessage.getText().toString();
	}

	//init the date and time for the select the date and time button.
	private void initDateTime(){
		c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMintes = c.get(Calendar.MINUTE);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mYear = c.get(Calendar.YEAR);
		String dateString = mYear + "." + (mMonth+1) + "." + mDay;
		String time = mHour + ":" + mMintes;
		btnDateButton = (Button) findViewById(R.id.btnDate);
		btnDateButton.setText(dateString);
		btnTimeButton = (Button) findViewById(R.id.btnTime);
		btnTimeButton.setText(time);
	}
	private void setDate() {
		
		btnDateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pickDate();
			}
		});
	}

	private void setTime() {
		
		btnTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pickTime();
			}
		});
	}

	//show a pop up dialog to get the time 
	private void pickTime() {
		c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		c.setTimeInMillis(System.currentTimeMillis());
		new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				String time = hourOfDay + ":" + minute;
				mHour = hourOfDay;
				mMintes = minute;
				btnTimeButton.setText(time);
			}

		}, hour, minute, true).show();
	}

	//show a pop up dialog to get the date
	private void pickDate() {
		c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int monthOfYear = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
				String dateString = year + "." + (monthOfYear+1)+ "." + dayOfMonth;
				mYear = year;
				mMonth = monthOfYear;
				mDay = dayOfMonth;
				btnDateButton.setText(dateString);
			}
		}, year, monthOfYear, dayOfMonth).show();
	}

	private void setConfirme() {
		btnConfirme = (Button) findViewById(R.id.btnComfirme);
		btnConfirme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setAlarm();
			}
		});
	}

	//the core code to set a alarm 
	protected void setAlarm() {
		getMessage();
		c = Calendar.getInstance();
		// TODO Auto-generated method stub
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.MONTH, mMonth);
		c.set(Calendar.DAY_OF_MONTH, mDay);
		c.set(Calendar.HOUR_OF_DAY, mHour);
		c.set(Calendar.MINUTE, mMintes);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Intent intent = new Intent("android.jianggest.alarmreceiver");
		intent.putExtra("message", mMessageString);
		intent.putExtra("messageNumber", mMessagePhoneNumber);
		intent.putExtra("callNumber", mCallPhoneNumber);
		PendingIntent pi = PendingIntent.getBroadcast(Tips.this, 0, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);// 设置闹钟
//		am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
//				(100 * 1000), pi);// 重复设置

		Toast.makeText(Tips.this, "设置的闹钟时间为" + mHour + ":" + mMintes,
				Toast.LENGTH_LONG).show();

	}
}
