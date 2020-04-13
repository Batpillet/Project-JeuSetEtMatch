package com.example.jeusetetmatch;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.service.autofill.FieldClassification;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Dashboard extends Activity implements LocationListener {  //public class Dashboard extends FragmentActivity implements OnMapReadyCallback {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat, localisation;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    EditText duration, ace, fault, nomj1, nomj2, set1j1, set2j1, set3j1, set1j2, set2j2, set3j2;
    SQLiteDatabaseHandler db;
    ArrayList<Match> listMatch;
    ArrayAdapter adapter;
    Location location;
    double lati, longi;
    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    RadioButton gagnantj1, gagnantj2;
    // private GoogleMap mMap;
    ImageView imageView;
    Button camera, back, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        camera = findViewById(R.id.camera);
        back =  findViewById(R.id.Back);
        save = findViewById(R.id.Save);
        duration = findViewById(R.id.saisie_duree);
        ace = findViewById(R.id.saisie_ace);
        fault = findViewById(R.id.saisie_fautes);
        txtLat =  findViewById(R.id.location);
        nomj1 = findViewById(R.id.Joueur1);
        nomj2 = findViewById(R.id.Joueur2);
        gagnantj1 = findViewById(R.id.radioj1);
        gagnantj2 = findViewById(R.id.radioj2);
        set1j1 = findViewById(R.id.Set1j1);
        set2j1 = findViewById(R.id.Set2j1);
        set3j1 = findViewById(R.id.Set3j1);
        set1j2 = findViewById(R.id.Set1j2);
        set2j2 = findViewById(R.id.Set2j2);
        set3j2 = findViewById(R.id.Set3j2);
        imageView = findViewById(R.id.image_view);

        db = new SQLiteDatabaseHandler(this);

        gagnantj1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gagnantj2.setEnabled(false); //Comme il est impossible d'avoir 2 gagnants, on desactive le second bouton radio lors de la selection du premier
                } //Cette action necessite un listener sur le bouton radio
            }
        });

        gagnantj2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gagnantj1.setEnabled(false);
                }
            }
        });

        //Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToMain = new Intent(Dashboard.this, Menu.class);
                startActivity(intToMain);
            }
        });

        if (ContextCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dispatchTakePictureIntent();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(duration.getText().toString()) || TextUtils.isEmpty(ace.getText().toString()) || TextUtils.isEmpty(fault.getText().toString())
                        || TextUtils.isEmpty(set1j1.getText().toString()) || TextUtils.isEmpty(set1j2.getText().toString()) || TextUtils.isEmpty(set2j1.getText().toString())
                        || TextUtils.isEmpty(set2j2.getText().toString()) || TextUtils.isEmpty(set3j1.getText().toString()) || TextUtils.isEmpty(set3j2.getText().toString())
                        || TextUtils.isEmpty(nomj1.getText().toString()) || TextUtils.isEmpty(nomj2.getText().toString()) || TextUtils.isEmpty(gagnantj1.getText().toString())
                        || TextUtils.isEmpty(gagnantj2.getText().toString())){
                    Toast.makeText(Dashboard.this, "donnée(s) manquante(s)", Toast.LENGTH_SHORT).show(); //Blindage
                }
                else {
                    addM(v); //ajout données SQLite
                    Toast.makeText(Dashboard.this, "Match sauvegardé !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void addM(View view){
        int Duree = Integer.parseInt(duration.getText().toString());
        int Ace = Integer.parseInt(ace.getText().toString());
        int Faute = Integer.parseInt(fault.getText().toString());
        int s1j1 = Integer.parseInt(set1j1.getText().toString());
        int s2j1 = Integer.parseInt(set2j1.getText().toString());
        int s3j1 = Integer.parseInt(set3j1.getText().toString());
        int s1j2 = Integer.parseInt(set1j2.getText().toString());
        int s2j2 = Integer.parseInt(set2j2.getText().toString());
        int s3j2 = Integer.parseInt(set3j2.getText().toString());
        String Nomj1 = nomj1.getText().toString();
        String Nomj2 = nomj2.getText().toString();
        boolean Gagnantj1 = gagnantj1.isChecked();
        boolean Gagnantj2 = gagnantj2.isChecked();
        ArrayList<Integer> listj1 = new ArrayList<Integer>();
        ArrayList<Integer> listj2 = new ArrayList<Integer>();

        listj1.add(s1j1);
        listj1.add(s2j1);
        listj1.add(s3j1);
        listj2.add(s1j2);
        listj2.add(s2j2);
        listj2.add(s3j2);

        Joueur j1 = new Joueur(Nomj1, listj1, Gagnantj1);
        Joueur j2 = new Joueur(Nomj2, listj2, Gagnantj2);

        db.addMatch(new Match(Duree, Ace, Faute, lati, longi, j1, j2));
        Log.i("Dashboard", " value : " + db.getCount());
        db.close();
    }

    public String getAddress(Context ctx, double lat, double lng){
        String fullAdd = null;
        try{
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

                String location = address.getLocality();
                String zip = address.getPostalCode();
                String country = address.getCountryName();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return fullAdd;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //données de l'image + enregistrement dans la galerie
        if(requestCode == 100){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    captureImage, "image name", "description of image");
            //message when picture saved to camera roll
            Toast.makeText(Dashboard.this,"Photo enregistrée dans la galerie",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = findViewById(R.id.location);
        String address = getAddress(this, location.getLatitude(), location.getLongitude());
        txtLat.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude() + "\nAdresse : " + address);

        lati = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


