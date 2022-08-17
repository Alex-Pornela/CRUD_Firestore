package com.activity.crud.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.crud.Model.Account;
import com.activity.crud.R;
import com.activity.crud.View.AccountInfo;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.myViewHolder>  {

    private List<Account> accountList;
    OnItemClicked onItemClicked;
    Context context;
    private final ViewBinderHelper viewBinderHelper  = new ViewBinderHelper();

    public AccountAdapter(OnItemClicked itemClicked, Context context){
        this.onItemClicked = itemClicked;
        this.context = context;
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
        String no = String.valueOf( accountList.get( position ).getAmount() );
        holder.numOfList.setText(no );
        String accountName = accountList.get( position ).getName();

        holder.name.setText( accountName );
        holder.userID.setText( accountList.get( position ).getId() );

        viewBinderHelper.setOpenOnlyOne( true );
        viewBinderHelper.bind( holder.swipeRevealLayout, String.valueOf( accountList.get( position ).getName() ));

        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccountInfo.class );
                intent.putExtra( "model", accountName) ;
                context.startActivity( intent );
            }
        } );
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
        SwipeRevealLayout swipeRevealLayout;
        TextView tvEdit;
        TextView tvDelete;

        public myViewHolder(@NonNull View itemView) {
            super( itemView );

            numOfList = itemView.findViewById( R.id.numberOfList );
            name = itemView.findViewById( R.id.name );
            userID = itemView.findViewById( R.id.userId );
            swipeRevealLayout = itemView.findViewById( R.id.swipeLayout );
            tvEdit = itemView.findViewById( R.id.tvEdit );
            tvDelete = itemView.findViewById( R.id.tvDelete );


            
            tvEdit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText( context.getApplicationContext(), "Edit Clicked", Toast.LENGTH_SHORT ).show();
                }
            } );
            tvDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText( context, "Delete Clicked", Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }

    public interface OnItemClicked{
        void accountClicked(int position);
    }



}
