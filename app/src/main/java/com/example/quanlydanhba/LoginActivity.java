package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/** Screen 1: login with a hard-coded account. */
public class LoginActivity extends AppCompatActivity {

    // Hard-coded account (no account storage needed)
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "123456";

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // The login screen looks cleaner without the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvError = findViewById(R.id.tv_error);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> attemptLogin());

        // "Done" on the keyboard also submits the form
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin();
                return true;
            }
            return false;
        });
    }

    private void attemptLogin() {
        tvError.setVisibility(View.GONE);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        // 1. Empty fields
        if (username.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.login_error_empty));
            return;
        }

        // 2. Wrong credentials: stay on this screen
        if (!username.equals(VALID_USERNAME) || !password.equals(VALID_PASSWORD)) {
            showError(getString(R.string.login_error_wrong));
            return;
        }

        // 3. Correct: explicit Intent to MainActivity, then finish() so Back
        //    does not return to the login screen
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}
