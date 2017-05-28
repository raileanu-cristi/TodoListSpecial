package com.raileanu.todolistspecial.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Cristian on 27.05.2017.
 */

public class RealmDbHelper
{
    public RealmDbHelper(Context context)
    {
        Realm.init( context );
        Realm realm = Realm.getDefaultInstance();
    }

    private RealmResults<User> findUsers(String username)
    {
        Log.d("findUsers", " start ");

        Realm realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<User> query = realm.where(User.class);
        // Add query conditions:
        query.equalTo("mName", username);

        // Execute the query:
        RealmResults<User> result1 = query.findAll();

        return result1;
    }

    public boolean userExists(String username)
    {
        return findUsers(username).size() > 0;
    }

    public void insertUser(String name, String password)
    {
        if (userExists(name))
            return;

        Log.d("insertUser", " start: "+name + " " + password);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User newUser = realm.createObject(User.class);
        newUser.setmName(name);
        newUser.setmPassword(password);
        realm.commitTransaction();

        Log.d("insertUser", " end "+name + " " + password);
    }

    public void insertTask(String taskName, String userName)
    {
        Realm realm = Realm.getDefaultInstance();

        User foundUser = findUsers(userName).first();

        Task newTask = new Task();
        newTask.setName(taskName);

        realm.beginTransaction();
        final Task managedNewTask = realm.copyToRealm(newTask);
        foundUser.getmTasks().add(managedNewTask);
        realm.commitTransaction();
    }

    public void deleteTask(int taskNr, String userName)
    {
        Realm realm = Realm.getDefaultInstance();

        User foundUser = findUsers(userName).first();

        final Task task = foundUser.getmTasks().get(taskNr);
        realm.beginTransaction();
        task.deleteFromRealm();
        realm.commitTransaction();
    }

    public String getPassword(String username)
    {
        User foundUser = findUsers(username).first();

        return foundUser.getmPassword();
    }

    public ArrayList<Task> getTasks(String username)
    {
        User foundUser = findUsers(username).first();

        ArrayList<Task> result = new ArrayList<>();
        result.addAll( foundUser.getmTasks() );

        return result;
    }




}
