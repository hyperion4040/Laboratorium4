package zpam.lab4.laboratorium4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import zpam.lab4.laboratorium4.model.Author;
import zpam.lab4.laboratorium4.model.Opinion;
import zpam.lab4.laboratorium4.model.Rate;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText note;
    private RatingBar ratingBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        note = findViewById(R.id.note);
        ratingBar = findViewById(R.id.ratingBar);


    }



    public void wyslij(View view){
        Rate rate = new Rate(ratingBar.getNumStars(),note.getText().toString());
        Author author = new Author(firstName.getText().toString(),lastName.getText().toString());
        Opinion opinion = new Opinion(author,rate);
        final String opinionJson = new Gson().toJson(opinion);


       String url = "http://10.253.8.176:8080/opinion/save";
       RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//tu należy samodzielnie zaimplementować wyświetlanie response
                        Toast.makeText(getApplicationContext(),
                                response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//tu należy samodzielnie zaimplementować wyświetlanie errora
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return opinionJson == null ? null :
                            opinionJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        queue.add(postRequest);
        queue.start();


    }

}