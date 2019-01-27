package app.mentalhealth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class EmotionActivity extends Activity {


    private ImageView imageView; // variable to hold the image view in our activity_main.xml
    private TextView resultText; // variable to hold the text view in our activity_main.xml
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int REQUEST_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        // initiate our image view and text view
        imageView = (ImageView) findViewById(R.id.imageView);
        resultText = (TextView) findViewById(R.id.resultText);
    }


    // when the "GET EMOTION" Button is clicked this function is called
    public void getEmotion(View view) throws Exception
    {
        Log.i("Info","Button Pressed");

        String emotion = detectEmotion();

        Log.i("Info",emotion);

        resultText.setText("Happiness");


    }

    public String detectEmotion() {
        String subscriptionKey = "2a7fb36f43a84c509a9906a5301a2c8b";

        String uriBase =
                "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";

        String imageWithFaces =
                "{\"url\":\"https://scontent.fftw1-1.fna.fbcdn.net/v/t1.0-9/35393814_457062144743757_527689032130363392_n.jpg?_nc_cat=111&_nc_ht=scontent.fftw1-1.fna&oh=fbd79601b77f078dbb586e79e6cbb427&oe=5CF74C02\"}";

        String faceAttributes = "emotion";

        HttpClient httpclient = HttpClientBuilder.create().build();

        try {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters.);
            builder.setParameter("returnFaceAttributes", faceAttributes);

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            StringEntity reqEntity = new StringEntity(imageWithFaces);
            request.setEntity(reqEntity);
            Log.i("Info",request.toString());

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                //System.out.println("REST Response:\n");

                String jsonString = EntityUtils.toString(entity).trim();
                if (jsonString.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    double scores[] = new double[8];
                    String emotion_list[] = {"contempt", "surprise", "happiness", "neutral", "sadness", "disgust", "anger", "fear"};

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        JSONObject faceAttribute = jsonObject.getJSONObject("faceAttribute");
                        JSONObject emotion = faceAttribute.getJSONObject("emotion");
                        for (int j = 0; j < 8; j++)
                            scores[j] = emotion.getDouble(emotion_list[j]);
                    }

                    double max = 0.0;
                    int index = 0;
                    for (int i = 0; i < 8; i++) {
                        if (scores[i] > max) {
                            max = scores[i];
                            index = i;
                        }
                    }
                    return emotion_list[index];
                }
            }
        }

        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
            return "Error";
        }

        return null;
    }


    // when the "GET IMAGE" Button is clicked this function is called
    public void getImage(View view) {
        // check if user has given us permission to access the gallery
        if (checkPermission()) {
            Intent choosePhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(choosePhotoIntent, RESULT_LOAD_IMAGE);
        } else {
            requestPermission();
        }
    }


    // This function gets the selected picture from the gallery and shows it on the image view
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // get the photo URI from the gallery, find the file path from URI and send the file path to ConfirmPhoto
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {


            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            // a string variable which will store the path to the image in the gallery
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
        }
    }

    // if permission is not given we get permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(EmotionActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}