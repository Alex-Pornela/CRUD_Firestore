package com.activity.crud.Firestore;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.activity.crud.Model.Account;
import com.activity.crud.View.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repo {

    OnDataAdded onDataAdded;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    public Repo(OnDataAdded onDataAdded) {
        this.onDataAdded = onDataAdded;
    }

    public void  getDataFromFirestore(){
        CollectionReference reference = firestore.collection("Account Name");
        reference.orderBy( "Name", Query.Direction.DESCENDING).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAG", "listen:error", error);
                    return;
                }
                List<Account> list = new ArrayList<>();
                assert value != null;
                for(QueryDocumentSnapshot queryDocumentSnapshot : value){
                    list.add( queryDocumentSnapshot.toObject( Account.class ) );
                    onDataAdded.accountDataAdded( list );
                }
            }

        } );

    }



    public  interface OnDataAdded{
        void accountDataAdded(List<Account> accountList);

    }
}
