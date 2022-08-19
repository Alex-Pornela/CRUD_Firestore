package com.activity.crud.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.crud.Model.Account;
import com.activity.crud.R;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.myViewHolder>  {

    private List<Account> accountList;
    OnItemClicked onItemClicked;
    private final ViewBinderHelper viewBinderHelper  = new ViewBinderHelper();
    CardView cardView;


    public AccountAdapter(OnItemClicked itemClicked, List<Account> accountList){
        this.onItemClicked = itemClicked;
        this.accountList = accountList;
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
        return new myViewHolder( view ).linkAdapter(this);
}

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;


        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));

        String no = String.valueOf( accountList.get( position ).getTotalAmount() );
        holder.numOfList.setText(no );
        String accountName = accountList.get( position ).getName();

        holder.name.setText( accountName );
        holder.userID.setText( accountList.get( position ).getId() );

        viewBinderHelper.setOpenOnlyOne( true );
        viewBinderHelper.bind( holder.swipeRevealLayout, String.valueOf( accountList.get( position ).getName() ));

        holder.charBg.setBackground(draw);
        String firstCharacter = accountName.substring( 0,1 );
        holder.firstChar.setText( firstCharacter );

        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.accountClicked( position );
            }
        } );
        holder.userID.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.accountClicked( position );
            }
        } );
        holder.tvDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(onItemClicked != null){
                        onItemClicked.accountDelete( (position));
                        accountList.remove( position );
                        notifyItemRemoved( position );
                        notifyItemRangeChanged(position, accountList.size());
                    }
            }
        } );

        holder.tvEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClicked != null){
                    onItemClicked.accountEdit( position );
                }
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
        AccountAdapter accountAdapter;
        TextView firstChar;
        ImageView charBg;

        public myViewHolder(@NonNull View itemView) {
            super( itemView );

            numOfList = itemView.findViewById( R.id.numberOfList );
            name = itemView.findViewById( R.id.name );
            userID = itemView.findViewById( R.id.userId );
            swipeRevealLayout = itemView.findViewById( R.id.swipeLayout );
            tvEdit = itemView.findViewById( R.id.tvEdit );
            tvDelete = itemView.findViewById( R.id.tvDelete );
            firstChar = itemView.findViewById( R.id.firstChar );
            charBg = itemView.findViewById( R.id.charBg );

        }



        public myViewHolder linkAdapter(AccountAdapter accountAdapter) {
            this.accountAdapter = accountAdapter;
            return this;
        }
    }

    public interface OnItemClicked{
        void accountClicked(int position);
        void accountDelete(int position);
        void accountEdit( int position);
    }



}
