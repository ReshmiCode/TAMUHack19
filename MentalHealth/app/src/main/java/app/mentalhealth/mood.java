package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageButton;
import android.view.View;

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

public class mood extends Activity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        auth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);
        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("pastMoods").document(auth.getUid());
        final Map<String, Object> data = new HashMap<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        final Map<String, Object> fdata = document.getData();
                        data.put("0 days ago",fdata.get("0 days ago"));
                        data.put("1 days ago",fdata.get("1 days ago"));
                        data.put("2 days ago",fdata.get("2 days ago"));
                        data.put("3 days ago",fdata.get("3 days ago"));
                        data.put("4 days ago",fdata.get("4 days ago"));
                        data.put("5 days ago",fdata.get("5 days ago"));
                        data.put("6 days ago",fdata.get("6 days ago"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //HAPPY
        final ImageButton happyButton = findViewById(R.id.happiness);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "happiness");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });

        final ImageButton sadButton = findViewById(R.id.sadness);
        sadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "sadness");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });

        final ImageButton angryButton = findViewById(R.id.anger);
        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "anger");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });

        final ImageButton surpriseButton = findViewById(R.id.surprise);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "surprise");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });

        final ImageButton neutralButton = findViewById(R.id.neutral);
        neutralButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "neutral");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });

        final ImageButton fearButton = findViewById(R.id.fear);
        fearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> fdata = data;
                fdata.put("6 days ago", fdata.get("5 days ago"));
                fdata.put("5 days ago", fdata.get("4 days ago"));
                fdata.put("4 days ago", fdata.get("3 days ago"));
                fdata.put("3 days ago", fdata.get("2 days ago"));
                fdata.put("2 days ago", fdata.get("1 days ago"));
                fdata.put("1 days ago", fdata.get("0 days ago"));
                fdata.put("0 days ago", "fear");
                db.collection("pastMoods").document(auth.getUid())
                        .set(fdata)
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
                startActivity(new Intent(mood.this, moodhistory.class));
            }
        });
    }
}
