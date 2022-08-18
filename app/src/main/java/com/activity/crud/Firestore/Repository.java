package com.activity.crud.Firestore;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.activity.crud.Model.Account;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userId;
    private ArrayList<Account> accountList = new ArrayList<>();
    private static Repository instance;
    private MutableLiveData<ArrayList<Account>> data = new MutableLiveData<>();

    //AccountAdded accountAdded;

/*
    public Repository(AccountAdded accountAdded) {
        this.accountAdded = accountAdded;
    }*/

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<Account>> getAccountData(){
        load();
        loadAccount();
        data.setValue( accountList );
        return data;
    }

    private void  load(){
        CollectionReference reference = firestore.collection("Account Name");
        reference.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAG", "listen:error", error);
                    return;
                }
                assert value != null;
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    accountList.add( documentChange.getDocument().toObject( Account.class ) );
                }
                data.setValue( accountList );
            }
        } );
    }

    private void loadAccount() {
        firestore.collection( "Account Name" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot documentSnapshot: list){
                        accountList.add( documentSnapshot.toObject( Account.class ) );
                        Log.e( "myTAG", String.valueOf( documentSnapshot.getData() ) );
                    }
                    Log.e( "TAG", "onSuccess: added" );

                    data.setValue( accountList );
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG",e.getMessage());
            }
        } );
    }


   /* @Override
    public FirestoreRecyclerOptions<Account> getAccountInfo() {
        CollectionReference docRef = firestore.collection( "Account Name" );

        Query query = docRef.orderBy( "No", Query.Direction.ASCENDING );
                *//*.addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e( "TAG",error.getMessage() );
                            return;
                        }
                        if(value != null){
                            for(DocumentChange dc : value.getDocumentChanges()){
                                accountList.add( dc.getDocument().toObject( Account.class ) );
                            }
                        }

                    }*//*
                    FirestoreRecyclerOptions<Account> options = new FirestoreRecyclerOptions.Builder<Account>()
                            .setQuery(query, Account.class)
                            .build();

                    return options;
               // } );


        *//*FirestoreRecyclerOptions<Account> options = new FirestoreRecyclerOptions.Builder<Account>()
                .setQuery(firestore, Account.class)
                .build();*//*

        *//*CollectionReference docRef = firestore.collection( "Account Name" );
        docRef.get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Account accountModel = queryDocumentSnapshots.toObject( Account.class );
                accountList.add( accountModel );
                Log.d("TAG", "info" + accountModel.getName());
            }
        } );*//*
        *//*docRef.get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Account accountModel = documentSnapshot.toObject( Account.class );
                accountList.add( accountModel );
                Log.d("TAG", "info" + accountModel.getName());
                //accountAdded.accountAdding( accountList );
            }
        } );*//*
       *//* firestore.collection( "Account Name" ).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                accountList.clear();
                if(value != null){
                    for(DocumentSnapshot ds : value.getDocuments()){
                        Account accountModel = ds.toObject( Account.class );
                        accountList.add( accountModel );
                        accountAdded.accountAdding( accountList );
                    }
                }

            }
        } );*//*

    }
*/




    public List<Account> accountAdding() {
        return accountList;
    }


   /* public interface AccountAdded{
        void accountAdding(List<Account> accountModels);
    }*/



}
