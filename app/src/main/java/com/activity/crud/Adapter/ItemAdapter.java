package com.activity.crud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.crud.Model.Account;
import com.activity.crud.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.itemViewHolder> {

    private List<Account> accountList;
    Context context;

    public ItemAdapter(List<Account> accountList, Context context){
        this.accountList = accountList;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItemAdapter(List<Account> accountList){
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.account_item_list,parent,false);
        return new itemViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        holder.itemName.setText( accountList.get( position ).getItemName() );
        holder.id.setText( accountList.get( position ).getItemId());
        holder.date.setText( accountList.get( position ).getItemDate() );
        holder.amount.setText( String.valueOf(accountList.get( position ).getAmount()) );

    }

    @Override
    public int getItemCount() {
        if(accountList!=null){
            return accountList.size();
        }else
            return 0;
    }


    public class itemViewHolder extends RecyclerView.ViewHolder{

        private ItemAdapter itemAdapter;
        TextView itemName;
        TextView date;
        TextView id;
        TextView amount;
        TextView accountName;

        public itemViewHolder(@NonNull View itemView) {
            super( itemView );

            itemName = itemView.findViewById( R.id.itemName );
            date = itemView.findViewById( R.id.itemDate );
            id = itemView.findViewById( R.id.itemId );
            amount = itemView.findViewById( R.id.itemAmount );
        }

        public ItemAdapter.itemViewHolder linkAdapter(ItemAdapter itemAdapter) {
            this.itemAdapter = itemAdapter;
            return this;
        }
    }
}
