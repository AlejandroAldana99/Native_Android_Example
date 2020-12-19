package com.example.niteclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

    //Boton cupones
    public void get_coupons(View view){
        Intent get_coupons = new Intent(this, Coupons.class);
        startActivity(get_coupons);
    }

    //Boton mensajes
    public void get_messages(View view){
        Intent get_messages = new Intent(this, Messages.class);
        startActivity(get_messages);
    }

    //Boton reservaciones
    public void get_reservations(View view){
        Intent get_reservations = new Intent(this, Reservations.class);
        startActivity(get_reservations);
    }

    //Boton favoritos
    public void get_favorites(View view){
        Intent get_favorites = new Intent(this, Favorites.class);
        startActivity(get_favorites);
    }

    //Boton Editar perfil
    public void get_editprofile(View view){
        Intent get_editprofile = new Intent(this, EditProfile.class);
        startActivity(get_editprofile);
    }

    //Boton notificaciones
    public void get_notifications(View view){
        Intent get_notifications = new Intent(this, Notifications.class);
        startActivity(get_notifications);
    }
}
