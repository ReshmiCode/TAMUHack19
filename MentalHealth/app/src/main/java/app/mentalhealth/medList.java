package app.mentalhealth;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Button;
import android.widget.EditText;
import android.text.InputType;
import android.content.DialogInterface;

import com.google.firebase.auth.FirebaseAuth;

public class medList extends Activity implements MyRecyclerViewAdapter.ItemClickListener  {

    String name;
    String dose;
    String time;

    public medList(){
        this.name = "name";
        this.dose = "dose";
        this.time = "time";
    }

    public medList(String name, String dose, String time){
        this.name = name;
        this.dose = dose;
        this.time = time;
    }
    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_list);

        // data to populate the RecyclerView with
        final ArrayList<String> medList = new ArrayList<>();
        medList.add("Drug 1");
        medList.add("Drug 2");
        medList.add("Drug 3");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.medList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, medList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        final Button logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(medList.this, LoginActivity.class));
            }
        });


        final Button addMedsButton = findViewById(R.id.add_meds_button);

        addMedsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(medList.this);
                builder.setTitle("Enter New Medication");
                    // Set up the input
                final EditText input = new EditText(medList.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newDrug =  input.getText().toString();
                        medList.add(newDrug);
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        final Button removeMedsButton = findViewById(R.id.rv_meds_button);

        removeMedsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(medList.size()> 0) {
                    medList.remove(medList.size()-1);
                    adapter.notifyItemRemoved(medList.size());
                } else{

                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void mood(View view) {
        startActivity(new Intent(medList.this, mood.class));
    }

    public void pic(View view) {
        startActivity(new Intent(medList.this, EmotionActivity.class));
    }
}

