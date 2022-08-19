package com.activity.crud.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activity.crud.Adapter.ItemAdapter;
import com.activity.crud.Model.Account;
import com.activity.crud.R;
import com.activity.crud.databinding.ActivityAccountInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountInfo extends AppCompatActivity {

    private ActivityAccountInfoBinding binding;
    List<Account> accountDataList;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    ItemAdapter itemAdapter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivityAccountInfoBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot());

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Account account= (Account) bundle.getSerializable("model");

        binding.tvName.setText( account.getName() );

        binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        binding.recyclerView.setHasFixedSize( true );
        itemAdapter = new ItemAdapter( accountDataList ,this);
        binding.recyclerView.setAdapter( itemAdapter );

        //get data from Firestore
        loadItemDataOnRecyclerview(account.getId() );

        binding.backBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountInfo.this,MainActivity.class);
                startActivity( intent );
                finish();
            }
        } );

        //FloatingActionButton
        binding.addItemBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(account);
            }
        } );

        // dialog for adding new account
        progressDialog = new ProgressDialog(this);

    }



    private void addItem(Account account) {

        dialog = new Dialog( this );
        dialog.setContentView( R.layout.add_layout_item );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT) );

        EditText itemName = dialog.findViewById( R.id.itemName );
        EditText itemDate= dialog.findViewById( R.id.itemDate );
        EditText itemAmount = dialog.findViewById( R.id.setTotalAmount);
        Button addItem = dialog.findViewById( R.id.addItem );
        ImageView closeBtn = dialog.findViewById( R.id.itemCloseBtn );

        addItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemsName = itemName.getText().toString();
                String itemDateCreated = itemDate.getText().toString();
                int itemPrice = Integer.parseInt( itemAmount.getText().toString() );
                String id = firestore.collection("Account Name").document(account.getId()).collection( "Account Item" ).document().getId();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put( "itemName",itemsName );
                hashMap.put( "itemDate", itemDateCreated );
                hashMap.put( "Amount", itemPrice );
                hashMap.put( "itemId", id );

                progressDialog.setMessage( "Adding Item" );
                progressDialog.show();

                firestore.collection( "Account Name" ).document(account.getId())
                        .collection( "Account Item" ).document(id).set( hashMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            loadItemDataOnRecyclerview(account.getId());
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText( AccountInfo.this, "Item successfully added.", Toast.LENGTH_SHORT).show();
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

    private void loadItemDataOnRecyclerview(String id) {

        CollectionReference reference = firestore.collection("Account Name").document(id).collection("Account Item");
        reference.orderBy( "itemName", Query.Direction.ASCENDING ).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w( "TAG", "listen:error", error );
                    return;
                }
                List<Account> list = new ArrayList<>();
                assert value != null;
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        list.add( dc.getDocument().toObject( Account.class ) );

                        Account account = dc.getDocument().toObject( Account.class );
                        double amount =+ account.getAmount();
                        getTotalAmount(id);

                    }
                    itemAdapter.updateItemAdapter( list);
                }
            }
        });

}

    private void getTotalAmount( String id) {

        CollectionReference reference = firestore.collection( "Account Name" ).document( id ).collection( "Account Item" );
        reference.get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int total = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Double itemCost = document.getDouble( "Amount" );
                        total += itemCost;
                    }
                    firestore.collection( "Account Name" ).document( id ).update( "totalAmount", total )
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            } );
                    Log.d( "TAG", String.valueOf( total ) );
                }
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AccountInfo.this,MainActivity.class);
        startActivity( intent );
        finish();
    }
}