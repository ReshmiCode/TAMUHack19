package app.mentalhealth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;

public class mood extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        final ImageButton happyButton = findViewById(R.id.happiness);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });

        final ImageButton sadButton = findViewById(R.id.sadness);
        sadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });

        final ImageButton angryButton = findViewById(R.id.anger);
        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });

        final ImageButton tiredButton = findViewById(R.id.surprise);
        tiredButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });

        final ImageButton neutralButton = findViewById(R.id.neutral);
        neutralButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });

        final ImageButton depressedButton = findViewById(R.id.fear);
        depressedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

            }
        });
    }
}
