package com.example.pokeconnect;

import android.content.res.Resources;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private int currentPoke = 1;
    private int totalPoke;
    private ImageView img;
    private GSONconvert data;
    private TextView pid;
    private TextView name;
    private TextView height;
    private TextView weight;
    private TextView base_xp;
    private TextView type1;
    private TextView type2;
    private TextView imgID;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pokedex:
                    mTextMessage.setText(R.string.title_pokedex);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        pid = findViewById(R.id.text_view_id1_2);
        name = findViewById(R.id.text_view_id2_2);
        height = findViewById(R.id.text_view_id3_2);
        weight = findViewById(R.id.text_view_id4_2);
        base_xp = findViewById(R.id.text_view_id5_2);
        type1 = findViewById(R.id.text_view_id6_2);
        type2 = findViewById(R.id.text_view_id7_2);
//        imgID = findViewById(R.id.text_view_id7_2);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        idRequest(1);
        countRequest();
    }

    private void countRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.139.1:8080/PokeApp/PokeAppServlet?count";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                totalPoke = Integer.parseInt(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextMessage.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    private void idRequest(int id){
        currentPoke = id;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.139.1:8080/PokeApp/PokeAppServlet?id=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+currentPoke,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        data = new GSONconvert();
                        data.setAll(response);
                        pid.setText(data.getId());
                        name.setText(data.getName());
                        height.setText(data.getHeight());
                        weight.setText(data.getWeight());
                        base_xp.setText(data.getBase_xp());
                        type1.setText(data.getType1());
                        type2.setText(data.getType2());
//                        imgID.setText((data.getImgID()));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextMessage.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    private void nameRequest(String name){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.139.1:8080/PokeApp/PokeAppServlet?name=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+name,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        data.setAll(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextMessage.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    public void next(View v) {
        int req;
        if(currentPoke < totalPoke){
            req = currentPoke+1;
        }
        else{
            req = 1;
        }
        idRequest(req);
        changeImg(String.valueOf(req));
    }

    public void prev(View v) {
        int req;
        if(currentPoke == 1){
            req = totalPoke;
        }
        else{
            req = currentPoke-1;
        }
        idRequest(req);
        changeImg(String.valueOf(req));
    }

    private void changeImg(String id){
        String imgRef = "p"+id;
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier(imgRef, "drawable", this.getPackageName());
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(resourceId);
    }
}
