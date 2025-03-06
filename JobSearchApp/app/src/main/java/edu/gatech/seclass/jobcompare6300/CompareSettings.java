package edu.gatech.seclass.jobcompare6300;

import org.json.JSONException;
import org.json.JSONObject;

public class CompareSettings {
    private int salaryWeight;
    private int bonusWeight;
    private int stockWeight;
    private int homeBuyWeight;
    private int personalHolidaysWeight;
    private int internetStipendWeight;

    public static final CompareSettings DEFAULT_SETTINGS = new CompareSettings(1,1,1,1,1,1);

    public CompareSettings(int salaryWeight, int bonusWeight, int stockWeight, int homeBuyWeight, int personalHolidaysWeight, int internetStipendWeight) {
        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.stockWeight = stockWeight;
        this.homeBuyWeight = homeBuyWeight;
        this.personalHolidaysWeight = personalHolidaysWeight;
        this.internetStipendWeight = internetStipendWeight;
    }

    public CompareSettings(JSONObject obj) throws JSONException {
        this.salaryWeight = (obj.getInt("salary"));
        this.bonusWeight = (obj.getInt("bonus"));
        this.stockWeight = (obj.getInt("stock"));
        this.homeBuyWeight = (obj.getInt("homeBuy"));
        this.personalHolidaysWeight = (obj.getInt("personalHolidays"));
        this.internetStipendWeight = (obj.getInt("internetStipend"));
    }

    @Override
    public String toString() {
        return String.format("Salary: %d, Bonus: %d, Stock: %d, HomeFund: %d, Holidays: %d, Internet: %d", salaryWeight, bonusWeight, stockWeight, homeBuyWeight, personalHolidaysWeight, internetStipendWeight);
    }

    public Result<JSONObject> encode() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("salary", this.salaryWeight);
            obj.put("bonus", this.bonusWeight);
            obj.put("stock", this.stockWeight);
            obj.put("homeBuy", this.homeBuyWeight);
            obj.put("personalHolidays", this.personalHolidaysWeight);
            obj.put("internetStipend", this.internetStipendWeight);
        } catch (Exception e) {
            return new Result<>(Result.ERR_JSON, "Error encoding object: " + e.toString());
        }
        return new Result<>(obj);
    }

    int getSalaryWeight(){
        return this.salaryWeight;
    }

    int getBonusWeight(){
        return this.bonusWeight;
    }

    int getStockWeight(){
        return this.stockWeight;
    }

    int getHomeBuyWeight(){
        return this.homeBuyWeight;
    }

    int getPersonalHolidaysWeight(){
        return this.personalHolidaysWeight;
    }

    int getInternetStipendWeight(){
        return this.internetStipendWeight;
    }

    int getTotal(){
        return salaryWeight + bonusWeight + stockWeight + homeBuyWeight + personalHolidaysWeight + internetStipendWeight;
    }
}
