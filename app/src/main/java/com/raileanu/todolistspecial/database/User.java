package com.raileanu.todolistspecial.database;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Cristian on 28.05.2017.
 */

public class User extends RealmObject
{
    private String mName;
    private String mPassword;
    private RealmList<Task> mTasks;

    public User()
    {
        mTasks = new RealmList<>();
    }


    public String getmName()
    {
        return mName;
    }


    public void setmName(String mName)
    {
        this.mName = mName;
    }


    public String getmPassword()
    {
        return mPassword;
    }

    public RealmList<Task> getmTasks()
    {
        return mTasks;
    }


    public void setmPassword(String mPassword)
    {
        this.mPassword = mPassword;
    }
}
