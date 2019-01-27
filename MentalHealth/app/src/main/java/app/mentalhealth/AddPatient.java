package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddPatient extends Activity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        auth = FirebaseAuth.getInstance();

        final Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AddPatient.this, DoctorView.class));
            }
        });

        FirebaseApp.initializeApp(this);
        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Button submit = findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText textfield = (EditText)findViewById(R.id.patientid);
                final String id = textfield.getText().toString();

                DocumentReference docRef = db.collection("users").document(id); //first look for patient
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                //FIND DOCTOR
                                final Map<String, Object> doctordata = new HashMap<>();
                                DocumentReference docRef = db.collection("users").document(auth.getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                final Map<String, Object> fdata = document.getData();
                                                doctordata.put("Name", fdata.get("Name"));
                                                doctordata.put("isDoc", true);
                                                for (int i = 1; i <= fdata.size()-2; i++)
                                                    doctordata.put("patientID"+i, fdata.get("patientID"+i));
                                                doctordata.put("patientID"+(fdata.size()-1), id);

                                                db.collection("users").document(auth.getUid())
                                                        .set(doctordata)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error writing document", e);
                                                            }
                                                        });
                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });

                            } else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(AddPatient.this, "No such patient found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

                startActivity(new Intent(AddPatient.this, DoctorView.class));
            }
        });
    }
}
