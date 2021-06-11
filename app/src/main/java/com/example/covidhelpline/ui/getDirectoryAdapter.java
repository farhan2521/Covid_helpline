package com.example.covidhelpline.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelpline.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class getDirectoryAdapter extends RecyclerView.Adapter<getDirectoryAdapter.directoryViewHolder> {

    Context mContext;
    ArrayList<WorkingPhoneNo> mWorkingPhoneNos;

    public getDirectoryAdapter(Context context, ArrayList<WorkingPhoneNo> arrayList) {
        mContext = context;
        this.mWorkingPhoneNos = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public getDirectoryAdapter.directoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_directory_items,parent,false);
        return new directoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull getDirectoryAdapter.directoryViewHolder holder, int position) {
        holder.phoneNumberTextView.setText(mWorkingPhoneNos.get(position).getPhoneNo());
        holder.categoryTextView.setText(mWorkingPhoneNos.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return mWorkingPhoneNos.size();
    }

    public class directoryViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberTextView, categoryTextView;
        public directoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            phoneNumberTextView = itemView.findViewById(R.id.textViewPhoneno);
            categoryTextView = itemView.findViewById(R.id.textViewCategories);
        }
    }
}
