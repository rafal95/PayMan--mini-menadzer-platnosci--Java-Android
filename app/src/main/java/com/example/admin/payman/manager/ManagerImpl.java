package com.example.admin.payman.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.payman.dao.PaymentDao;
import com.example.admin.payman.model.Payment;
import com.example.admin.payman.table.PaymentTable;
import com.example.admin.payman.utils.OpenHelper;

import java.util.List;

/**
 * Created by Admin on 2018-12-05.
 */

public class ManagerImpl {
    private Context context;

    private SQLiteDatabase db;

    private PaymentDao paymentDao;

    public ManagerImpl(Context context) {

        this.context = context;

        SQLiteOpenHelper openHelper = new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();

        paymentDao = new PaymentDao(db);
    }

    public Payment getPayment(long id) {
        Payment payment = paymentDao.get(id);

        return payment;
    }


    public List<Payment> getAllPayment() {
        return paymentDao.getAll();
    }


    public Payment findPayment(String name) {
        Payment payment = paymentDao.find(name);

        return payment;
    }

    public long savePayment(Payment payment) {
        long paymentId;
        try {
            db.beginTransaction();
            paymentId = paymentDao.save(payment);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //Log.e(Constants.LOG_TAG, "Error saving movie (transaction rolled back)", e);
            paymentId = 0L;
        } finally {
            db.endTransaction();
        }

        return paymentId;
    }

    public void deletePayment(Payment entity) {
        try {
            db.beginTransaction();
            Payment payment = getPayment(entity.getId());
            if (payment != null) {
                paymentDao.delete(payment);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
          //  Log.e(Constants.LOG_TAG, "Error deleting movie (transaction rolled back)", e);
        } finally {
            db.endTransaction();
        }
    }

    public void updatePayment(Payment entity) {
        paymentDao.update(entity);
    }

    public Cursor getPaymentCursor() {
        return db.rawQuery("select " + PaymentTable.PaymentColumns._ID + ", " + PaymentTable.PaymentColumns.NAME
                + ", " + PaymentTable.PaymentColumns.COST + ", " + PaymentTable.PaymentColumns.DATE + ", " + PaymentTable.PaymentColumns.STATUS + " from " + PaymentTable.TABLE_NAME, null);
    }
}
