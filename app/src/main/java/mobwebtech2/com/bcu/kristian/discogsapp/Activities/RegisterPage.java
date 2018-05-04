package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private EditText regEmail, regPassword, regPassword2;
    private Button registerBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        regEmail = findViewById(R.id.registerEmail);
        regPassword = findViewById(R.id.registerPassword);
        regPassword2 = findViewById(R.id.registerPasswordCfm);
        registerBtn = findViewById(R.id.registerBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(this);
    }

    private void registerUser(){

        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        String password2 = regPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.equals(password, password2)) {

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Toast.makeText(RegisterPage.this, "Registering...",
                                    Toast.LENGTH_SHORT).show();

                            if (task.isSuccessful()){

                                finish();
                                Intent i = new Intent(RegisterPage.this, MenuPage.class);
                                startActivity(i);
                                Toast.makeText(RegisterPage.this, "User registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(RegisterPage.this,
                                        "Registration failed. Please try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {

            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        if (v == registerBtn) {

            registerUser();
        }
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
