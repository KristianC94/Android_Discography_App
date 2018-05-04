package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class FirstPage extends AppCompatActivity implements View.OnClickListener {

    private EditText logEmail, logPass;
    private Button loginBtn;
    private TextView registerTxt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        logEmail = findViewById(R.id.loginEmail);
        logPass = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxt = findViewById(R.id.registerTxt);
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(this);
        registerTxt.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() != null) {

            finish();
            Intent i = new Intent(FirstPage.this, MenuPage.class);
            startActivity(i);
        }
    }

    private void userLogin(){

        String email = logEmail.getText().toString().trim();
        String password = logPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            finish();
                            Intent i = new Intent(FirstPage.this, MenuPage.class);
                            startActivity(i);
                            Toast.makeText(FirstPage.this, "Login successful",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(FirstPage.this,
                                    "Login unsuccessful. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {

        if (v == loginBtn) {

            userLogin();
        }

        if (v == registerTxt) {

            finish();
            Intent i = new Intent(FirstPage.this, RegisterPage.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {}
}
