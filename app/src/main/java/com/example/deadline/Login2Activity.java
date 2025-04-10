package com.example.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login2Activity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnSignup;
    TextView tvForgotPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần giao diện
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                // Chuyển sang HomeActivity và xoá stack đăng nhập
                                Intent intent = new Intent(Login2Activity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Xử lý đăng ký
        btnSignup.setOnClickListener(view -> {
            Intent intent = new Intent(Login2Activity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Quên mật khẩu
        tvForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(Login2Activity.this, ForgotActivity.class);
            startActivity(intent);
        });
    }
}
