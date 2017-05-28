package com.raileanu.todolistspecial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.raileanu.todolistspecial.database.RealmDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.signInButton).setOnClickListener(this);
    }

    private boolean authenticationSuccessful(String username, String password)
    {
        RealmDbHelper dbHelper = new RealmDbHelper( getApplicationContext() );
        Log.d("authenticationSuccess", " username " +username +dbHelper.userExists(username)  );
        return  ( dbHelper.userExists(username) && password.equals(dbHelper.getPassword(username)) );
    }

    @Override
    public void onClick(View v)
    {
        findViewById(R.id.loginErrorTextView).setVisibility(View.INVISIBLE);

        switch (v.getId())
        {
            case R.id.loginButton:
                String userName = ((EditText) findViewById(R.id.loginEditText)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

                if (authenticationSuccessful(userName, password))
                {
                    Intent loginIntent = new Intent(this, UserSpaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", userName);
                    bundle.putString("password", password);
                    loginIntent.putExtra("main", bundle);

                    startActivity(loginIntent);
                }
                else
                {
                    findViewById(R.id.loginErrorTextView).setVisibility(View.VISIBLE);
                }

                break;

            case R.id.signInButton:
                Intent signInIntent = new Intent(this, SignInActivity.class);
                startActivity(signInIntent);
                break;
        }
    }
}
