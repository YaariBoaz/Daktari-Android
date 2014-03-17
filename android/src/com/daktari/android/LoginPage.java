package com.daktari.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class LoginPage extends Activity{

	EditText phone;
	EditText email;
	EditText name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		Parse.initialize(this,"at13zo9lNQjWBAiCEHvyaDvJNsG1SfGJFFo4nMw1", "DJU6OyRjQ5OPNX0VwF9G7k6nn958adJaflrFhb51");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(getBaseContext(), "all", HotStuff.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();		
		phone  = (EditText)findViewById(R.id.phoneNumber);
		email  = (EditText)findViewById(R.id.email);
		name  = (EditText)findViewById(R.id.fullName);
	}

	public void saveUser(View v)
	{	
		if(!isUserExists())
		{
			if(isOk())
			{
				ParseObject gameScore = new ParseObject("Users");
				gameScore.put("email", email.getText().toString());
				gameScore.put("name",name.getText().toString());
				gameScore.put("phone", phone.getText().toString());
				gameScore.saveInBackground();



				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
				Editor editor = pref.edit();
				editor.putBoolean("isRegistered",true);
				editor.commit();


				Intent in=new Intent(LoginPage.this,GridViewActivity.class);
				startActivity(in);
				finish();
			}
			else
			{
				Toast.makeText(getBaseContext(),"אנא מלא את כל השדות",Toast.LENGTH_SHORT).show();
			}	
		}
		else
		{
			SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			Editor editor = pref.edit();
			editor.putBoolean("isRegistered",true);
			editor.commit();


			Intent in=new Intent(LoginPage.this,GridViewActivity.class);
			startActivity(in);
			finish();
		}
	}


	private boolean isUserExists() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
		query.whereEqualTo("email", email.getText().toString());
		try {
			return query.find().size()>0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isOk()
	{	
		if(phone.getText()!=null&&name.getText()!=null&&isValidEmail(email.getText()))
		{	
			return true;
		}
		else
		{
			return false;
		}	
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

}
