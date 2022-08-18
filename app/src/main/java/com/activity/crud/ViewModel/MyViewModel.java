package com.activity.crud.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.activity.crud.Firestore.Repo;
import com.activity.crud.Model.Account;

import java.util.List;

public class MyViewModel extends ViewModel implements Repo.OnDataAdded {

    MutableLiveData<List<Account>> accountLiveData = new MutableLiveData<>();
    Repo firebaseRepo = new Repo(this);

    public MyViewModel(){
        firebaseRepo.getDataFromFirestore();
    }

    public LiveData<List<Account>> getLiveDataFromFirestore(){
        return accountLiveData;
    }

    @Override
    public void accountDataAdded(List<Account> accountList) {
        accountLiveData.postValue( accountList );
    }




}
