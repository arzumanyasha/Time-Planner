package com.example.user.timeplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.example.user.timeplanner.Category.housework;
import static com.example.user.timeplanner.Category.learning;
import static com.example.user.timeplanner.Category.meal;
import static com.example.user.timeplanner.Category.phystraining;
import static com.example.user.timeplanner.Category.rest;
import static com.example.user.timeplanner.Category.transport;
import static com.example.user.timeplanner.Category.work;

public class TaskInfoActivity extends AppCompatActivity implements DescriptionDialog.DescriptionDialogListener{

    private TextClock startTxtClock;
    private TextClock endTxtClock;
    private TextView dateTxtView;
    private int day, month, year;
    private int hour, minute;
    private Date startdate;
    private Date enddate;
    private Date taskDate;
    private Button addDescriptionBtn;

    private Category category;

    private Realm mRealm;

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.descriptionBtn)
    Button descriptionBtn;

    @BindView(R.id.categorySpinner)
    Spinner categorySpinner;

    @BindView(R.id.importanceSpinner)
    Spinner importanceSpinner;

    //private Spinner deleteSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        mRealm = Realm.getDefaultInstance();

        Calendar currentDate = Calendar.getInstance();

        addDescriptionBtn = (Button) findViewById(R.id.descriptionBtn);
        startTxtClock = (TextClock) findViewById(R.id.startTextClock);
        endTxtClock = (TextClock) findViewById(R.id.endTextClock);
        dateTxtView = (TextView) findViewById(R.id.dateTxtView);
        //deleteSpinner = (Spinner) findViewById(R.id.spinner2);


        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);
        //month++;

        dateTxtView.setText(day + "/" + (month+1) + "/" + year);

        hour = currentDate.get(Calendar.HOUR_OF_DAY);
        minute = currentDate.get(Calendar.MINUTE);

        //categorySpinner.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, Category.values()));

        addDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        startTxtClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");*/
                TimePickerDialog timePickerDialog = new TimePickerDialog(TaskInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        startTxtClock.setFormat24Hour(hourOfDay + ":" + minuteOfHour);
                        startdate = new Date(0, 0,0, hourOfDay, minuteOfHour);
                    }
                }, hour, minute, DateFormat.is24HourFormat(TaskInfoActivity.this));

                timePickerDialog.show();
            }
        });

        endTxtClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                DialogFragment timePicker = new timePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");*/
                TimePickerDialog timePickerDialog = new TimePickerDialog(TaskInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        endTxtClock.setFormat24Hour(hourOfDay + ":" + minuteOfHour);
                        enddate = new Date(0, 0,0, hourOfDay, minuteOfHour);
                    }
                }, hour, minute, DateFormat.is24HourFormat(TaskInfoActivity.this));

                timePickerDialog.show();
            }
        });

        dateTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskInfoActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        //monthOfYear++;
                        dateTxtView.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                        taskDate = new Date(year, (monthOfYear+1),dayOfMonth, 0, 0);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
    }

    public void openDialog(){
        DescriptionDialog descriptionDialog = new DescriptionDialog();
        descriptionDialog.show(getSupportFragmentManager(),"Task description");
    }

    @Override
    public void applyTexts(String description) {
        addDescriptionBtn.setText(description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.task_info, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_done:
                if(startdate.getTime()< enddate.getTime()){
                mRealm.beginTransaction();
                Random rn = new Random();
                Task task = mRealm.createObject(Task.class, rn.nextInt(1000));


                //проверка на уникальность
                //task.setTaskId(rn.nextInt(1000));
                task.setTaskName(getTrimmedName());
                switch (categorySpinner.getSelectedItem().toString()){
                    case "Work": task.setTaskCategory(work);
                        break;
                    case "Phystraining": task.setTaskCategory(phystraining);
                        break;
                    case "Rest": task.setTaskCategory(rest);
                        break;
                    case "Housework": task.setTaskCategory(housework);
                        break;
                    case "Meal": task.setTaskCategory(meal);
                        break;
                    case "Transport": task.setTaskCategory(transport);
                        break;
                    case "Learning": task.setTaskCategory(learning);
                        break;
                    default:
                        break;
                }
                task.setTaskDescription(getTrimmedDescription());
                task.setTaskStartTime(startdate.getTime());
                task.setTaskEndTime(enddate.getTime());
                task.setTaskDate(taskDate.getTime());
                switch (importanceSpinner.getSelectedItem().toString()){
                    case "Urgent and important": task.setTaskImportance(Importance.urgentAndImportant);
                        break;
                    case "Urgent and unimportant": task.setTaskImportance(Importance.urgentAndUnimportant);
                        break;
                    case "Non-urgent and important": task.setTaskImportance(Importance.nonurgentAndImportant);
                        break;
                    case "Non-urgent and unimportant": task.setTaskImportance(Importance.nonurgentAndUnimportant);
                        break;
                    default:
                        break;
                }
                mRealm.commitTransaction();
                startActivity(new Intent(TaskInfoActivity.this, MainActivity.class));}
                else Toast.makeText(this, "Start time might be bigger than end time", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getTrimmedName(){
        return nameEditText.getText().toString();
    }

    private String getTrimmedDescription(){
        return descriptionBtn.getText().toString().trim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
    //Событие нажатия кнопки accept и проверка чтобы время начала не было позже времени окончания
}
