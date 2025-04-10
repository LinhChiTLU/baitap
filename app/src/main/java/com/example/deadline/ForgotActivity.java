package com.example.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deadline.Login2Activity;

public class ForgotActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnNext, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        // Ánh xạ view
        etEmail = findViewById(R.id.et_email);
        btnNext = findViewById(R.id.btn_next);
        btnLogin = findViewById(R.id.btn_login);

        // Xử lý nút "Next Step"
        btnNext.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email cannot be empty");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Invalid email format");
                return;
            }

            // Nếu hợp lệ, chuyển sang màn hình tiếp theo (ví dụ: ResetPasswordActivity)
            Intent intent = new Intent(ForgotActivity.this, ResetPassActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        // Xử lý nút "Log In"
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotActivity.this, Login2Activity.class);
            startActivity(intent);
            finish();
        });
    }
}
