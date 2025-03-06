package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class CurrentJob extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job);

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

        this.setJobDetails(this.app.getCurrentJob());
    }

    private void setJobDetails(Job job) {
        if (job == null) {
            return;
        }
        this.etTitle.setText(job.getTitle());
        this.etCompany.setText(job.getCompany());
        this.etCity.setText(job.getCity());
        this.etState.setText(job.getState());
        this.etCoL.setText("" + job.getCoLIndex());
        this.etSalary.setText("" + job.getSalary());
        this.etBonus.setText("" + job.getBonus());
        this.etStockOptions.setText("" + job.getNumStockOptions());
        this.etHomeFund.setText("" + job.getHomeBuyingFund());
        this.etPersonalHolidays.setText("" + job.getNumPersonalChoiceHolidays());
        this.etInternetStipend.setText("" + job.getInternetStipend());
    }

    public void onExitPress(View view) {
        super.onBackPressed();
    }

    public void onSaveExitPress(View view) {
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
//
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
            if (!app.setCurrentJob(job)) {
                throw new RuntimeException("Error saving current job");
            }
            super.onBackPressed();
        }
    }
}