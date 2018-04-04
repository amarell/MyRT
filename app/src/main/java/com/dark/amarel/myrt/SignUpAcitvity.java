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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpAcitvity extends AppCompatActivity implements View.OnClickListener {
    EditText EtEmail, EtPassword;

    private FirebaseAuth mAuth;

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitvity);

        EtEmail=(EditText) findViewById(R.id.EtEmail);
        EtPassword=(EditText) findViewById(R.id.EtPassword);
        progressbar=(ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.TvSignIn).setOnClickListener(this);
        findViewById(R.id.BtnSignUp).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    private void registeruser(){
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

       progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User Berhasil Registrasi", Toast.LENGTH_SHORT).show();
                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "User telah terdaftar", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnSignUp:
                registeruser();
                break;

            case R.id.TvSignIn:
                startActivity(new Intent(this, MainActivity.class));
        }
    }
}
