package com.example.admin.payman;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 2018-12-05.
 */

class PaymentCursorAdapter extends CursorAdapter{
    private final LayoutInflater vi;

    public PaymentCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
        this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItem = vi.inflate(R.layout.payment_item, parent, false); //inny item
        TextView text = listItem.findViewById(android.R.id.text1);
        Button editButton = listItem.findViewById(R.id.edit_button);
        final ViewHolder holder = new ViewHolder(text,editButton); //editButton

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.text.getText().toString().split(" ")[0];
                //Log.d("log", "Students name: " + name);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c);
                System.out.println("Current time => " + formattedDate);

                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra(EditActivity.PAYMENT_NAME, name);
                v.getContext().startActivity(intent);
            }
        });

        listItem.setTag(holder);
        populateView(listItem, cursor);
        return listItem;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        populateView(view, cursor);
    }

    private void populateView(final View listItem, final Cursor cursor) {
        ViewHolder holder = (ViewHolder) listItem.getTag();
        if ((cursor != null) && !cursor.isClosed()) {
            String name = cursor.getString(1);
            holder.text.setText(name);
        }
    }

    private static class ViewHolder {
        protected final TextView text;
        protected final Button editButton;

        public ViewHolder(TextView text,Button editButton ) { //, Button editButton
            this.text = text;
            this.editButton = editButton;
        }
    }
}
