package com.example.loginregister.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginregister.MainActivity;
import com.example.loginregister.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_Activity extends AppCompatActivity {
    EditText  inputemail, inputpassword, inputconfirmpassword;
    String  email, password, confirmpassword;
    Button btnregister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        inputemail = findViewById(R.id.edt_email_register);
        inputpassword = findViewById(R.id.edt_password_register);
        inputconfirmpassword = findViewById(R.id.edt_confirmpassword_register);
        btnregister = findViewById(R.id.button_register);
        TextView txv_signin = findViewById(R.id.txv_signin);

        txv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigasi ke halaman register
                Intent intent = new Intent(register_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekRegister();
            }
        });
    }

    private void cekRegister() {
        email = inputemail.getText().toString();
        password = inputpassword.getText().toString();
        confirmpassword = inputconfirmpassword.getText().toString();

        if (!password.equals(confirmpassword)) {
            Toast.makeText(register_Activity.this, "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register_Activity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                            //navigasi
                            Intent intent = new Intent(register_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Memastikan pengguna tidak bisa kembali ke activity register
                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(register_Activity.this, "Register Gagal, email atau password Anda salah"+ errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
