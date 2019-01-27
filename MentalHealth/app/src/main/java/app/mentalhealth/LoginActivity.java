package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText email, pass;
    Button logIn;
    TextView creation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        if(email.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
            Toast.makeText(this, "worked", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "didn't work", Toast.LENGTH_SHORT).show();
    }
}
