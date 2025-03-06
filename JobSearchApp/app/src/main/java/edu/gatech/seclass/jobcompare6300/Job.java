package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

public class Job {
    private String title = "";
    private String company = "";
    private String city = "";
    private String state = "";
    private int coLIndex = -1;
    private int salary = -1;
    private int bonus = -1;
    private int numStockOptions = -1;
    private int homeBuyingFund = -1;
    private int numPersonalChoiceHolidays = -1;
    private int internetStipend = -1;

    public static class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job a, Job b) {
            return (int) (a.calcJobScore() - b.calcJobScore());
        }
    }

    // Empty job
    public Job() {
    }

    /**
     * Try construct Job from JSON object
     *
     * @param obj
     */
    public Job(JSONObject obj) throws JSONException, IllegalArgumentException {
        this.setTitle(obj.getString("title"));
        this.setCompany(obj.getString("company"));
        this.setCity(obj.getString("city"));
        this.setState(obj.getString("state"));
        this.setCoLIndex("" + obj.getInt("col"));
        this.setSalary("" + obj.getInt("salary"));
        this.setBonus("" + obj.getInt("bonus"));
        this.setNumStockOptions("" + obj.getInt("stocks"));
        this.setHomeBuyingFund("" + obj.getInt("home-fund"));
        this.setNumPersonalChoiceHolidays("" + obj.getInt("personal-holidays"));
        this.setInternetStipend("" + obj.getInt("internet-stipend"));
    }

    public Result<JSONObject> encode() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", this.getTitle());
            obj.put("company", this.getCompany());
            obj.put("city", this.getCity());
            obj.put("state", this.getState());
            obj.put("col", this.getCoLIndex());
            obj.put("salary", this.getSalary());
            obj.put("bonus", this.getBonus());
            obj.put("stocks", this.getNumStockOptions());
            obj.put("home-fund", this.getHomeBuyingFund());
            obj.put("personal-holidays", this.getNumPersonalChoiceHolidays());
            obj.put("internet-stipend", this.getInternetStipend());
        } catch (Exception e) {
            return new Result<>(Result.ERR_JSON, "Error encoding object: " + e.toString());
        }
        return new Result<>(obj);
    }

    public boolean validate() {
        return !title.equals("") && !company.equals("") && !city.equals("")
                && !state.equals("") && coLIndex != -1 && salary != -1 && bonus != -1
                && numStockOptions != -1 && homeBuyingFund != -1 && numPersonalChoiceHolidays != -1
                && internetStipend != -1;
    }

    private Result<Integer> parseInt(String s) {
        try {
            int n = Integer.parseInt(s);
            return new Result<>(n);
        } catch (Exception e) {
            return new Result<>(Result.ERR_NOT_A_INT, "Not an integer");
        }
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    public String toString() {
        String res = String.format("Title: %s", this.getTitle());
        res = String.format("%s\nCompany: %s", res, this.getCompany());
        res = String.format("%s\nCity: %s", res, this.getCity());
        res = String.format("%s\nState: %s", res, this.getState());
        res = String.format("%s\nCoL: %s", res, this.getCoLIndex());
        res = String.format("%s\nSalary: $%d (adjusted= $%.2f)", res, this.getSalary(), this.costOfLivingAdjust(this.getSalary()));
        res = String.format("%s\nBonus: $%d (adjusted= $%.2f)", res, this.getBonus(), this.costOfLivingAdjust(this.getBonus()));
        res = String.format("%s\nStock Options: %d", res, this.getNumStockOptions());
        res = String.format("%s\nHome Buy Fund: $%d", res, this.getHomeBuyingFund());
        res = String.format("%s\nHolidays: %d", res, this.getNumPersonalChoiceHolidays());
        res = String.format("%s\nInternet Stipend: $%d", res, this.getInternetStipend());
        return res;
    }

    public double calcJobScore() {
        App app = App.getApp();
        CompareSettings compareSettings = app.getCompSettings();

        double wTotal = 1.0 * compareSettings.getTotal();
        double wSalary = compareSettings.getSalaryWeight() / wTotal;
        double wBonus = compareSettings.getBonusWeight() / wTotal;
        double wStock = compareSettings.getStockWeight() / wTotal;
        double wHomeBuy = compareSettings.getHomeBuyWeight() / wTotal;
        double wHolidays = compareSettings.getPersonalHolidaysWeight() / wTotal;
        double wInternetStipend = compareSettings.getInternetStipendWeight() / wTotal;

        double partSalary = wSalary * this.costOfLivingAdjust(this.getSalary());
        double partBonus = wBonus * this.costOfLivingAdjust(this.getBonus());
        double partStock = wStock * this.getNumStockOptions() / 3.0;
        double partHomeBuy = wHomeBuy * this.getHomeBuyingFund();
        double partHoliday = wHolidays * this.getNumPersonalChoiceHolidays() * this.costOfLivingAdjust(this.getSalary()) / 260.0;
        double partInternetStipend = wInternetStipend * this.getInternetStipend();

        return partSalary + partBonus + partStock + partHomeBuy + partHoliday + partInternetStipend;
    }

    public double costOfLivingAdjust(int value) {
        return value * ((100.0 / this.getCoLIndex()));
    }

    public Result<String> setTitle(String newTitle) {
        newTitle = newTitle.trim();
        if (newTitle.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Title is empty");
        }
        this.title = newTitle;
        return new Result<>(this.title);
    }

    public Result<String> setCompany(String newCompany) {
        newCompany = newCompany.trim();
        if (newCompany.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Company is empty");
        }
        this.company = newCompany;
        return new Result<>(this.company);
    }

    public Result<String> setCity(String newCity) {
        newCity = newCity.trim();
        if (newCity.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "City is empty");
        }
        this.city = newCity;
        return new Result<>(this.city);
    }

    public Result<String> setState(String newState) {
        newState = newState.trim();
        if (newState.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "State is empty");
        }
        this.state = newState;
        return new Result<>(this.state);
    }

    public Result<Integer> setSalary(String newSalary) {
        if (newSalary.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Salary is empty");
        }
        Result<Integer> salRes = this.parseInt(newSalary);
        if (salRes.isErr()) {
            return new Result<>(salRes.getErrCode(), "Salary is not an integer");
        }
        if (salRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Salary is negative");
        }
        this.salary = salRes.val();
        return new Result<>(this.salary);
    }

    public Result<Integer> setBonus(String newBonus) {
        if (newBonus.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Bonus is empty");
        }
        Result<Integer> bonusRes = this.parseInt(newBonus);
        if (bonusRes.isErr()) {
            return new Result<>(bonusRes.getErrCode(), "Bonus is not an integer");
        }
        if (bonusRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Bonus is negative");
        }
        this.bonus = bonusRes.val();
        return new Result<>(this.bonus);
    }

    public Result<Integer> setNumStockOptions(String newNumStockOptions) {
        if (newNumStockOptions.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Number of stock options is empty");
        }
        Result<Integer> stockRes = this.parseInt(newNumStockOptions);
        if (stockRes.isErr()) {
            return new Result<>(stockRes.getErrCode(), "Number of stock options is not an integer");
        }
        if (stockRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Number of stock options is negative");
        }
        this.numStockOptions = stockRes.val();
        return new Result<>(this.numStockOptions);
    }

    public Result<Integer> setCoLIndex(String coLIndex) {
        if (coLIndex.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Cost of living index is empty");
        }
        Result<Integer> colRes = this.parseInt(coLIndex);
        if (colRes.isErr()) {
            return new Result<>(colRes.getErrCode(), "Cost of living index is not an integer");
        }
        if (colRes.val() == 0) {
            return new Result<>(Result.ERR_ZERO, "Cost of living index may not be zero");
        }
        if (colRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Cost of living index is negative");
        }
        this.coLIndex = colRes.val();
        return new Result<>(this.coLIndex);
    }

    public Result<Integer> setHomeBuyingFund(String homeBuyingFund) {
        if (homeBuyingFund.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Home buying fund index is empty");
        }
        Result<Integer> homeRes = this.parseInt(homeBuyingFund);
        if (homeRes.isErr()) {
            return new Result<>(homeRes.getErrCode(), "Home buying fund is not an integer");
        }
        if (homeRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Home buying fund is negative");
        }
        if (homeRes.val() > (int) (this.salary * 0.15)) {
            return new Result<>(Result.ERR_EXCEED_MAX, "Home buying fund exceeds 15% of salary");
        }
        this.homeBuyingFund = homeRes.val();
        return new Result<>(this.homeBuyingFund);
    }

    public Result<Integer> setNumPersonalChoiceHolidays(String numPersonalChoiceHolidays) {
        if (numPersonalChoiceHolidays.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Personal choice holidays is empty");
        }
        Result<Integer> holidayRes = this.parseInt(numPersonalChoiceHolidays);
        if (holidayRes.isErr()) {
            return new Result<>(holidayRes.getErrCode(), "Personal choice holidays is not an integer");
        }
        if (holidayRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Personal choice holidays is negative");
        }
        if (holidayRes.val() > 20) {
            return new Result<>(Result.ERR_EXCEED_MAX, "Personal choice holidays exceeds maximum of 20 days");
        }
        this.numPersonalChoiceHolidays = holidayRes.val();
        return new Result<>(this.numPersonalChoiceHolidays);
    }

    public Result<Integer> setInternetStipend(String internetStipend) {
        if (internetStipend.length() == 0) {
            return new Result<>(Result.ERR_ZERO_LEN, "Internet stipend is empty");
        }
        Result<Integer> internetRes = this.parseInt(internetStipend);
        if (internetRes.isErr()) {
            return new Result<>(internetRes.getErrCode(), "Internet stipend is not an integer");
        }
        if (internetRes.val() < 0) {
            return new Result<>(Result.ERR_NEG, "Internet stipend is negative");
        }
        if (internetRes.val() > 75) {
            return new Result<>(Result.ERR_EXCEED_MAX, "Internet stipend exceeds maximum of $75");
        }
        this.internetStipend = internetRes.val();
        return new Result<>(this.internetStipend);
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getCoLIndex() {
        return coLIndex;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

    public int getNumStockOptions() {
        return numStockOptions;
    }

    public int getHomeBuyingFund() {
        return homeBuyingFund;
    }

    public int getNumPersonalChoiceHolidays() {
        return numPersonalChoiceHolidays;
    }

    public int getInternetStipend() {
        return internetStipend;
    }
}
