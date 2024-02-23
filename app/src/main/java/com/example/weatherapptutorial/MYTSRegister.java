package com.example.weatherapptutorial;


import java.net.URL;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.board.mDatabase;

public class MYTSRegister extends Activity 
{
	Button myregister;
	EditText account;
	EditText mypwd;
	TextView loginErrorMsg;

	public static String IPAddress;

	public static MYTSRegister rent;

	String saccount, spwd;

	EditText occupation, email, age;
	
	static String thisaccount;
	static String groupid;

	Spinner spinner;
	Spinner myid;
	Spinner spinner2;

	private mDatabase rDatabase;
	private Cursor mCursor;

	Spinner sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myregister);

		rent = this;

		Resources res = getResources();
		rDatabase = new mDatabase(getApplicationContext());


		myregister = (Button) findViewById(R.id.mregister);
		//addr, id, sex, phone, mail
		account = (EditText) findViewById(R.id.account);
		mypwd = (EditText) findViewById(R.id.mypwd);
		email = (EditText) findViewById(R.id.email);

		age = (EditText) findViewById(R.id.age);
		//occupation = (EditText) findViewById(R.id.occupation);
		spinner = (Spinner) findViewById(R.id.spinner);
		myid = (Spinner) findViewById(R.id.myid);

		final String[] sex = {"男性", "女性"};
		ArrayAdapter<String> Sex = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				sex);
		myid.setAdapter(Sex);


		final String[] lunch = {"資訊業", "服務業", "教育業"};
		ArrayAdapter<String> lunchList = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				lunch);
		spinner.setAdapter(lunchList);

		//check login
        myregister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				int add=1;
				String acc = account.getText().toString();
				String pwd = mypwd.getText().toString();

				mCursor = rDatabase.getSCOREList();
				mCursor.moveToFirst();
				//no data
				if (mCursor.isAfterLast())
				{
					add=1;
				}
				mCursor.moveToFirst();

				while(!mCursor.isAfterLast())
				{
					// mCursor.getString(2)
					if (acc.equals(mCursor.getString(1)))
					{
						add=0;
					}
					mCursor.moveToNext();
				}

				if (add==1) {
					rDatabase.inputScore(acc,pwd);
					Toast.makeText(view.getContext(), "註冊成功", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(view.getContext(), "註冊失敗", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
