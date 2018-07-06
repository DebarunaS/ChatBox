/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {
  boolean loginMode = false;
  public void redirectifloggedin()
  {
    if(ParseUser.getCurrentUser()!=null)
    {
      Intent i=new Intent(getApplicationContext(),UserLlistActivity.class);
      startActivity(i);
    }
  }
  public void loginMode(View view)
  {
    Button LoginButton=(Button)findViewById(R.id.b1);
    TextView loginText=(TextView)findViewById(R.id.tv1);
    if(loginMode)
    {
      loginMode=false;
      LoginButton.setText("SignUp");
      loginText.setText("Or,LogIn");
    }
    else
    {
      loginMode=true;
      LoginButton.setText("LogIn");
      loginText.setText("Or,SignUp");

    }
  }

public void signup(View view) {
  EditText et1, et2;
  et1 = (EditText) findViewById(R.id.et1);
  et2 = (EditText) findViewById(R.id.et2);
  if (loginMode)
  {

   ParseUser.logInInBackground(et1.getText().toString(), et2.getText().toString(), new LogInCallback() {
     @Override
     public void done(ParseUser user, ParseException e) {

       if(e==null)
       {
         Log.i("Info","user Logged In");
         redirectifloggedin();
       }
       else {
         String message = e.getMessage();
         if (message.toLowerCase().contains("java")) {
           message=e.getMessage().substring(e.getMessage().indexOf(" "));
         }
           Toast.makeText(MainActivity.this,message , Toast.LENGTH_SHORT).show();

       }
     }
   });

  }
  else
    {
    ParseUser user = new ParseUser();
    user.setUsername(et1.getText().toString());
    user.setPassword(et2.getText().toString());
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.i("INFO", "user signed up");
          redirectifloggedin();

        } else {
          String message = e.getMessage();
          if (message.toLowerCase().contains("java")) {
            message=e.getMessage().substring(e.getMessage().indexOf(" "));
          }
          Toast.makeText(MainActivity.this,message , Toast.LENGTH_SHORT).show();
        }

      }
    });
  }
}
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

   setTitle("whatsapp Login");
    redirectifloggedin();
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}