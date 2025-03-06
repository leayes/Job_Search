package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class CompareResults extends AppCompatActivity {

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_results);

        // Populate elements with input data
        String job1Json = this.getIntent().getStringExtra("job1Json");
        String job2Json = this.getIntent().getStringExtra("job2Json");
        Job job1;
        Job job2;
        try {
            job1 = new Job(new JSONObject(job1Json));
            job2 = new Job(new JSONObject(job2Json));
            Log.w("MyApp", "Compare jobs " + job1 + " and " + job2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ((TextView) this.findViewById(R.id.crTitle1)).setText(job1.getTitle());
        ((TextView) this.findViewById(R.id.crCompany1)).setText(job1.getCompany());
        ((TextView) this.findViewById(R.id.crCity1)).setText(job1.getCity());
        ((TextView) this.findViewById(R.id.crState1)).setText(job1.getState());
        ((TextView) this.findViewById(R.id.crSalaryAdj1)).setText(String.format("%.2f", job1.costOfLivingAdjust(job1.getSalary())));
        ((TextView) this.findViewById(R.id.crBonusAdj1)).setText(String.format("%.2f", job1.costOfLivingAdjust(job1.getBonus())));
        ((TextView) this.findViewById(R.id.crStocks1)).setText("" + job1.getNumStockOptions());
        ((TextView) this.findViewById(R.id.crHomeBuyFund1)).setText("" + job1.getHomeBuyingFund());
        ((TextView) this.findViewById(R.id.crHolidays1)).setText("" + job1.getNumPersonalChoiceHolidays());
        ((TextView) this.findViewById(R.id.crInternetStipend1)).setText("" + job1.getInternetStipend());

        ((TextView) this.findViewById(R.id.crTitle2)).setText(job2.getTitle());
        ((TextView) this.findViewById(R.id.crCompany2)).setText(job2.getCompany());
        ((TextView) this.findViewById(R.id.crCity2)).setText(job2.getCity());
        ((TextView) this.findViewById(R.id.crState2)).setText(job2.getState());
        ((TextView) this.findViewById(R.id.crSalaryAdj2)).setText(String.format("%.2f", job2.costOfLivingAdjust(job2.getSalary())));
        ((TextView) this.findViewById(R.id.crBonusAdj2)).setText(String.format("%.2f", job2.costOfLivingAdjust(job2.getBonus())));
        ((TextView) this.findViewById(R.id.crStocks2)).setText("" + job2.getNumStockOptions());
        ((TextView) this.findViewById(R.id.crHomeBuyFund2)).setText("" + job2.getHomeBuyingFund());
        ((TextView) this.findViewById(R.id.crHolidays2)).setText("" + job2.getNumPersonalChoiceHolidays());
        ((TextView) this.findViewById(R.id.crInternetStipend2)).setText("" + job2.getInternetStipend());
    }

    public void onExitPress(View view) {
        // Go back to main menu, not the selection page
        Intent intent = new Intent(this, App.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void onNewComparisonPress(View view) {
        super.onBackPressed();
    }
}
