package com.app.meatdistributions;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeNameActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCurrentName;
    private EditText editTextNewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cambiar nombre");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("fragmentNumber", "4");
                startActivity(i);
            }
        });

        findViewById(R.id.buttomChangeName).setOnClickListener(this);

        editTextCurrentName = (EditText) findViewById(R.id.editTextCurrentName);
        editTextNewName = (EditText) findViewById(R.id.editTextNewName);
        getUser();
    }

    private void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        editTextCurrentName.setText(user.getDisplayName());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.buttomChangeName:
                changeName();
                break;
        }
    }

    private void changeName() {
        String name = editTextNewName.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "El campo nuevo nombre es requerido", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ChangeNameActivity.this, "Se ha cambiado el nombre correctamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("fragmentNumber", "4");
                    startActivity(i);
                }
            }
        });
    }
}