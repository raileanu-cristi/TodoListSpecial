package com.raileanu.todolistspecial.database;

import io.realm.RealmObject;

/**
 * Created by Cristian on 28.05.2017.
 */

public class Task extends RealmObject
{
    String mName;
    String mDate;

    public void setName(String taskName)
    {
        mName = taskName;
    }
    public void setDate(String taskDate)
    {
        mDate = taskDate;
    }
    public String getName()
    {
        return mName;
    }
    String getDate()
    {
        return mDate;
    }
}
