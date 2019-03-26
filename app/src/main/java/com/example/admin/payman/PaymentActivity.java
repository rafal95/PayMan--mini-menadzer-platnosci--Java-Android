package com.example.admin.payman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.payman.manager.ManagerImpl;
import com.example.admin.payman.model.Payment;

public class PaymentActivity extends AppCompatActivity {
    public static final String PAYMENT_ID = "stid";
    private ManagerImpl manager;
    private Payment payment;
    TextView name;
    TextView cost;
    TextView date;
    CheckBox status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        name = findViewById(R.id.value_name);
        cost = findViewById(R.id.value_cost);
        date = findViewById(R.id.value_date);
        status = findViewById(R.id.checkBox2);

        Intent intent = getIntent();
        manager = new ManagerImpl(this);

        long paymentID = intent.getLongExtra(PAYMENT_ID, 0L);
        payment = manager.getPayment(paymentID);

        name.setText(payment.getName());
        cost.setText(payment.getCost());
        date.setText(payment.getDate());
        if(payment.getStatus() == 0)
            status.setChecked(false);
        else
            status.setChecked(true);

    }
}
