package com.example.niteclub;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Maps extends AppCompatActivity implements OnInfoWindowClickListener, OnMapReadyCallback, View.OnKeyListener {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    public boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng mDefaultLocation = new LatLng(37.4244162, -122.089391);
    private static final int DEFAULT_ZOOM = 14;
    private static final int ULTRA_ZOOM = 17;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final String TAG = Maps.class.getSimpleName();

    private ArrayList<Marker> locationFirst = new ArrayList<>();
    private ArrayList<Marker> locationFinal = new ArrayList<>();
    public List<PlaceItem> placeList = new ArrayList<>();
    public HashMap<String, LatLng> point = new HashMap<>();

    private GoogleMap mMap;
    public CameraPosition mCameraPosition;
    public Location mCurrentLocation;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AutoCompleteTextView searchText;

    @RequiresApi(api = Build.VERSION_CODES.M)
    //customers/mexico/guanajuato/leon/places/Easter_Egg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_maps);
        searchText = findViewById(R.id.sText);
        searchText.setOnKeyListener(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //Llamado del mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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

    //Boton home
    public void get_home(View view){
        Intent get_home = new Intent(this, MainActivity.class);
        startActivity(get_home);
    }

    public void onMapReady(GoogleMap map) {
        mMap = map;
        updateLocationUI();
        getDeviceLocation();
        get_points();
        mMap.setOnInfoWindowClickListener(this);
    }

    private void get_points() {
        db.collection("customers").document("mexico")
                .collection("guanajuato").document("leon").collection("places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (Marker marker:locationFinal){
                                marker.remove();
                            }
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("places", document.getId() + " => " + document.getData());
                                Maps_database rc = document.toObject(Maps_database.class);
                                String name = rc.getName();
                                String music = rc.getMusic();
                                double averageprice = rc.getAveragePrice();
                                double latitud = rc.getLatitud();
                                double longitud = rc.getLongitud();

                                point.put(name, new LatLng(latitud, longitud));
                                placeList.add(new PlaceItem(name));

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(new LatLng(latitud,longitud)).title(name).snippet("Type of Music :"+music+" | $"+averageprice)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                locationFirst.add(mMap.addMarker(markerOptions));
                            }
                            locationFinal.clear();
                            locationFinal.addAll(locationFirst);

                            Log.d("Lugar", placeList + " => ");
                            AutoCompletePlace adapter = new AutoCompletePlace(Maps.this, placeList);
                            searchText.setAdapter(adapter);
                        }
                        else {
                            Log.w("places", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (!mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(false);
        }
        catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            assert mLastKnownLocation != null;
//                            if (counter == 0) {
//                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(mLastKnownLocation.getLatitude(),
//                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                            else{
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        // If request is cancelled, the result arrays are empty.
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle();
        Intent get_detail = new Intent(this, Detail.class);
        get_detail.putExtra("Query", title);
        startActivity(get_detail);
        Toast.makeText(this, "Great election: " + title,
                Toast.LENGTH_SHORT).show();
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
                    LatLng g_point = point.get(_place);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(g_point, ULTRA_ZOOM));

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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
