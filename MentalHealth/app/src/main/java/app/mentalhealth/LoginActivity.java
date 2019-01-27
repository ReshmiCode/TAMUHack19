package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {
    EditText email, pass;
    Button logIn;
    TextView creation;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) { // already logged in
            startActivity(new Intent(LoginActivity.this, FingerActivity.class));
        }

        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        creation = (TextView)findViewById(R.id.create);
        logIn = (Button)findViewById(R.id.logIn);

        creation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), CreateAccActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logIn(View view) {
        String emailInput = email.getText().toString().trim();
        String passInput = pass.getText().toString().trim();

        auth.signInWithEmailAndPassword(emailInput, passInput).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) Toast.makeText(LoginActivity.this, "didn't work", Toast.LENGTH_SHORT).show();
                else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    DocumentReference data = FirebaseFirestore.getInstance().collection("users").document(auth.getUid());
                    data.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    final Map<String, Object> fdata = document.getData();
                                    boolean isDoc = (boolean) fdata.get("isDoc");
                                    if (isDoc) startActivity(new Intent(LoginActivity.this, DoctorActivity.class));
                                    else startActivity(new Intent(LoginActivity.this, mood.class));
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }
}
