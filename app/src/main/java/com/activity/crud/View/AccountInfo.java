package com.activity.crud.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.activity.crud.Model.Account;
import com.activity.crud.R;

public class AccountInfo extends AppCompatActivity {

    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_account_info );

        id = findViewById( R.id.accountID );

        String accountName = getIntent().getStringExtra( "model" );

        id.setText( accountName);
    }
}