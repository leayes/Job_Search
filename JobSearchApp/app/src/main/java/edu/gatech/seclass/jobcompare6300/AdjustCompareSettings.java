package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AdjustCompareSettings extends AppCompatActivity {
    private EditText salaryWeight;
    private EditText bonusWeight;
    private EditText stockWeight;
    private EditText homeBuyWeight;
    private EditText personalHolidaysWeight;
    private EditText internetStipendWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_compare_settings);

        // Get access to elements
        this.salaryWeight = findViewById(R.id.compSalary);
        this.bonusWeight = findViewById(R.id.compBonus);
        this.stockWeight = findViewById(R.id.compStock);
        this.homeBuyWeight = findViewById(R.id.compHomeFund);
        this.personalHolidaysWeight = findViewById(R.id.compPersonalHolidays);
        this.internetStipendWeight = findViewById(R.id.compInternetStipend);

        CompareSettings current = App.getApp().getCompSettings();
        this.salaryWeight.setText("" + current.getSalaryWeight());
        this.bonusWeight.setText("" + current.getBonusWeight());
        this.stockWeight.setText("" + current.getStockWeight());
        this.homeBuyWeight.setText("" + current.getHomeBuyWeight());
        this.personalHolidaysWeight.setText("" + current.getPersonalHolidaysWeight());
        this.internetStipendWeight.setText("" + current.getInternetStipendWeight());
    }

    public void onExitPress(View view) {
        super.onBackPressed();
    }

    public int getValue(EditText field) {
        String s = field.getText().toString();
        if (s.equals("")) {
            return 1;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            Log.w("MyApp", "Number field gave something other than an integer: " + s);
            return -999;
        }
    }

    public void onSavePress(View view) {
        // Collect data
        int salary = getValue(salaryWeight);
        int bonus = getValue(bonusWeight);
        int stock = getValue(stockWeight);
        int homeBuy = getValue(homeBuyWeight);
        int personalHolidays = getValue(personalHolidaysWeight);
        int internetStipend = getValue(internetStipendWeight);

        // Change comp settings
        if (!App.getApp().setCompSetting(new CompareSettings(salary, bonus, stock, homeBuy, personalHolidays, internetStipend))) {
            throw new RuntimeException("Failed to save comparison settings");
        }
        super.onBackPressed();
    }
}