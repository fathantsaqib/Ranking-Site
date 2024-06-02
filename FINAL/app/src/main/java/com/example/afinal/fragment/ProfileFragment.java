package com.example.afinal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.afinal.LoginActivity;
import com.example.afinal.R;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private TextView nimTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nimTextView = view.findViewById(R.id.tv_name2);
        emailTextView = view.findViewById(R.id.email);
        passwordTextView = view.findViewById(R.id.tv_username2);
        button = view.findViewById(R.id.btnLogout);

        sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String nim = sharedPreferences.getString("nim", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        nimTextView.setText(nim);
        emailTextView.setText(email);
        passwordTextView.setText(password);
        passwordTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }
    private void logout() {
        // Navigate to LoginActivity without clearing user data
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
