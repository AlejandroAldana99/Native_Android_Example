package com.example.niteclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener{

    public HashMap<String, String> point = new HashMap<>();

    AutoCompleteTextView searchText;

    //Crea plantilla main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.sText_main);
        searchText.setOnKeyListener(this);
    }

    //Boton perfil
    public void get_profile(View view){
        Intent get_profile = new Intent(this, Profile.class);
        startActivity(get_profile);
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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int KeyCode, KeyEvent event) {
        if ((event.getAction()==KeyEvent.ACTION_DOWN)&&(KeyCode == KeyEvent.KEYCODE_ENTER)){
             String _place = searchText.getText().toString();
             closeKeyboard();

             if (_place.equals("") || _place.length() == 0){
                 return false;
             }
             else {
                 if (point.containsKey(_place)){

                     Toast.makeText(this, "Great election: " + _place,
                             Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(this, "Place '" + _place + "' no found",
                             Toast.LENGTH_SHORT).show();
                 }
             }
        }
        return false;
    }

}
