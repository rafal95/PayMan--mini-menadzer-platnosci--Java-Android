package com.example.admin.payman;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.admin.payman.manager.ManagerImpl;
import com.example.admin.payman.model.Payment;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    public static final String PAYMENT_NAME = "sname";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Payment payment;
    private long id;
    private ManagerImpl manager;
    EditText nameEdit;
    EditText surnameEdit;
    EditText dateEdit;
    CheckBox status;
    private String shareName;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        manager = new ManagerImpl(this);

        nameEdit = findViewById(R.id.editText);
        surnameEdit = findViewById(R.id.editText2);
        dateEdit = findViewById(R.id.editText3);
        status = findViewById(R.id.checkBox);
        Intent intent = this.getIntent();
        String name = (intent).getStringExtra(PAYMENT_NAME);

       // Log.d("log", "Students name: " + name);
        payment = manager.findPayment(name);

        if (payment != null) {
            id = payment.getId();
            nameEdit.setText(payment.getName());
            surnameEdit.setText(payment.getCost());
            dateEdit.setText(payment.getDate());
            if(payment.getStatus() == 0)
                status.setChecked(false);
            else
                status.setChecked(true);
        }

       /* sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        shareName = sharedPref.getString("text", "");
        nameEdit.setText(shareName);*/

        dateEdit.setInputType(InputType.TYPE_NULL);
       // dateEdit.requestFocus();

        dateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showCalendarDialog(dateEdit);
                }
            }
        });
        dateEdit.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        showCalendarDialog(dateEdit);
                    }
                }
        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                dateEdit.setText(day+"-"+month+"-"+year);
            }
        };

    }

   /* @Override
    public void onPause() {
        super.onPause();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        nameEdit = (EditText)findViewById(R.id.editText);
        editor.putString("text", nameEdit.getText().toString());
        editor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        shareName = sharedPref.getString("text", "");
        nameEdit = (EditText)findViewById(R.id.editText);
        nameEdit.setText(shareName);

    }*/

    public void showCalendarDialog(EditText dateEdit){
        Calendar cal = Calendar.getInstance();
        String[] date = dateEdit.getText().toString().split("-");
        int year = Integer.parseInt(date[2]);//cal.get(Calendar.YEAR);
        int mont = Integer.parseInt(date[1])-1;//cal.get(Calendar.MONTH);
        int day = Integer.parseInt(date[0]);// cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(EditActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,year,mont,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void saveButton(View view) {
        int stat;
        if(status.isChecked())
            stat = 1;
        else
            stat = 0;
        manager.updatePayment(new Payment(id, nameEdit.getText().toString(), surnameEdit.getText().toString(),dateEdit.getText().toString(),stat));
    }
}
