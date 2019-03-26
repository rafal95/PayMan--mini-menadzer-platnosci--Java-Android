package com.example.admin.payman.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Admin on 2018-12-05.
 */

public class PaymentTable {
    public static final String TABLE_NAME = "payment_table";

    public static class PaymentColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String COST = "cost";
        public static final String DATE = "date";
        public static final String STATUS = "st";
    }

    public static void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE " + PaymentTable.TABLE_NAME + " (");
        builder.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        builder.append(PaymentColumns.NAME + " TEXT UNIQUE NOT NULL, ");
        builder.append(PaymentColumns.COST + " TEXT, ");
        builder.append(PaymentColumns.DATE + " TEXT, ");
        builder.append(PaymentColumns.STATUS + " TEXT");
        builder.append(");");
        db.execSQL(builder.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PaymentTable.TABLE_NAME);
        PaymentTable.onCreate(db);
    }
}
