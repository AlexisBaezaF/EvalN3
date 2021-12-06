package com.example.evaln3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.Attributes;

public class createFireEmergencyEntry extends AppCompatActivity {

    private final String[] permission = {Manifest.permission.CAMERA,
                                         Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                         Manifest.permission.READ_EXTERNAL_STORAGE};
    TextView name, desc, latitude, longitude;
    ImageView photo;
    private Bitmap bitmap;
    private final int TAKE_PIC = 500;
    private final int RECOVER_PIC = 600;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fire_emergency_entry);

        name = findViewById(R.id.logName);
        desc = findViewById(R.id.logDesc);
        latitude = findViewById(R.id.logLAT);
        longitude = findViewById(R.id.logLON);
        photo = findViewById(R.id.logPhoto);
        bitmap = null;
        mAuth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permission, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "¡Permiso de cámara requerido!", Toast.LENGTH_SHORT).show();
            }
            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "¡Permiso de escritura de memoria requerido!", Toast.LENGTH_SHORT).show();
            }
            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "¡Permiso de lectura de memoria requerido!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void tomarFoto(View view){
        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intento, TAKE_PIC);
    }

    public void guardarFoto(View view){
        OutputStream stream_out = null;
        File PhotoFile = null;
        String FileName;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver r = getContentResolver();
            ContentValues v = new ContentValues();
            FileName = System.currentTimeMillis()+"_Photo_0";

            v.put(MediaStore.Images.Media.DISPLAY_NAME, FileName);
            v.put(MediaStore.Images.Media.MIME_TYPE, "Image/png");
            v.put(MediaStore.Images.Media.RELATIVE_PATH, "StoragePhotos/ImageSiniestros");
            v.put(MediaStore.Images.Media.IS_PENDING, 1);

            Uri collections = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri uriPhotos = r.insert(collections, v);

            try{
                stream_out = r.openOutputStream(uriPhotos);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            v.clear();

            r.update(uriPhotos, v, null, null);
            v.put(MediaStore.Images.Media.IS_PENDING, 0);
        }else{
            String route = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            FileName = System.currentTimeMillis()+"_Photo_0.png";
            PhotoFile = new File(route, FileName);

            try{
                stream_out = new FileOutputStream(PhotoFile);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        if(bitmap != null){
            boolean savedPic = bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream_out);
            if(savedPic){
                Toast.makeText(this, "¡Foto guardada exitosamente", Toast.LENGTH_SHORT).show();
            }
            if(stream_out != null){
                try{
                    stream_out.flush();
                    stream_out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(PhotoFile != null){
                MediaScannerConnection.scanFile(this, new String[]{PhotoFile.toString()}, null, null);
            }
        }else{
            Toast.makeText(this, "Se requiere tomar una fotografía.", Toast.LENGTH_SHORT).show();
        }
    }

    public void galeria(View view){
        Intent intento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intento, RECOVER_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PIC:
                if(resultCode == RESULT_OK){
                    Bitmap b = (Bitmap) data.getExtras().get("data");
                    photo.setImageBitmap(b);
                }
                break;
            case RECOVER_PIC:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    photo.setImageURI(ruta);
                }
                break;
        }
    }

    public void crearEntrada(View view){
        String Name = name.getText().toString();
        String Desc = desc.getText().toString();
        String LAT = latitude.getText().toString();
        String LON = longitude.getText().toString();

        Intent intento = new Intent(getApplicationContext(), Siniestro.class);
        intento.putExtra("Name", Name);
        intento.putExtra("Desc", Desc);
        intento.putExtra("LAT", LAT);
        intento.putExtra("LON", LON);
        startActivity(intento);
    }
}