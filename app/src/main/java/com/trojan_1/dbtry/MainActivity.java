package com.trojan_1.dbtry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePicker,btnSubmit,btnViewEvent;
    EditText txtDate, txtTime, eventName,mobileNum,message ;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatabaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        eventName=(EditText)findViewById(R.id.event_name);
        mobileNum=(EditText)findViewById(R.id.mobile_number);
        message=(EditText)findViewById(R.id.message);
        btnSubmit=(Button)findViewById(R.id.Submit_event);
        btnViewEvent=(Button)findViewById(R.id.ViewEvents);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnViewEvent.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            mMonth=monthOfYear+1;;
                            mYear=year;
                            mDay=dayOfMonth;
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

        }
    }, mYear, mMonth, mDay);
            datePickerDialog.show();
}
        if (v == btnTimePicker) {


final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHour = hourOfDay;
                            mMinute=minute;
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(v==btnSubmit){
            //SmsManager sms=SmsManager.getDefault();
            //sms.sendTextMessage("7530000107",null,"it works",null,null);
            String date=mDay+"/"+mMonth+"/"+mYear;
            DateDiff d=new DateDiff();
            int daysleft=d.daysLeft(date);
          //Toast.makeText(this,"day left: "+dayleft,Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Event added for "+mDay+"-"+mMonth+"-"+mYear, Toast.LENGTH_SHORT).show();
            boolean isInserted = mydb.insertData(txtDate.getText().toString(),txtTime.getText().toString(),mobileNum.getText().toString(),eventName.getText().toString(),message.getText().toString(),daysleft);;
            if(isInserted=true){
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();

            txtDate.setText(null);
            txtTime.setText(null);
            eventName.setText(null);
            mobileNum.setText(null);
            message.setText(null);
            /*Intent i = new Intent(this,MainActivity.class);
            startActivity(i);*/
        }
        if(v==btnViewEvent){
            Cursor res = mydb.getAllData();
            if(res.getCount()==0){
                showMessage("Error","Nothing Found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("ID :"+res.getString(0)+"\n");
                buffer.append("Date :"+res.getString(1)+"\n");
                buffer.append("Time :"+res.getString(2)+"\n");
                buffer.append("Mobile Number :"+res.getString(3)+"\n");
                buffer.append("Event Name :"+res.getString(4)+"\n");
                buffer.append("Message :"+res.getString(5)+"\n");
                buffer.append("Days Left :"+res.getString(6)+"\n\n");

            }
            //show all data
            showMessage("Data",buffer.toString());
        }

    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}