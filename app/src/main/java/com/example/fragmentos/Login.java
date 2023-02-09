package com.example.fragmentos;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    Button singIn;
    TextView singUp,logIn;
    LinearLayout singUpLayout,logInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        singUp = findViewById(R.id.singUp);
        logIn = findViewById(R.id.logIn);
        singIn = findViewById(R.id.singIn);
        singUpLayout = findViewById(R.id.singUpLayout);
        logInLayout = findViewById(R.id.loInLayout);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp.setBackground(getResources().getDrawable(R.drawable.switch_trcks, null));
                singUp.setTextColor(getResources().getColor(R.color.negro, null));
                logIn.setBackground(null);
                singUpLayout.setVisibility(View.VISIBLE);
                logInLayout.setVisibility(View.GONE);
                logIn.setTextColor(getResources().getColor(R.color.amarillo, null));
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp.setBackground(null);
                singUp.setTextColor(getResources().getColor(R.color.amarillo, null));
                logIn.setBackground(getResources().getDrawable(R.drawable.switch_trcks, null));
                singUpLayout.setVisibility(View.GONE);
                logInLayout.setVisibility(View.VISIBLE);
                logIn.setTextColor(getResources().getColor(R.color.negro, null));
            }
        });
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.negro, this.getTheme()));
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.negro, this.getTheme()));
        }

    }
}