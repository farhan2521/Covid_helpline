package com.example.covidhelpline.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelpline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CallLogAdapterRecyclerView extends RecyclerView.Adapter<CallLogAdapterRecyclerView.CallLogViewHolder> {
    FirebaseFirestore db;
    ArrayList<WorkingPhoneNo> workingPhoneNos;
    Context mContext;
    String TAG = CallLogAdapterRecyclerView.class.getSimpleName();
    public CallLogAdapterRecyclerView(ArrayList<WorkingPhoneNo> workingPhoneNos, Context context, FirebaseFirestore db) {
        this.workingPhoneNos = workingPhoneNos;
        mContext= context;
        this.db = db;
    }

    @NonNull
    @NotNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_call_log,parent,false);
        return new CallLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CallLogViewHolder holder, int position) {
        holder.phoneTextView.setText(workingPhoneNos.get(position).getPhoneNo());
        holder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phno = workingPhoneNos.get(position).getPhoneNo();
                String category = holder.categorySpinner.getSelectedItem().toString();
                // String category = workingPhoneNos.get(position).getCategory();
                Toast.makeText(mContext, category + "---"+phno , Toast.LENGTH_SHORT).show();
                Log.i(TAG,"ph-cate"+phno+"---"+category);
                addData(phno,category);
                holder.submitButton.setText("Submitted");
                holder.submitButton.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workingPhoneNos.size();
    }

    public class CallLogViewHolder extends RecyclerView.ViewHolder {
        TextView phoneTextView;
        Spinner categorySpinner;
        Button submitButton;

        public CallLogViewHolder(View itemView) {
            super(itemView);
            phoneTextView = itemView.findViewById(R.id.textViewPhoneno);
            categorySpinner = itemView.findViewById(R.id.spinnerCategories);
            submitButton = itemView.findViewById(R.id.submit_button);
        }
    }

    private void addData(String phone, String category) {
        Map<String, Object> user = new HashMap<>();
        user.put("phone", phone);
        user.put("category", category);
        user.put("timestamp", Timestamp.now());

// Add a new document with a generated ID
        db.collection("phonenumbers")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        /*Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });*/
    }

    /*private void readData(){
        db.collection("phonenumbers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }*/
}
