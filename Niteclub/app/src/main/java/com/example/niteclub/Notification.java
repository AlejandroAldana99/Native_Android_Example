package com.example.niteclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    //Boton perfil
    public void get_profile(View view){
        Intent get_profile = new Intent(this, Profile.class);
        startActivity(get_profile);
    }

    //Boton home
    public void get_home(View view){
        Intent get_home = new Intent(this, MainActivity.class);
        startActivity(get_home);
    }

    //Boton mapas
    public void get_map(View view){
        Intent get_map = new Intent(this, Maps.class);
        startActivity(get_map);
    }
}
