package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class JobCompareListAdapter extends ArrayAdapter<SelectableJob> {

    public JobCompareListAdapter(Context context, List<SelectableJob> jobs) {
        super(context, R.layout.compare_job_entry, jobs);
    }

    @NonNull
    public View getView(int position, View convert, @NonNull ViewGroup parent) {
        if (convert == null) {
            convert = LayoutInflater.from(this.getContext()).inflate(R.layout.compare_job_entry, parent, false);
        }
        SelectableJob sjob = this.getItem(position);
        ((CheckBox) convert.findViewById(R.id.selectedBox)).setChecked(sjob.isChecked());
        ((TextView) convert.findViewById(R.id.companyField)).setText(sjob.getJob().getCompany());
        ((TextView) convert.findViewById(R.id.positionField)).setText(sjob.getJob().getTitle());
        // Alternating colors
        LinearLayout ll = (LinearLayout) convert.findViewById(R.id.jobEntryRow);
        if (position % 2 == 0) {
            ll.setBackgroundColor(0xFFD3D3D3);
        } else {
            ll.setBackgroundColor(0xFFFFFBFE);
        }
        return convert;
    }
}
