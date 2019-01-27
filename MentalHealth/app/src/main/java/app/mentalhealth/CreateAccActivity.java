package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class CreateAccActivity extends Activity {

    ImageView icon;
    EditText email, pass, name;
    Button create;
    Switch doctor;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        auth = FirebaseAuth.getInstance();
        icon = (ImageView)findViewById(R.id.profpic);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        create = (Button)findViewById(R.id.logIn);
        doctor = (Switch)findViewById(R.id.doctor);
    }

    public void create(View view) {
        String emailInput = email.getText().toString().trim();
        String passInput = pass.getText().toString().trim();

        if (TextUtils.isEmpty(emailInput)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passInput)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        else auth.createUserWithEmailAndPassword(emailInput, passInput).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Toast.makeText(CreateAccActivity.this, "Failed Registration: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
