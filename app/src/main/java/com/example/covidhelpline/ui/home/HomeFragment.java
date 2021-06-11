package com.example.covidhelpline.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelpline.R;
import com.example.covidhelpline.ui.WorkingPhoneNo;
import com.example.covidhelpline.ui.getDirectoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    FirebaseFirestore db;
    private HomeViewModel homeViewModel;
    ArrayList<WorkingPhoneNo> list = new ArrayList<WorkingPhoneNo>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        RecyclerView directoryRV = root.findViewById(R.id.directoryRecyclerView);
        readDataFirebase();
        root.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        getDirectoryAdapter workingNosAdapter = new getDirectoryAdapter(requireActivity(),list);
        directoryRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
        directoryRV.setAdapter(workingNosAdapter);
        Log.i(TAG,list.toString()+"is data");
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        readDataFirebase();
    }*/

    private void readDataFirebase() {
        //ArrayList<WorkingPhoneNo> list = new ArrayList<WorkingPhoneNo>();

        db.collection("phonenumbers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String phoneno = document.getString("phone");
                                String category = document.getString("category");
                                WorkingPhoneNo workingPhoneNo = new WorkingPhoneNo(phoneno,true,category);
                                list.add(workingPhoneNo);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.i(TAG, list+"is data");
        //return list;
    }
}