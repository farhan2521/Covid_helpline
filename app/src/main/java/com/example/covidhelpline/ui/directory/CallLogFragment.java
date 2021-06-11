package com.example.covidhelpline.ui.directory;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelpline.R;
import com.example.covidhelpline.ui.WorkingPhoneNo;
import com.example.covidhelpline.ui.CallLogAdapterRecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CallLogFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<WorkingPhoneNo> workingPhoneNos;
    FirebaseFirestore db;
    private CallLogViewModel callLogViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        callLogViewModel =
                new ViewModelProvider(this).get(CallLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_call_log, container, false);
        //final TextView textView = root.findViewById(R.id.text_directory);
        getCallLog();
        db = FirebaseFirestore.getInstance();
        RecyclerView callLogRecyclerView = root.findViewById(R.id.callLogRecyclerView);
        CallLogAdapterRecyclerView adapter = new CallLogAdapterRecyclerView(workingPhoneNos,requireActivity(),db);
        callLogRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        callLogRecyclerView.setAdapter(adapter);
        /*callLogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    private void getCallLog() {
        workingPhoneNos = new ArrayList<>();
        Cursor managedCursor = requireActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, null);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int noOfRows = managedCursor.getCount();
        managedCursor.moveToLast();

        for (int i = managedCursor.getCount(); i > 0; i--) {
            managedCursor.moveToPosition(i - 1);
            String phno = managedCursor.getString(number);
            workingPhoneNos.add(new WorkingPhoneNo(phno, false, "oxygen"));
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WorkingPhoneNo workingPhoneNo = (WorkingPhoneNo) parent.getItemAtPosition(position);
        Toast.makeText(getActivity(),workingPhoneNo.getPhoneNo()+"\n"+workingPhoneNo.getCategory(),Toast.LENGTH_LONG).show();
    }
}