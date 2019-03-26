package com.example.admin.payman;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.payman.manager.ManagerImpl;
import com.example.admin.payman.model.Payment;

import java.util.ArrayList;
import java.util.Calendar;


public class NewPayment extends Fragment implements View.OnClickListener {
    private Payment payment;
    private long id;
    private ManagerImpl manager;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText name;
    EditText cost;
    EditText date;
    private SharedPreferences.Editor[] tabeditor;
    private String shareName;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String shareDate;
    private String shareCost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        manager = new ManagerImpl(getActivity());
        View view = inflater.inflate(R.layout.fragment_new_payment, container, false);
        name = (EditText) view.findViewById(R.id.editText);
        cost = (EditText) view.findViewById(R.id.editText2);
        date = (EditText) view.findViewById(R.id.editText3);
        Button add = (Button) view.findViewById(R.id.add_payment);


        date.setInputType(InputType.TYPE_NULL);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showCalendarDialog(date);
                }
            }
        });

        date.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        showCalendarDialog(date);
                    }
                }
        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                date.setText(day+"-"+month+"-"+year);
            }
        };

        sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        shareName = sharedPref.getString("text", "");
        shareCost = sharedPref.getString("text2", "");
        shareDate = sharedPref.getString("text3", "");
        name.setText(shareName);
        cost.setText(shareCost);
        date.setText(shareDate);

        add.setOnClickListener(this);
       // name.setText(shareName);
        return view;

    }



    @Override
    public void onPause() {
        super.onPause();

        sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("text", name.getText().toString());
        editor.putString("text2", cost.getText().toString());
        editor.putString("text3", date.getText().toString());
        editor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        shareName = sharedPref.getString("text", "");
        shareCost = sharedPref.getString("text2", "");
        shareDate = sharedPref.getString("text3", "");
        name.setText(shareName);
        cost.setText(shareCost);
        date.setText(shareDate);

    }

    public void showCalendarDialog(EditText dateEdit){
        Calendar cal = Calendar.getInstance();
        //String[] date = dateEdit.getText().toString().split("-");
        int year = cal.get(Calendar.YEAR);
        int mont = cal.get(Calendar.MONTH);
        int day =  cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,year,mont,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        manager.savePayment(new Payment(0, name.getText().toString(), cost.getText().toString(),date.getText().toString(),0));
        Toast.makeText(getActivity(), "Dodano płatność", Toast.LENGTH_SHORT).show();
    }
}