package com.judah.jasperdewaelheyns.lionsapplication;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.judah.jasperdewaelheyns.lionsapplication.database.LionProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildOverviewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private int childId;
    private long childLong;
    private TextView childName, parentName, childAge, childDob;

    public ChildOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View childOverview = inflater.inflate(R.layout.fragment_child_overview, container, false);

        childId = getArguments().getInt("CID");
        childLong = new Long(childId);

        childAge = (TextView) childOverview.findViewById(R.id.tv_age);

        getLoaderManager().initLoader(2, null, this);

        return childOverview;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(LionProvider.CONTENT_URI + "/lions/" + childLong);
        CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(), uri,  null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        StringBuilder stringBuilder = new StringBuilder();
        if (data.moveToFirst()) {
            for (int i = 0; i < data.getColumnCount(); i++) {
                stringBuilder.append(data.getString(data.getColumnIndex(data.getColumnName(i))) + " ");
            }
        }
        else {
            stringBuilder.append("no data");

        }

        childAge.setText(String.valueOf(ageCheck(data.getString(data.getColumnIndex(data.getColumnName(3))))));
        Toast.makeText(getActivity().getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private int ageCheck(String dateOfBirth){
        Calendar dob = Calendar.getInstance();
        Date date;

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(dateOfBirth);
            dob.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        return age;
    }
}
