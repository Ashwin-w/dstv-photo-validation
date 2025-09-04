package com.example.validator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private String category = "dish";
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectBtn = findViewById(R.id.selectBtn);
        Button uploadBtn = findViewById(R.id.uploadBtn);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        resultText = findViewById(R.id.resultText);

        selectBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });

        uploadBtn.setOnClickListener(v -> {
            if (imageUri != null) {
                resultText.setText("Pretend upload... Validating " + category);
            } else {
                Toast.makeText(this, "Select image first", Toast.LENGTH_SHORT).show();
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                category = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            resultText.setText("Image selected: " + imageUri.getLastPathSegment());
        }
    }
}
