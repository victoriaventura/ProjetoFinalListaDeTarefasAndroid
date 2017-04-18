package com.example.oddvicky.projetofinallistadetarefas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oddvicky.projetofinallistadetarefas.R;
import com.example.oddvicky.projetofinallistadetarefas.model.Task;

import java.util.List;

public class ListAndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Task> tasks;

    public ListAndroidAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.tasks = tasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_spinner_item, parent, false);
        return new AndroidItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AndroidItemHolder androidItemHolder = (AndroidItemHolder)holder;
        androidItemHolder.tvCategory.setText(tasks.get(position).getCategory().getName());
        androidItemHolder.tvName.setText(tasks.get(position).getName());
        androidItemHolder.tvDescription.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder {

        TextView tvCategory, tvName, tvDescription;

        public AndroidItemHolder(View itemView) {
            super(itemView);

            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        }

    }
}
