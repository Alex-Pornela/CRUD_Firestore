package com.activity.crud.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.activity.crud.Firestore.Repository;
import com.activity.crud.Model.Account;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AccountViewModel extends ViewModel/* implements Repository.AccountAdded*/ {

    public MutableLiveData<ArrayList<Account>> liveData;
    Repository repo = new Repository();

    public void init(){
        if(liveData != null){
            return;
        }
        liveData = Repository.getInstance(  ).getAccountData();
    }




   /* public AccountViewModel(){
       accountList = new MutableLiveData<>();
    }*/

    public LiveData<ArrayList<Account>> getAccountList() {
        return liveData;
    }

    /*@Override
    public void accountAdding(List<Account> accountModels) {
        accountList.setValue( accountModels );
    }*/

   /* public void getUserInfo(){
       options.setValue( repo.getAccountInfo() );
    }*/

}
