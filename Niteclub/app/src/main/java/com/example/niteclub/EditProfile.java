package com.example.niteclub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        setupActionBar();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //Muestra contenido
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Edit Profile");
        }
    }

    //Boton home
    public void get_home(View view){
        Intent get_home = new Intent(this, MainActivity.class);
        startActivity(get_home);
    }

    //Boton notificaciones
    public void get_notification(View view){
        Intent get_notification = new Intent(this, Notification.class);
        startActivity(get_notification);
    }

    //Boton mapas
    public void get_map(View view){
        Intent get_map = new Intent(this, Maps.class);
        startActivity(get_map);
    }

    //Boton perfil
    public void get_profile(View view){
        Intent get_profile = new Intent(this, Profile.class);
        startActivity(get_profile);
    }

}
