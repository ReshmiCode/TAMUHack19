package app.mentalhealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class moodhistory extends Activity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodhistory);

        auth = FirebaseAuth.getInstance();

        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(moodhistory.this, mood.class));            }
        });

        FirebaseApp.initializeApp(this);
        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("pastMoods").document(auth.getUid());
        final Map<String, Object> data = new HashMap<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("success", "DocumentSnapshot data");
                if (task.isSuccessful()) {
                    Log.d("success", "DocumentSnapshot data");
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("success", "DocumentSnapshot data: " + document.getData());
                        final Map<String, Object> fdata = document.getData();
                        data.put("0 days ago",fdata.get("0 days ago"));
                        data.put("1 days ago",fdata.get("1 days ago"));
                        data.put("2 days ago",fdata.get("2 days ago"));
                        data.put("3 days ago",fdata.get("3 days ago"));
                        data.put("4 days ago",fdata.get("4 days ago"));
                        data.put("5 days ago",fdata.get("5 days ago"));
                        data.put("6 days ago",fdata.get("6 days ago"));
                        //Log.d("probprob", fdata.get("1 days ago")+"");

                        ImageView[] pastfaces = new ImageView[7];
                        pastfaces[0] = (ImageView) findViewById(R.id.imageView);
                        pastfaces[1] = (ImageView) findViewById(R.id.imageView2);
                        pastfaces[2] = (ImageView) findViewById(R.id.imageView3);
                        pastfaces[3] = (ImageView) findViewById(R.id.imageView4);
                        pastfaces[4] = (ImageView) findViewById(R.id.imageView5);
                        pastfaces[5] = (ImageView) findViewById(R.id.imageView6);
                        pastfaces[6] = (ImageView) findViewById(R.id.imageView7);

                        ImageView image;
                        for (int day = 1; day <= 6; day++){
                            String key = day + " days ago";
                            Object emotion = data.get(key);
                            Log.d("PROB", emotion+"");
                            image = pastfaces[day];
                            if (emotion == null || (emotion.toString()).equals("N/A"))
                                image.setImageResource(R.drawable.gray);
                            else if ((emotion.toString()).equals("surprise"))
                                image.setImageResource(R.drawable.smallsurprised);
                            else if ((emotion.toString()).equals("happiness"))
                                image.setImageResource(R.drawable.smallhappy);
                            else if ((emotion.toString()).equals("sadness"))
                                image.setImageResource(R.drawable.smallsad);
                            else if ((emotion.toString()).equals("anger"))
                                image.setImageResource(R.drawable.smallangry);
                            else if ((emotion.toString()).equals("neutral"))
                                image.setImageResource(R.drawable.smallneutral);
                            else if ((emotion.toString()).equals("fear"))
                                image.setImageResource(R.drawable.smallfear);
                        }
                        String key = "0 days ago";
                        Object emotion = data.get(key);
                        image = pastfaces[0];
                        if ((emotion.toString()).equals("surprise"))
                            image.setImageResource(R.drawable.surprised);
                        else if ((emotion.toString()).equals("happiness"))
                            image.setImageResource(R.drawable.happy);
                        else if ((emotion.toString()).equals("sadness"))
                            image.setImageResource(R.drawable.sad);
                        else if ((emotion.toString()).equals("anger"))
                            image.setImageResource(R.drawable.angry);
                        else if ((emotion.toString()).equals("neutral"))
                            image.setImageResource(R.drawable.neutral);
                        else if ((emotion.toString()).equals("fear"))
                            image.setImageResource(R.drawable.fear);
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