package com.example.pr22_captura_app_externa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int RC_PHOTO_PICKER = 1;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    ImageView vistaImatge;
    Button botoObrirGaleria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vistaImatge = findViewById(R.id.imageView);
        botoObrirGaleria = findViewById(R.id.buttonGallery);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            //There are no request codes
                            Intent data = result.getData();
                            Uri URI = data.getData();
                            vistaImatge = findViewById(R.id.imageView);
                            vistaImatge.setImageURI(URI);
                        }
                    }
                });

        botoObrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(null);
            }
        });
    }

    public void openGallery(View view){
        //Create intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch Activity to get the result
        someActivityResultLauncher.launch(intent);
    }


    @Override
    protected void onActivityResult(int codiRequerit, int codiResultant, Intent dades) {
        super.onActivityResult(codiRequerit, codiResultant, dades);
        if (codiRequerit == RC_PHOTO_PICKER && codiResultant == RESULT_OK) {
            Bundle extras = dades.getExtras();
            Bitmap imatgeBitmap = (Bitmap) extras.get("data");
            vistaImatge.setImageBitmap(imatgeBitmap);
        }
    }
}