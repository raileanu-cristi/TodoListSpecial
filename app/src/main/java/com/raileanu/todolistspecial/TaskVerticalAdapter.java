package com.raileanu.todolistspecial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.raileanu.todolistspecial.database.RealmDbHelper;
import com.raileanu.todolistspecial.database.Task;
import com.raileanu.todolistspecial.interfaces.OnDatabaseChanged;

import java.util.List;

/**
 * Created by Cristian on 28.05.2017.
 */

public class TaskVerticalAdapter extends RecyclerView.Adapter<TaskVerticalAdapter.ViewHolder> implements View.OnClickListener
{
    private List<Task> horizontalList;
    private String userName;
    OnDatabaseChanged onDatabaseChanged;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameTextView;
        public Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            deleteButton = (Button) view.findViewById(R.id.deleteTaskButton);
            nameTextView = (TextView) view.findViewById(R.id.taskNameTextView);
        }

    }


    public TaskVerticalAdapter(List<Task> horizontalList, String username)
    {
        this.horizontalList = horizontalList;
        this.userName = username;
    }


    //
    //
    //
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_manage_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        Task currentTask = horizontalList.get(position);

        if (currentTask == null)
            return;
        String taskName = "null";
        if (currentTask.getName() != null)
            taskName = currentTask.getName();

        holder.nameTextView.setText(taskName);
        holder.deleteButton.setTag(position);
        holder.deleteButton.setOnClickListener(this);

    }


    @Override
    public int getItemCount()
    {
        return horizontalList.size();
    }

    public void setOnDatabaseChanged(OnDatabaseChanged context )
    {
        onDatabaseChanged = context;
    }

    @Override
    public void onClick(View view)
    {
        int position = (int) view.getTag();
//        Integer color = horizontalList.get(position);

        if (view.getId() == R.id.deleteTaskButton)
        {
            new RealmDbHelper( view.getContext() ).deleteTask(position, userName);

        }

        onDatabaseChanged.refresh();
    }
}
