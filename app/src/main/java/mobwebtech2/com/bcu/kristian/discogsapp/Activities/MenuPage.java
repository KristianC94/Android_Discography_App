package mobwebtech2.com.bcu.kristian.discogsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class MenuPage extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;
    private Button searchBtn, savedBtn, logoutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        userEmail = findViewById(R.id.userEmailTxt);
        searchBtn = findViewById(R.id.searchBtn);
        savedBtn = findViewById(R.id.savedBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        searchBtn.setOnClickListener(this);
        savedBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        userEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        if (firebaseAuth.getCurrentUser() == null) {

            finish();
            Intent i = new Intent(MenuPage.this, FirstPage.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == logoutBtn) {

            firebaseAuth.signOut();
            finish();
            Intent i = new Intent(MenuPage.this, FirstPage.class);
            startActivity(i);
        }

        if (v == searchBtn) {

            Intent i = new Intent(MenuPage.this, TabActivitySearch.class);
            startActivity(i);
        }

        if (v == savedBtn) {

            Intent i = new Intent(MenuPage.this, TabActivitySaved.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {}
}
