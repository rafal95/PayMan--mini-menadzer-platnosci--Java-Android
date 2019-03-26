package com.example.admin.payman.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.admin.payman.model.Payment;
import com.example.admin.payman.table.PaymentTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2018-12-05.
 */

public class PaymentDao {
    private static final String INSERT =
            "insert into " + PaymentTable.TABLE_NAME + "(" + PaymentTable.PaymentColumns.NAME + ", " + PaymentTable.PaymentColumns.COST + ", " + PaymentTable.PaymentColumns.DATE + ", " + PaymentTable.PaymentColumns.STATUS +") values (?, ?, ?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public PaymentDao(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(PaymentDao.INSERT);
    }

    public long save(Payment entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getName());
        insertStatement.bindString(2, entity.getCost());
        insertStatement.bindString(3, entity.getDate());
        insertStatement.bindLong(4, entity.getStatus());
        return insertStatement.executeInsert();
    }

    public void update(Payment entity) {
        final ContentValues values = new ContentValues();
        values.put(PaymentTable.PaymentColumns.NAME, entity.getName());
        values.put(PaymentTable.PaymentColumns.COST, entity.getCost());
        values.put(PaymentTable.PaymentColumns.DATE, entity.getDate());
        values.put(PaymentTable.PaymentColumns.STATUS, entity.getStatus());
        int result = db.update(PaymentTable.TABLE_NAME, values, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity
                .getId()) });
      //  Log.d(Constants.LOG_TAG, "UPDATIN WITH NAME: " + entity.getName() + " AND SURNAME: " + entity.getCost());
      //  Log.d(Constants.LOG_TAG, "RESULT: " + result + " ROWS UPDATED!");
    }


    public void delete(Payment entity) {
        if (entity.getId() > 0) {
            db.delete(PaymentTable.TABLE_NAME, BaseColumns._ID + " = ?", new String[] { String.valueOf(entity.getId()) });
        }
    }


    public Payment get(long id) {
        Payment payment = null;
        Cursor c =
                db.query(PaymentTable.TABLE_NAME, new String[] { BaseColumns._ID, PaymentTable.PaymentColumns.NAME,
                                PaymentTable.PaymentColumns.COST,PaymentTable.PaymentColumns.DATE,PaymentTable.PaymentColumns.STATUS},
                        BaseColumns._ID + " = ?", new String[] { String.valueOf(id) }, null, null, null, "1");
        if (c.moveToFirst()) {
            payment = this.buildPaymentFromCursor(c);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return payment;
    }


    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<Payment>();
        Cursor c =
                db.query(PaymentTable.TABLE_NAME, new String[] { BaseColumns._ID, PaymentTable.PaymentColumns.NAME,
                                PaymentTable.PaymentColumns.COST,PaymentTable.PaymentColumns.DATE,PaymentTable.PaymentColumns.STATUS},
                        null, null, null, PaymentTable.PaymentColumns.NAME, null);
        if (c.moveToFirst()) {
            do{
                Payment payment = this.buildPaymentFromCursor(c);
                if (payment != null) {
                    payments.add(payment);
                }
            } while (c.moveToNext());
        }

        if (!c.isClosed()) {
            c.close();
        }

        return payments;
    }

    private Payment buildPaymentFromCursor(Cursor c) {
        Payment payment = null;
        if (c != null) {
            payment = new Payment();
            payment.setId(c.getLong(0));
            payment.setName(c.getString(1));
            payment.setCost(c.getString(2));
            payment.setDate(c.getString(3));
            payment.setStatus(c.getLong(4));
        }
        return payment;
    }

    public Payment find(String name) {
        long paymentId = 0L;
        //Log.d("log", "Students name: " + name);
        String sql = "select _id from " + PaymentTable.TABLE_NAME + " where upper(" + PaymentTable.PaymentColumns.NAME + ") = ? limit 1";
        Cursor c = db.rawQuery(sql, new String[] { name.toUpperCase() });
        if (c.moveToFirst()) {
            paymentId = c.getLong(0);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return this.get(paymentId);
    }
}
