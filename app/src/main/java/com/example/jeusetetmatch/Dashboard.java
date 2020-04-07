package com.example.jeusetetmatch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dashboard extends Activity implements LocationListener {  //public class Dashboard extends FragmentActivity implements OnMapReadyCallback {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button camera = findViewById(R.id.camera);
        Button back =  findViewById(R.id.Back);
        Button save = findViewById(R.id.Save);
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

        db = new SQLiteDatabaseHandler(this);

        //Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToMain = new Intent(Dashboard.this, Menu.class);
                startActivity(intToMain);
            }
        });

        //Camera button
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addM(v);
                Toast.makeText(Dashboard.this, "Match sauvegardé !", Toast.LENGTH_SHORT).show();
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
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(s1j1);
        list.add(s2j1);
        list.add(s3j1);
        list.add(s1j2);
        list.add(s2j2);
        list.add(s3j2);

        Joueur j1 = new Joueur(Nomj1, list, Gagnantj1);
        Joueur j2 = new Joueur(Nomj2, list, Gagnantj2);

        db.addMatch(new Match(Duree, Ace, Faute, lati, longi, j1, j2));
        Log.i("Dashboard", "OK");
        Log.i("Dashboard", " value : " + db.getCount());
        db.close();
    }

    public void handleRadio1(Match m, RadioButton button){
        if(button.isChecked()){
            m.getJoueur1().setGagnant(true);
        }
        else{
            m.getJoueur1().setGagnant(false);
        }
    }

    public void handleRadio2(Match m, RadioButton button){
        if(button.isChecked()){
            m.getJoueur2().setGagnant(true);
        }
        else{
            m.getJoueur2().setGagnant(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPic();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = findViewById(R.id.location);
        txtLat.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        lati = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
    }