package com.dark.amarel.myrt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText EtEmail, EtPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();

        EtEmail =(EditText) findViewById(R.id.EtEmail);
        EtPassword=(EditText) findViewById(R.id.EtPassword);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);


        findViewById(R.id.TvSignUp).setOnClickListener(this);
        findViewById(R.id.BtnLogin).setOnClickListener(this);
    }

    private void userlogin(){
        String email = EtEmail.getText().toString().trim();
        String password= EtPassword.getText().toString().trim();

        if (email.isEmpty()){
            EtEmail.setError("Email harus Diisi");
            EtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EtEmail.setError("Masukan Email Yang Valid");
            EtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            EtPassword.setError("Password harus diisi");
            EtPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            EtPassword.setError("Password minimal 6 karakter");
            EtPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this, MainDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TvSignUp:

                startActivity(new Intent(this, SignUpAcitvity.class));
                break;

            case R.id.BtnLogin:
                userlogin();
                break;
        }
    }
}
