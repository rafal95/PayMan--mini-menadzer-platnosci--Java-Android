package com.example.admin.payman.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.payman.R;
import com.example.admin.payman.dao.PaymentDao;
import com.example.admin.payman.model.Payment;
import com.example.admin.payman.table.PaymentTable;

/**
 * Created by Admin on 2018-12-05.
 */

public class OpenHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context;

    public OpenHelper(final Context context) {
        super(context, "paymentsdb", null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PaymentTable.onCreate(db);
        PaymentDao payemntDao = new PaymentDao(db);
        String[] payments = context.getResources().getStringArray(R.array.data_payments);
        for (String payment : payments) {
            payemntDao.save(new Payment(0, payment.split(" ")[0], payment.split(" ")[1], payment.split(" ")[2],0));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PaymentTable.onUpgrade(db, oldVersion, newVersion);
    }
}
