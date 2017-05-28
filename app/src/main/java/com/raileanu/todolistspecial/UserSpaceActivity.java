package com.raileanu.todolistspecial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.raileanu.todolistspecial.database.RealmDbHelper;
import com.raileanu.todolistspecial.database.Task;
import com.raileanu.todolistspecial.interfaces.OnDatabaseChanged;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class UserSpaceActivity extends AppCompatActivity implements View.OnClickListener, OnDatabaseChanged
{
    private ArrayList<Task> mTaskArrayList;
    private String mUserName;
    private TaskVerticalAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space);

        findViewById(R.id.logoutButton).setOnClickListener(this);
        findViewById(R.id.addTaskButton).setOnClickListener(this);
        findViewById(R.id.confirmAddButton).setOnClickListener(this);
        findViewById(R.id.cancelAddButton).setOnClickListener(this);

        Bundle infos = getIntent().getBundleExtra("main");
        mUserName = (String) infos.get("username");

        TextView userGreeting = (TextView) findViewById(R.id.userGreetingTextView);
        userGreeting.setText("Welcome, " + mUserName);

        mTaskArrayList = new ArrayList<>();

        // set up the
        mArrayAdapter = new TaskVerticalAdapter(mTaskArrayList, mUserName);
        mArrayAdapter.setOnDatabaseChanged( this );

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(UserSpaceActivity.this, LinearLayoutManager.VERTICAL, false);

        RecyclerView colorListRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        colorListRecyclerView.setLayoutManager(verticalLayoutManager);
        colorListRecyclerView.setAdapter(mArrayAdapter);

        getFromDB();
    }

    public void getFromDB()
    {
        RealmDbHelper dbHelper = new RealmDbHelper( getApplicationContext() );

        mTaskArrayList.clear();

        mTaskArrayList.addAll(dbHelper.getTasks(mUserName));

        mArrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.logoutButton:
                Intent loginIntent = new Intent(this, MainActivity.class);

                startActivity(loginIntent);
                break;
            case R.id.addTaskButton:

                findViewById(R.id.addTaskLayout).setVisibility(View.VISIBLE);

                break;
            case R.id.confirmAddButton:

                String taskName = ((EditText) findViewById(R.id.addTaskTextEdit)).getText().toString();
                new RealmDbHelper( getApplicationContext() ).insertTask(taskName, mUserName);

                getFromDB();

                findViewById(R.id.addTaskLayout).setVisibility(View.GONE);

                break;
            case R.id.cancelAddButton:
                findViewById(R.id.addTaskLayout).setVisibility(View.GONE);
                break;
        }

        ((EditText) findViewById(R.id.addTaskTextEdit)).setText("");
    }


    @Override
    public void refresh()
    {
        getFromDB();
    }
}
