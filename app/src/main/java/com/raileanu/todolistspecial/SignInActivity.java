package com.raileanu.todolistspecial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.raileanu.todolistspecial.database.RealmDbHelper;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.backButton).setOnClickListener(this);
    }

    boolean userExists(String userName)
    {
        RealmDbHelper dbHelper = new RealmDbHelper( getApplicationContext() );

        return dbHelper.userExists(userName);
    }

    @Override
    public void onClick(View v)
    {
        findViewById(R.id.registerErrorTextView).setVisibility(View.INVISIBLE);

        switch (v.getId())
        {
            case R.id.registerButton:
                String userName = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordRegisterEditText)).getText().toString();

                if (!userExists(userName))
                {
                    // add to database
                    RealmDbHelper dbHelper = new RealmDbHelper( getApplicationContext());

                    dbHelper.insertUser(userName, password);

                    // log in
                    Intent loginIntent = new Intent(this, UserSpaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", userName);
                    bundle.putString("password", password);
                    loginIntent.putExtra("main", bundle);

                    startActivity(loginIntent);
                }
                else
                {
                    // display UserNameExists error
                    findViewById(R.id.registerErrorTextView).setVisibility(View.VISIBLE);
                }

                break;

            case R.id.backButton:
                finish();
                break;
        }
    }
}
