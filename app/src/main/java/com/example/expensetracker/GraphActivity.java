package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;


public class GraphActivity extends AppCompatActivity {

    Button back;
    PieChart pieChart;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        back = findViewById(R.id.btnGraphGoBack);
        pieChart = findViewById(R.id.pieChart);
        db = new Database(this);

        setupPieChart();

        loadExpenseData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, MainActivity.class));
            }
        });


    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(true);  // To show values as percentages
        pieChart.getDescription().setEnabled(false); // Disable chart description
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f); // Add padding
        pieChart.setDragDecelerationFrictionCoef(0.95f); // Set deceleration coefficient
    }

    private void loadExpenseData() {
        // Predefined expense types
        String[] expenseTypes = getResources().getStringArray(R.array.add_expense_types);

        // Query the database to get the count of each expense type
        Cursor cursor = db.getExpenseTypesCount();

        if (cursor != null && cursor.getCount() > 0) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            // Initialize a map to store the count of each type
            HashMap<String, Integer> expenseCounts = new HashMap<>();

            // Fill the map with expense types and their counts
            while (cursor.moveToNext()) {
                String type = cursor.getString(0);  // Expense type
                int count = cursor.getInt(1);       // Count of that expense type
                expenseCounts.put(type, count);
            }

            // Populate PieChart entries using the predefined types
            for (String type : expenseTypes) {
                Integer count = expenseCounts.get(type);
                if (count != null && count > 0) {
                    // If the count is greater than 0, add it to the chart
                    entries.add(new PieEntry(count, type));
                    labels.add(type);
                }
            }

            // Create a PieDataSet from the entries
            PieDataSet dataSet = new PieDataSet(entries, "Expense Types");
            dataSet.setSliceSpace(3f); // Space between slices
            dataSet.setSelectionShift(5f); // Shift the slice when selected
            dataSet.setColors(getResources().getColor(R.color.yellow), getResources().getColor(R.color.RoyaleBlue), getResources().getColor(R.color.red), getResources().getColor(R.color.MintGreen), getResources().getColor(R.color.lightBlue), getResources().getColor(R.color.lightGreen)); // Customize colors

            // Create PieData using the PieDataSet
            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f); // Text size for percentage labels
            data.setValueTextColor(getResources().getColor(R.color.white)); // Color for value text

            // Set the data for the PieChart
            pieChart.setData(data);
            pieChart.invalidate(); // Refresh the chart to apply the new data
        } else {
            Toast.makeText(this, "No expense data available", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();  // Always close the cursor to avoid memory leaks
        }
    }


}