package com.activity.crud.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activity.crud.Adapter.AccountAdapter;
import com.activity.crud.Model.Account;
import com.activity.crud.R;
import com.activity.crud.ViewModel.MyViewModel;
import com.activity.crud.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AccountAdapter.OnItemClicked {

    private ActivityMainBinding binding;
    AccountAdapter accountAdapter;
    List<Account> accountDataList;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    MyViewModel myViewModel;
    private long pressedTime;
    Dialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );

        firestore = FirebaseFirestore.getInstance();

        //get data from Firestore
        loadDataOnRecyclerview( );

        //FloatingActionButton
        binding.addAccountBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        } );

        // dialog for adding new account
        progressDialog = new ProgressDialog(this);

    }

    public void loadDataOnRecyclerview() {
        binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        binding.recyclerView.setHasFixedSize( true );
        accountAdapter = new AccountAdapter( this,accountDataList );
        binding.recyclerView.setAdapter( accountAdapter );
        myViewModel = new ViewModelProvider( this ).get( MyViewModel.class );
        myViewModel.getLiveDataFromFirestore().observe( this, new Observer<List<Account>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Account> accountList) {
                accountDataList = accountList;
                accountAdapter.updateAdapter(accountList );

            }
        } );
    }


    private void addAccount() {

        dialog = new Dialog( this );
        dialog.setContentView( R.layout.add_layout_account );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT) );


        EditText userName = dialog.findViewById( R.id.userName );
        EditText date = dialog.findViewById( R.id.selectDate );
        EditText amount = dialog.findViewById( R.id.setTotalAmount );
        Button addBtn = dialog.findViewById( R.id.addAccount );
        ImageView closeBtn = dialog.findViewById( R.id.closeBtn );

        addBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userName.getText().toString();
                String dateCreated = date.getText().toString();
                int totalAmount = Integer.parseInt( amount.getText().toString() );
                String id = firestore.collection("Account Name").document().getId();


                HashMap<String, Object> hashMap = new HashMap<>();
                //hashMap.put( "No", amount );
                hashMap.put( "totalAmount", totalAmount);
                hashMap.put( "Name" , name );
                hashMap.put( "Date", dateCreated );
                hashMap.put("id", id);

                progressDialog.setMessage( "Adding Account" );
                progressDialog.show();

                firestore.collection( "Account Name" ).document(id).set( hashMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            loadDataOnRecyclerview();
                            dialog.dismiss();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Account successfully added.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } );


            }
        } );

        closeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        dialog.show();

       /* builder.setView( viewInflated );

        builder.setPositiveButton( "Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = userName.getText().toString();
                String dateCreated = date.getText().toString();
                int totalAmount = Integer.parseInt( amount.getText().toString() );
                String id = firestore.collection("Account Name").document().getId();



                HashMap<String, Object> hashMap = new HashMap<>();
                //hashMap.put( "No", amount );
                hashMap.put( "totalAmount", totalAmount);
                hashMap.put( "Name" , name );
                hashMap.put( "Date", dateCreated );
                hashMap.put("id", id);

                progressDialog.setMessage( "Adding Account" );
                progressDialog.show();

                firestore.collection( "Account Name" ).document(id).set( hashMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            loadDataOnRecyclerview();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Account successfully added.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } );

            }
        });
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        } );
       builder.show();*/


    }


    @Override
    public void accountClicked(int position) {
        Account account = accountDataList.get( position );
        Intent intent = new Intent(MainActivity.this, AccountInfo.class );
        intent.putExtra( "model", account) ;
        startActivity( intent );
    }

    @Override
    public void accountDelete( int position ) {
        Account accountModel = accountDataList.get( position );
        progressDialog.setMessage( "Deleting Account" );
        progressDialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection( "Account Name" ).document(accountModel.getId()).delete()
                .addOnCompleteListener( new OnCompleteListener<Void>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            progressDialog.dismiss();

                            Toast.makeText(MainActivity.this, "Account successfully deleted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } );
    }

    @Override
    public void accountEdit(int position) {
        Account accountModel = accountDataList.get( position );

        dialog = new Dialog( this );
        dialog.setContentView( R.layout.add_layout_account );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT) );


        EditText userName = dialog.findViewById( R.id.userName );
        EditText date = dialog.findViewById( R.id.selectDate );
        EditText amount = dialog.findViewById( R.id.setTotalAmount );
        Button addBtn = dialog.findViewById( R.id.addAccount );
        ImageView closeBtn = dialog.findViewById( R.id.closeBtn );


        userName.setText( accountModel.getName() );
        date.setText( accountModel.getDate() );
        String totalAmount = String.valueOf( accountModel.getTotalAmount() );
        amount.setText( totalAmount );

        addBtn.setText("UPDATE" );
        addBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userName.getText().toString();
                String dateCreated = date.getText().toString();
                int totalAmount = Integer.parseInt( amount.getText().toString() );

                firestore.collection( "Account Name" ).document(accountModel.getId()).update( "Name",name, "Date",dateCreated, "totalAmount",totalAmount )
                        .addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loadDataOnRecyclerview();
                                    progressDialog.dismiss();
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Account successfully updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Update Failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } );
            }
        } );

        closeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}