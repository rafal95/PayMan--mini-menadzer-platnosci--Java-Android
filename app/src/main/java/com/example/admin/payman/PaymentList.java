package com.example.admin.payman;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.admin.payman.manager.ManagerImpl;

import java.util.ArrayList;


public class PaymentList extends Fragment {
   // private ArrayAdapter<String> adapter;
    private Cursor cursor;
    private PaymentCursorAdapter adapter;
    private ManagerImpl manager;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        manager = new ManagerImpl(getActivity());
        View view = inflater.inflate(R.layout.fragment_payment_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list);;


        cursor = manager.getPaymentCursor();
        if (cursor != null) {
            getActivity().startManagingCursor(cursor);
            adapter = new PaymentCursorAdapter(getActivity(), cursor);
            listView.setAdapter(adapter);
        }

        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //  Log.d(Constants.LOG_TAG, "Student item clicked!");
                        cursor.moveToPosition(position);
                        long paymentId = cursor.getInt(cursor.getColumnIndex("_id"));
                        Intent intent = new Intent(getActivity(), PaymentActivity.class);
                        intent.putExtra(PaymentActivity.PAYMENT_ID, paymentId);
                        startActivity(intent);
                    }
                }
        );

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        cursor.moveToPosition(position);
                        long paymentId = cursor.getInt(cursor.getColumnIndex("_id"));
                        manager.deletePayment(manager.getPayment(paymentId));
                       // Log.d(Constants.LOG_TAG, "AFTER DELETING");
                        Toast.makeText(getActivity(), "Usunięto", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                        return true;
                    }
                }
        );

        return view;


    }

}
