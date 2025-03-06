package edu.gatech.seclass.jobcompare6300;

import androidx.annotation.NonNull;

public class SelectableJob {
    private Job job;
    private boolean checked;

    public SelectableJob(Job job, boolean checked) {
        this.job = job;
        this.checked = checked;
    }

    public Job getJob() {
        return this.job;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
