package com.activity.crud.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.activity.crud.Adapter.AccountAdapter;
import com.activity.crud.Firestore.AccountRepository;
import com.activity.crud.Model.Account;
import com.activity.crud.R;
import com.activity.crud.ViewModel.AccountViewModel;
import com.activity.crud.ViewModel.MyViewModel;
import com.activity.crud.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AccountAdapter.OnItemClicked {

    private ActivityMainBinding binding;
    LinearLayoutManager linearLayoutManager;
    AccountViewModel accountViewModel;
    AccountAdapter accountAdapter;
    List<Account> accountDataList = new ArrayList<>();
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    private FirestoreRecyclerOptions<Account> options;
    MyViewModel myViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

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

    private void loadDataOnRecyclerview() {
        binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        binding.recyclerView.setHasFixedSize( true );
        accountAdapter = new AccountAdapter( this );
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Account");

        View viewInflated = getLayoutInflater().inflate( R.layout.add_user_layout, null );
        EditText userName = viewInflated.findViewById( R.id.userName );
        EditText date = viewInflated.findViewById( R.id.selectDate );

        builder.setView( viewInflated );

        builder.setPositiveButton( "Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = userName.getText().toString();
                String dateCreated = date.getText().toString();
                String id = firestore.collection("Account Name").document().getId();
                int amount = 0;


                HashMap<String, Object> hashMap = new HashMap<>();
                //hashMap.put( "No", amount );
                hashMap.put( "Amount", amount );
                hashMap.put( "Name" , name );
                hashMap.put( "Date", dateCreated );
                hashMap.put("id", id);

                progressDialog.setMessage( "Adding Account" );
                progressDialog.show();

                firestore.collection( "Account Name" ).document().set( hashMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
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
        builder.show();
    }

    @Override
    public void accountClicked(int position) {

    }




}