package com.app.meatdistributions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.textLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.textLogin:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }
}