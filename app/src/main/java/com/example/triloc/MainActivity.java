package com.example.triloc;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ReferenceObject> objectList = new ArrayList<>();
    private ReferenceObjectAdapter adapter;

    private Button addObjectButton;
    private Button calculateButton;
    private RecyclerView objectRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objectRecyclerView = findViewById(R.id.objectRecyclerView);
        addObjectButton = findViewById(R.id.addObjectButton);
        calculateButton = findViewById(R.id.calculateButton);

        adapter = new ReferenceObjectAdapter(objectList);
        objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        objectRecyclerView.setAdapter(adapter);

        addObjectButton.setOnClickListener(v -> showAddObjectDialog());

        calculateButton.setOnClickListener(v -> calculateUserLocation());
    }

    private void showAddObjectDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_object, null);
        EditText editName = dialogView.findViewById(R.id.editObjectName);
        EditText editHeight = dialogView.findViewById(R.id.editHeight);
        EditText editAngle = dialogView.findViewById(R.id.editAngle);

        new AlertDialog.Builder(this)
                .setTitle("Add Reference Object")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    try {
                        String name = editName.getText().toString();
                        float height = Float.parseFloat(editHeight.getText().toString());
                        float angle = Float.parseFloat(editAngle.getText().toString());

                        objectList.add(new ReferenceObject(name, height, angle));
                        adapter.notifyDataSetChanged();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Enter valid height and angle", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void calculateUserLocation() {
        if (objectList.isEmpty()) {
            Toast.makeText(this, "No reference objects added", Toast.LENGTH_SHORT).show();
            return;
        }

        double sumX = 0, sumY = 0;
        int validCount = 0;

        for (ReferenceObject obj : objectList) {
            float height = obj.getObjectHeight();
            float angleDeg = obj.getHorizontalAngle();

            // Avoid angle = 0 or 90 to prevent infinite tan
            if (angleDeg == 0 || angleDeg == 90 || angleDeg == -90) continue;

            double angleRad = Math.toRadians(angleDeg);
            double distance = height / Math.tan(angleRad);

            // Project distance on X and Y axis
            double x = distance * Math.sin(angleRad);
            double y = distance * Math.cos(angleRad);

            sumX += x;
            sumY += y;
            validCount++;
        }

        if (validCount == 0) {
            Toast.makeText(this, "Angles too extreme. Use objects with valid angle values.", Toast.LENGTH_LONG).show();
            return;
        }

        double avgX = sumX / validCount;
        double avgY = sumY / validCount;

        String result = "Estimated Location:\nX = " + String.format("%.2f", avgX) + " m\nY = " + String.format("%.2f", avgY) + " m";
        new AlertDialog.Builder(this)
                .setTitle("Your Location")
                .setMessage(result)
                .setPositiveButton("OK", null)
                .show();
    }
}
