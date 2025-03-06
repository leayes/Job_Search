package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class App extends AppCompatActivity {
    private Context ctx;
    private static App app; // singleton
    private CompareSettings compSettings;
    private List<Job> jobOffers;
    private Job currentJob;
    private static final String FILENAME_JSON_JOB_CURRENT = "job_current.json";
    private static final String FILENAME_JSON_JOB_OFFERS = "job_offers.json";
    private static final String FILENAME_COMP_SETTINGS = "comp_settings.json";
    private File externalFilesDir;
    private Random rng;

    private static final boolean CLEAR_JOB_OFFERS = false;
    private static final boolean ADD_TEST_DATA = false;


    public App() {
        this.compSettings = CompareSettings.DEFAULT_SETTINGS;
        this.jobOffers = new ArrayList<>();
        this.currentJob = null;
        this.rng = new Random();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ctx = this.getApplicationContext();
        App.app = this;  // onCreate assumed to be called once for our singleton
        this.externalFilesDir = getExternalFilesDir(null);
        Log.w("MyApp", "Files location: " + this.ctx.getFilesDir());
        this.readPersistentData();
        this.clearJobOffers();
        this.addTestData();
    }

    private void clearJobOffers() {
        if (!CLEAR_JOB_OFFERS)
            return;
        this.jobOffers.clear();
        this.persistJobOffers();
    }

    private int randIntBounds(int min, int max) {
        return rng.nextInt(max - min + 1) + min;
    }

    private void addTestData() {
        if (!ADD_TEST_DATA)
            return;
        for (int i = 1; i <= 14; i++) {
            Job j = new Job();
            j.setTitle("job" + i);
            j.setCompany("company" + i);
            j.setCity("city" + this.randIntBounds(10, 1000));
            j.setState("state" + this.randIntBounds(10, 1000));
            j.setSalary("" + this.randIntBounds(10000, 30000));
            j.setBonus("" + this.randIntBounds(10, 1300));
            j.setNumStockOptions("" + this.randIntBounds(0, 500));
            j.setCoLIndex("" + this.randIntBounds(1, 200));
            j.setHomeBuyingFund("" + this.randIntBounds(0, 1300));
            j.setNumPersonalChoiceHolidays("" + this.randIntBounds(0, 20));
            j.setInternetStipend("" + this.randIntBounds(0, 75));
            this.jobOffers.add(j);
        }
        persistJobOffers();
    }

    /**
     * Read data from persistent location
     */
    private void readPersistentData() {
        // Current Job
        Result<JSONObject> currentJob = loadJSON(FILENAME_JSON_JOB_CURRENT);
        if (currentJob.isOk()) {
            try {
                this.currentJob = new Job(currentJob.val());
            } catch (Exception e) {
                Log.w("MyApp", "Error loading saved current job from file: " + e.toString());
            }
            Log.w("MyApp", "Loaded current job from file!");
        }

        // Job Offers
        Result<JSONObject> jobOffersRes = loadJSON(FILENAME_JSON_JOB_OFFERS);
        if (jobOffersRes.isOk()) {
            try {
                JSONObject obj = jobOffersRes.val();
                Log.w("MyApp", "JOB OFFERS FROM JSON: " + obj);
                JSONArray jobArr = obj.getJSONArray("jobOffersList");
                for (int i = 0; i < jobArr.length(); i++) {
                    JSONObject jobOfferData = jobArr.getJSONObject(i);
                    try {
                        this.jobOffers.add(new Job(jobOfferData));
                    } catch (Exception e) {
                        Log.w("MyApp", "Malformed job offer: " + jobOfferData);
                    }
                }
            } catch (Exception e) {
                Log.w("MyApp", "Error loading job offer from file: " + e.toString());
            }
            Log.w("MyApp", "Loaded " + this.jobOffers.size() + " job offers");
        }

        // Comparison settings
        Result<JSONObject> compSettings = loadJSON(FILENAME_COMP_SETTINGS);
        if (compSettings.isOk()) {
            try {
                this.compSettings = new CompareSettings(compSettings.val());
            } catch (Exception e) {
                Log.w("MyApp", "Error loading comparison settings: " + e.toString());
            }
            Log.w("MyApp", "Loaded comparison settings from file! " + this.compSettings);
        } else {
            this.compSettings = CompareSettings.DEFAULT_SETTINGS;   // use default comp settings
        }

    }

    public void onCurrentJobPress(View view) {
        startActivity(new Intent(view.getContext(), CurrentJob.class));
    }

    public void onJobOffersPress(View view) {
        startActivity(new Intent(view.getContext(), EnterJobOffer.class));
    }

    public void onCompSettingsPress(View view) {
        startActivity(new Intent(view.getContext(), AdjustCompareSettings.class));
    }

    private boolean canRunComparison() {
        int numOptions = this.jobOffers.size();
        if (this.currentJob != null) {
            numOptions += 1;
        }
        return numOptions >= 2;
    }

    public void onComparePress(View view) {
        if (!this.canRunComparison()) {
            Log.w("MyApp", "can't go to comparison screen since don't have enough job offers");
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
            alertBuilder.setTitle("Cannot Compare Jobs");
            alertBuilder.setMessage("Need at least 2 job offers first");
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            return;
        }
        startActivity(new Intent(view.getContext(), CompareSelectJobs.class));
    }

    public static App getApp() {
        if (App.app == null) {
            throw new RuntimeException("Singleton app instance should have been set in onCreate()");
            // App.app = new App();
        }
        return App.app;
    }

    public void clearAllPersistedData(){
        truncateFile(FILENAME_JSON_JOB_CURRENT);
        truncateFile(FILENAME_JSON_JOB_OFFERS);
        truncateFile(FILENAME_COMP_SETTINGS);
    }

    public boolean setCompSetting(CompareSettings settings) {
        this.compSettings = settings;
        Log.w("MyApp", "New comparison settings: " + this.compSettings);
        return this.persistCompSetting();
    }

    private boolean persistCompSetting() {
        // Persist comparison settings
        Result<JSONObject> obj = this.compSettings.encode();
        if (obj.isErr()) {
            return false;
        }
        Log.w("MyApp", "saving comparison settings json");
        return this.saveJSON(obj.get(), FILENAME_COMP_SETTINGS);
    }

    public boolean setCurrentJob(Job job) {
        if (!job.validate()) {
            return false;
        }
        this.currentJob = job;
        return persistCurrentJob();
    }

    private boolean persistCurrentJob() {
        // Persist the current job json file
        Result<JSONObject> obj = this.currentJob.encode();
        if (obj.isErr()) {
            return false;
        }
        Log.w("MyApp", "saving current job json");
        return this.saveJSON(obj.get(), FILENAME_JSON_JOB_CURRENT);
    }

    public Job getCurrentJob() {
        return this.currentJob;
    }

    public CompareSettings getCompSettings() {
        return compSettings;
    }


    public boolean addJobOffer(Job job) {
        if (!job.validate()) {
            return false;
        }
        this.jobOffers.add(job);
        return this.persistJobOffers();
    }

    private boolean persistJobOffers() {
        JSONObject obj = new JSONObject();
        List<JSONObject> jobOffersList = new ArrayList<>();
        for (Job j : this.jobOffers) {
            Result<JSONObject> jobObject = j.encode();
            if (jobObject.isErr()) {
                return false;
            }
            jobOffersList.add(jobObject.val());
        }
        try {
            obj.put("jobOffersList", new JSONArray(jobOffersList));
        } catch (Exception e) {
            return false;
        }
        Log.w("MyApp", "saving job offer json");
        return this.saveJSON(obj, FILENAME_JSON_JOB_OFFERS);
    }

    /**
     * Returns list of all job offers and current job sorted best to worst according to a score
     * @return
     */
    public List<Job> getSortedJobOffers() {
        List<Job> results = new ArrayList<>();
        results.addAll(this.jobOffers);
        if (this.currentJob != null) {
            results.add(this.currentJob);
        }
        Collections.sort(results, new Job.JobComparator());
        Collections.reverse(results);

        // Sanity check
        for (int i = 0; i < results.size() - 1; i++)
            assert results.get(i).calcJobScore() >= results.get(i+1).calcJobScore();
        return results;
    }

    /**
     * Save JSON object to a file
     *
     * @param obj
     * @param fileName
     * @return
     */
    private boolean saveJSON(JSONObject obj, String fileName) {
        // Save JSON to file
        byte[] data = obj.toString().getBytes();
        File filePath = new File(this.externalFilesDir, fileName);
        Log.w("MyApp", "json filePath = " + filePath);
        try {
            FileOutputStream writer = new FileOutputStream(filePath);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            Log.w("MyApp", "Error writing json file: " + e.toString());
            return false;
        }
        Log.w("MyApp", "Success writing json file: " + filePath);
        return true;
    }

    /**
     * Read file and try to load content into json object
     *
     * @param fileName
     * @return
     */
    private Result<JSONObject> loadJSON(String fileName) {
        File filePath = new File(this.externalFilesDir, fileName);
        String content;
        JSONObject obj;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath.toString())));
        } catch (Exception e) {
            String errorMsg = "Error reading file " + filePath + ": " + e.toString();
            Log.w("MyApp", errorMsg);
            return new Result<>(Result.ERR_FILE_RW, errorMsg);
        }
        try {
            obj = new JSONObject(content);
        } catch (Exception e) {
            String errorMsg = "File content is not valid JSON: " + content;
            Log.w("MyApp", errorMsg);
            return new Result<>(Result.ERR_FILE_RW, errorMsg);
        }
        return new Result<>(obj);
    }

    private Result<Void> truncateFile(String fileName) {
        File filePath = new File(this.externalFilesDir, fileName);
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.setLength(0); // Truncate file to 0 bytes
        } catch (Exception e) {
            String errorMsg = "Error truncating file " + filePath + ": " + e.toString();
            Log.w("MyApp", errorMsg);
            return new Result<>(Result.ERR_FILE_RW, errorMsg);
        }
        return new Result<>(null); // Success, no object to return
    }
}
