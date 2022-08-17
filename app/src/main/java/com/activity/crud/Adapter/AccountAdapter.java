package com.activity.crud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.crud.Model.Account;
import com.activity.crud.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.myViewHolder>  {

    private List<Account> accountList;
    OnItemClicked onItemClicked;

    public AccountAdapter(OnItemClicked itemClicked){
        this.onItemClicked = itemClicked;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<Account> accountList){
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_item,parent,false);
        return new myViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String no = String.valueOf( accountList.get( position ).getNo() );
        //holder.numOfList.setText( no) ;
        holder.name.setText( accountList.get( position ).getName() );
    }



    @Override
    public int getItemCount() {
        if(accountList!=null){
            return accountList.size();
        }else
            return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView numOfList;
        TextView userID;
        TextView name;
        public myViewHolder(@NonNull View itemView) {
            super( itemView );

            numOfList = itemView.findViewById( R.id.numberOfList );
            name = itemView.findViewById( R.id.name );
            userID = itemView.findViewById( R.id.userId );
        }
    }

    public interface OnItemClicked{
        void accountClicked(int position);
    }



}
