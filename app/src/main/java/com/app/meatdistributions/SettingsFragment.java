package com.app.meatdistributions;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private SettingsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Setting> settings;
    private TextView textViewName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    private void getUSer(FirebaseUser user) {
        if (user == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            textViewName.setText(user.getDisplayName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Configuración");

        textViewName = view.findViewById(R.id.textViewName);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        settings = new ArrayList<>();
        settings.add(new Setting("Cambiar nombre"));
        settings.add(new Setting("Cambiar correo electronico"));
        settings.add(new Setting("Cambiar contraseña"));
        settings.add(new Setting("Eliminar cuenta"));

        mAdapter = new SettingsAdapter(settings);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,
                new SettingsAdapter.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        actionEventList(position);
                    }
                }));

        Button button = (Button) view.findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        getUSer(currentUser);

        return view;
    }

    private void actionEventList(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getContext(), ChangeNameActivity.class));
                break;
        }
    }

    class Setting {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        Setting(String name) {
            this.name = name;
        }
    }
}