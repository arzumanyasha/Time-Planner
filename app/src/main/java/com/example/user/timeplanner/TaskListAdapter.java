package com.example.user.timeplanner;

/**
 * Created by User on 07.05.2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private final Activity context;
    ArrayList<Task> tasks;
    //private final String[] itemname;
    //private Spinner deleteSpinner;
    //private final Integer[] imgid;

    public TaskListAdapter(Activity context, ArrayList<Task> tasks) {
        super (context, R.layout.tasklist, tasks);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.tasks=tasks;
        //this.itemname=itemname;
        //this.artistName = artistName;

        // this.imgid=imgid;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView=inflater.inflate(R.layout.tasklist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView dateView = (TextView) rowView.findViewById(R.id.textView1);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.textView2);
        //Spinner deleteSpinner = (Spinner) rowView.findViewById(R.id.spinner2);

        //txtTitle.setText(itemname[position]);
        txtTitle.setText(tasks.get(position).getTaskName());
        //Uri uri = Uri.parse(artists.get(position).getImgUrl());
        //Picasso.with(context).load(uri).into(imageView);
        switch (tasks.get(position).getTaskCategory()){
            case work: imageView.setImageResource(R.drawable.work);
            break;
            case phystraining: imageView.setImageResource(R.drawable.phys);
            break;
            case rest: imageView.setImageResource(R.drawable.rest);
            break;
            case housework: imageView.setImageResource(R.drawable.houework);
            break;
            case meal: imageView.setImageResource(R.drawable.meal);
            break;
            case transport: imageView.setImageResource(R.drawable.transport);
            break;
            case learning: imageView.setImageResource(R.drawable.learning);
            break;
            default:
                break;
        }
        //final Context context = view.getContext();
        Date startTime = new Date(tasks.get(position).getTaskStartTime());
        Date endTime = new Date(tasks.get(position).getTaskEndTime());
        dateView.setText(startTime.getHours() + ":" + startTime.getMinutes() + " - "
                + endTime.getHours() + ":" + endTime.getMinutes());
        descriptionView.setText(tasks.get(position).getTaskDescription());
/*
        deleteSpinner = (Spinner) rowView.findViewById(R.id.spinner2);
        ArrayAdapter<?> spinnerAdapter =
                ArrayAdapter.createFromResource(context, R.array.deletelist, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteSpinner.setAdapter(spinnerAdapter);

        deleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = context.getResources().getStringArray(R.array.deletelist);
                Toast.makeText(context, choose + " selected!", Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        return rowView;

    };
}
