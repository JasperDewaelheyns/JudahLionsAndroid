package com.judah.jasperdewaelheyns.lionsapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.judah.jasperdewaelheyns.lionsapplication.database.LionContract;
import com.judah.jasperdewaelheyns.lionsapplication.database.LionProvider;

public class AddChildFragment extends Fragment {

    public AddChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View addChildView = inflater.inflate(R.layout.fragment_add_child, container, false);

        Button insertButton = (Button) addChildView.findViewById(R.id.btn_add_lion);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertTask().addView(addChildView.findViewById(R.id.et_fName))
                        .addView(addChildView.findViewById(R.id.et_lName))
                        .addView(addChildView.findViewById(R.id.et_pName))
                        .addView(addChildView.findViewById(R.id.et_phNumber))
                        .addView(addChildView.findViewById(R.id.et_bday)).execute();
            }
        });

        return addChildView;
    }

    private class InsertTask extends AsyncTask<Object, Void, Void> {
        private ContentValues values;
        private ProgressDialog progressDialog;
        private SparseArray<View> views;

        public InsertTask() {
            this.views = new SparseArray<View>();
        }

        public InsertTask addView(View view) {
            this.views.append(view.getId(), view);
            return this;
        }

        public InsertTask addView(int id) {
            this.views.append(id, getView().findViewById(id));
            return this;
        }

        @Override
        protected void onPreExecute() {

            String name = ((EditText) this.views.get(R.id.et_pName)).getText().toString();
            String childName = ((TextView) this.views.get(R.id.et_fName)).getText().toString() + " " + ((TextView) this.views.get(R.id.et_lName)).getText().toString();
            String phone = ((TextView) this.views.get(R.id.et_phNumber)).getText().toString();
            String birthday = ((TextView) this.views.get(R.id.et_bday)).getText().toString();

            values = new ContentValues();
            values.put(LionContract.Lions.COL_NAME, name);
            values.put(LionContract.Lions.COL_CHILDNAME, childName);
            values.put(LionContract.Lions.COL_PHONE, phone);
            values.put(LionContract.Lions.COL_BIRTHDAY, birthday);

            this.progressDialog = new ProgressDialog(getActivity());
            this.progressDialog.setMessage("Inserting...");
            this.progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Object... params) {
            Uri uriInsert = Uri.parse(LionProvider.CONTENT_URI + "/lions");
            getActivity().getApplicationContext().getContentResolver().insert(uriInsert, values);
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            if (this.progressDialog != null)
                this.progressDialog.dismiss();

            Toast.makeText(getActivity().getApplicationContext(), "Done adding", Toast.LENGTH_SHORT).show();

            super.onPostExecute(o);
        }
    }
}
