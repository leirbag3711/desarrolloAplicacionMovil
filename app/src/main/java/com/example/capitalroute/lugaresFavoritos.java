package com.example.capitalroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.capitalroute.adapter.PlaceAutoSuggestAdapter;

public class lugaresFavoritos extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_favoritos);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_favoritos);
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(lugaresFavoritos.this,android.R.layout.simple_list_item_1));
    }
}
