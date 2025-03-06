package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EnterJobOffer extends AppCompatActivity {
    private EditText etTitle;
    private EditText etCompany;
    private EditText etCity;
    private EditText etState;
    private EditText etCoL;
    private EditText etSalary;
    private EditText etBonus;
    private EditText etStockOptions;
    private EditText etHomeFund;
    private EditText etPersonalHolidays;
    private EditText etInternetStipend;
    private App app;
    private int lastJobSaveHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offer);

        this.app = App.getApp();

        // Programmatic access to elements
        this.etTitle = findViewById(R.id.textFieldTitle);
        this.etCompany = findViewById(R.id.textFieldCompany);
        this.etCity = findViewById(R.id.textFieldCity);
        this.etState = findViewById(R.id.textFieldState);
        this.etCoL = findViewById(R.id.textFieldCoL);
        this.etSalary = findViewById(R.id.textFieldSalary);
        this.etBonus = findViewById(R.id.textFieldBonus);
        this.etStockOptions = findViewById(R.id.textFieldStockOptions);
        this.etHomeFund = findViewById(R.id.textFieldHomeFund);
        this.etPersonalHolidays = findViewById(R.id.textFieldPersonalHolidays);
        this.etInternetStipend = findViewById(R.id.textFieldInternetStipend);
    }

    public void onExitPress(View view) {
        super.onBackPressed();
    }

    private boolean saveCurrentJobOfferDetails() {
        boolean hasError = false;
        Job job = new Job();
        Result<String> sRes;
        Result<Integer> iRes;

        String title = this.etTitle.getText().toString();
        sRes = job.setTitle(title);
        if (sRes.isErr()) {
            this.etTitle.setError(sRes.getError());
            hasError = true;
        }

        String company = this.etCompany.getText().toString();
        sRes = job.setCompany(company);
        if (sRes.isErr()) {
            this.etCompany.setError(sRes.getError());
            hasError = true;
        }

        String city = this.etCity.getText().toString();
        sRes = job.setCity(city);
        if (sRes.isErr()) {
            this.etCity.setError(sRes.getError());
            hasError = true;
        }

        String state = this.etState.getText().toString();
        sRes = job.setState(state);
        if (sRes.isErr()) {
            this.etState.setError(sRes.getError());
            hasError = true;
        }

        String salary = this.etSalary.getText().toString();
        iRes = job.setSalary(salary);
        if (iRes.isErr()) {
            this.etSalary.setError(iRes.getError());
            hasError = true;
        }

        String colIdx = this.etCoL.getText().toString();
        iRes = job.setCoLIndex(colIdx);
        if (iRes.isErr()) {
            this.etCoL.setError(iRes.getError());
            hasError = true;
        }

        String bonus = this.etBonus.getText().toString();
        iRes = job.setBonus(bonus);
        if (iRes.isErr()) {
            this.etBonus.setError(iRes.getError());
            hasError = true;
        }

        String stockOptions = this.etStockOptions.getText().toString();
        iRes = job.setNumStockOptions(stockOptions);
        if (iRes.isErr()) {
            this.etStockOptions.setError(iRes.getError());
            hasError = true;
        }

        String homeBuyFind = this.etHomeFund.getText().toString();
        iRes = job.setHomeBuyingFund(homeBuyFind);
        if (iRes.isErr()) {
            this.etHomeFund.setError(iRes.getError());
            hasError = true;
        }

        String personalHolidays = this.etPersonalHolidays.getText().toString();
        iRes = job.setNumPersonalChoiceHolidays(personalHolidays);
        if (iRes.isErr()) {
            this.etPersonalHolidays.setError(iRes.getError());
            hasError = true;
        }

        String internetStipend = this.etInternetStipend.getText().toString();
        iRes = job.setInternetStipend(internetStipend);
        if (iRes.isErr()) {
            this.etInternetStipend.setError(iRes.getError());
            hasError = true;
        }

        if (!hasError) {
            if (!app.addJobOffer(job)) {
                throw new RuntimeException("Failed to save job offer");
            }
            this.lastJobSaveHash = job.hashCode();
        } else {
            Log.w("MyApp", "Some error, not adding job offer");
            return false;
        }
        return true;
    }

    public void onSaveExitPress(View view) {
        if (!this.saveCurrentJobOfferDetails())
            return;
        super.onBackPressed();
    }

    private void clearFields() {
        this.etTitle.setText("");
        this.etCompany.setText("");
        this.etCity.setText("");
        this.etState.setText("");
        this.etCoL.setText("");
        this.etSalary.setText("");
        this.etBonus.setText("");
        this.etStockOptions.setText("");
        this.etHomeFund.setText("");
        this.etPersonalHolidays.setText("");
        this.etInternetStipend.setText("");
    }

    public void onSaveEnterAnotherPress(View view) {
        if (!this.saveCurrentJobOfferDetails())
            return;
        this.clearFields();
    }

    public void onSaveComparePress(View view) {
        if (!this.saveCurrentJobOfferDetails())
            return;
        this.clearFields();     // If user goes to this page back then they should see cleared fields
        Intent next = new Intent(view.getContext(), CompareSelectJobs.class);
        next.putExtra("initialSelectionJobHash", this.lastJobSaveHash);
        startActivity(next);
    }
}