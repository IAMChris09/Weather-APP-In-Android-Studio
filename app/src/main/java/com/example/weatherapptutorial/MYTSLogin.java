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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.board.mDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MYTSLogin extends Activity 
{
	Button mylogin;
	Button myregister;
	Button abouts;
	EditText account;
	EditText mypwd;
	TextView loginErrorMsg;
	
	public static String IPAddress;
	
	public static MYTSLogin rent;

	String saccount, spwd, smail, sreg, sphone, sspwd;
	
	static String accountid;
	static String groupid;
	
	String myaccountid;

	private mDatabase rDatabase;
	private Cursor mCursor;

	FloatingActionButton mAddFab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myts);

		rent = this;

		account = (EditText) findViewById(R.id.account);
		mypwd = (EditText) findViewById(R.id.mypwd);
		mylogin = (Button) findViewById(R.id.mylogin);

		rDatabase = new mDatabase(getApplicationContext());

		//check login
		mylogin.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view)
				{
					String acc = account.getText().toString();
					String pwd = mypwd.getText().toString();

					mCursor = rDatabase.getSCOREList();
					mCursor.moveToFirst();
					//no data
					if (mCursor.isAfterLast())
					{
						Toast.makeText(view.getContext(), "登入錯誤", Toast.LENGTH_LONG).show();
						return;
					}
					mCursor.moveToFirst();

					while(!mCursor.isAfterLast())
					{
						// mCursor.getString(2)
						if (acc.equals(mCursor.getString(1)) && pwd.equals(mCursor.getString(2)))
						{
							Intent app = new Intent(MYTSLogin.this, MainActivity.class);
							Bundle ndata = new Bundle();
							ndata.putString("user", account.getText().toString());
							app.putExtras(ndata);
							startActivity(app);
							break;
						}
						mCursor.moveToNext();
					}


				}
		});

		mAddFab = findViewById(R.id.add_fab);

		mAddFab.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent app = new Intent(MYTSLogin.this, MYTSRegister.class);
						startActivity(app);
					}
				});


		/*
        Button mylogin2 = (Button) findViewById(R.id.mylogin2);

        //check login
        mylogin2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent app = new Intent(MYTSLogin.this, MYTSRegister.class);
                startActivity(app);
            }
        });*/


	}
	

    
}
