package com.example.niteclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class Detail extends AppCompatActivity {

    public String title;
    private TextView type_tv, music_tv, address_tv, phone_tv, rate_tv, schedule_tv, averageprice_tv, cover_tv, menu_tv;
    private ImageView pic_iv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title = getIntent().getStringExtra("Query");

        pic_iv = findViewById(R.id.IV_pic);
        type_tv = findViewById(R.id.TV_type);
        music_tv = findViewById(R.id.TV_music);
        address_tv = findViewById(R.id.TV_address);
        phone_tv = findViewById(R.id.TV_phone);
        rate_tv = findViewById(R.id.TV_rate);
        schedule_tv = findViewById(R.id.TV_schedule);
        averageprice_tv = findViewById(R.id.TV_ap);
        cover_tv = findViewById(R.id.TV_cover);
        menu_tv = findViewById(R.id.TV_menu);

        setupActionBar();
        get_image();
        get_data();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //Muestra contenido
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    private void get_image() {
        final StorageReference listRef = storage.getReference().child("customers/mexico/guanajuato/leon/Easter_Egg");
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            Log.d("Imagen: ", item.getPath() + "->"+ item.getName() + listRef.getPath());
                            Task<Uri> showpic = item.getDownloadUrl();

                            Picasso.with(Detail.this)
                                    .load(String.valueOf(showpic))
                                    .fit()
                                    .centerCrop()
                                    .into(pic_iv);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Detail.this, "Error al cargar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void get_data(){
        db.collection("customers").document("mexico")
                .collection("guanajuato").document("leon").collection("places")
                .whereEqualTo("Name", title)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("places", document.getId() + " => " + document.getData());

                                Maps_database rc = document.toObject(Maps_database.class);
                                //String name = rc.getName();
                                String type = rc.getType();
                                String music = rc.getMusic();

                                HashMap<String, String> address = rc.getAddress();
                                String phone = rc.getPhone();
                                double rate = rc.getRate();
                                HashMap<String, String> schedule = rc.getSchudule();
                                double averageprice = rc.getAveragePrice();
                                String cover = rc.getCover();
                                //HashMap<String, String> menu = rc.getMenu();

                                type_tv.setText("Categoria: "+type);
                                music_tv.setText("Musica: "+music);
                                address_tv.setText("Direccion: "+address.get("Street")+", \n"+address.get("Neighborhood"));
                                phone_tv.setText("Telefono: "+phone);
                                rate_tv.setText("Estrellas: "+rate);
                                schedule_tv.setText("Horario: \n" +
                                        "Lunes: "+schedule.get("Monday")+"\n" +
                                        "Martes: "+schedule.get("Tuesday")+"\n" +
                                        "Miercoles: "+schedule.get("Wednesday")+"\n" +
                                        "Jueves: "+schedule.get("Thursday")+"\n" +
                                        "Viernes: "+schedule.get("Friday")+"\n" +
                                        "Sabado: "+schedule.get("Saturday")+ "\n" +
                                        "Domingo: "+schedule.get("Sunday"));
                                averageprice_tv.setText("Ticket promedio: $"+averageprice);
                                cover_tv.setText("Cover: "+cover);
                                menu_tv.setText("Menu: ");
                            }
                        }
                        else {
                            Log.w("places", "Error getting documents.", task.getException());
                        }
                    }
                });
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
