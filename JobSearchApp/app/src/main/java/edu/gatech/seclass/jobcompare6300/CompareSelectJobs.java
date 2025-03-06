package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompareSelectJobs extends AppCompatActivity {
    private List<SelectableJob> selectableOffers;
    private ListView offersListView;
    private JobCompareListAdapter jobsAdapter;

    private static int MAX_SELECT = 2;
    private static int MUST_SELECT_BEFORE_COMPARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_select_jobs);

        List<Job> offers = App.getApp().getSortedJobOffers();
        this.selectableOffers = new ArrayList<>();
        for (Job j : offers)
            this.selectableOffers.add(new SelectableJob(j, false));
        this.offersListView = this.findViewById(R.id.jobOfferList);
        this.jobsAdapter = new JobCompareListAdapter(this.getApplicationContext(), this.selectableOffers);

        this.offersListView.setAdapter(this.jobsAdapter);
        this.offersListView.setClickable(true);
        this.offersListView.setOnItemClickListener((parent, v, position, id) -> {
            // Change selection state in underlying type so that when user scrolls the state will be retained
            SelectableJob sjob = (SelectableJob) parent.getItemAtPosition(position);
            if (sjob.isChecked()) {
                sjob.setChecked(false);
            } else if (this.numJobsSelected() < MAX_SELECT) {
                sjob.setChecked(true);
            }
            // Let adapter know data changed so it will re-render
            jobsAdapter.notifyDataSetChanged();
        });
        this.selectInitialJob();
    }

    private void selectInitialJob() {
        Intent intent = this.getIntent();
        if (!intent.hasExtra("initialSelectionJobHash"))
            return;
        int jobHash = intent.getIntExtra("initialSelectionJobHash", -1);
        if (jobHash == -1)
            return;
        // Find job by hash
        for (SelectableJob sj : this.selectableOffers) {
            if (sj.getJob().hashCode() == jobHash) {
                sj.setChecked(true);
                jobsAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private int numJobsSelected() {
        int count = 0;
        for (SelectableJob j : this.selectableOffers)
            if (j.isChecked())
                count += 1;
        return count;
    }

    public void onExitPress(View view) {
        super.onBackPressed();
    }

    public void onComparePress(View view) {
        int numSelected = this.numJobsSelected();
        if (numSelected != MUST_SELECT_BEFORE_COMPARE) {
            Log.w("MyApp", "can't go to comparison screen since user needs to select two jobs");
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
            alertBuilder.setTitle("Select two jobs to compare");
            alertBuilder.setMessage("Please select two jobs to compare. Currently there " + (numSelected == 1 ? "is" : "are") + " " + numSelected + " selected");
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            return;
        }
        // Get the two selected jobs
        Job job1 = null;
        Job job2 = null;
        for (SelectableJob j : this.selectableOffers) {
            if (!j.isChecked())
                continue;
            if (job1 == null) {
                job1 = j.getJob();
            } else {
                job2 = j.getJob();
                break;
            }
        }
        Intent next = new Intent(view.getContext(), CompareResults.class);
        next.putExtra("job1Json", job1.encode().val().toString());
        next.putExtra("job2Json", job2.encode().val().toString());
        startActivity(next);
    }
}