package com.judah.jasperdewaelheyns.lionsapplication;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.judah.jasperdewaelheyns.lionsapplication.database.LionContract;
import com.judah.jasperdewaelheyns.lionsapplication.database.LionProvider;

//import android.support.v4.app.ListFragment;


public class LionListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CONTEXT_MENU = 300;
    private static final int ITEM_VIEW_CHILD = 1;
    private static final int ITEM_TEXT = 3;
    private static final int ITEM_CALL = 2;

    private ListView listView;
    private SimpleCursorAdapter adapter;

    public LionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ContentResolver cr = getActivity().getContentResolver();
        String[] from = {LionContract.Lions.COL_NAME, LionContract.Lions.COL_CHILDNAME};
        int[] to = {R.id.tv_parentName, R.id.tv_childName};
        adapter = new SimpleCursorAdapter(getActivity().getApplication().getApplicationContext(),
                R.layout.lion_row, null, from, to, 0);

        getActivity().getSupportLoaderManager().initLoader(DataLoader.ID, null, this);

        listView = (ListView) view.findViewById(R.id.lv_Contacts);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Cursor cursor = (Cursor) adapter.getItem(info.position);

        menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(LionContract.Lions.COL_NAME)));
        menu.add(CONTEXT_MENU, ITEM_VIEW_CHILD, 1, getActivity().getResources().getString(R.string.view_child));
        menu.add(CONTEXT_MENU, ITEM_TEXT, 3, getActivity().getResources().getString(R.string.text_parent));
        menu.add(CONTEXT_MENU, ITEM_CALL, 2, getActivity().getResources().getString(R.string.call_parent));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = info.id;
        Cursor cursor = (Cursor) adapter.getItem(info.position);
        String phoneNumber = cursor.getString(cursor.getColumnIndex(LionContract.Lions.COL_PHONE));
        switch (item.getItemId()) {
            case ITEM_VIEW_CHILD:
                Bundle args = new Bundle();
                args.putInt("CID", (int) id);
                ChildOverviewFragment childOverviewFragment = new ChildOverviewFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                childOverviewFragment.setArguments(args);
                transaction.replace(R.id.mainFragment, childOverviewFragment).addToBackStack(null).commit();
                break;
            case ITEM_TEXT:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + phoneNumber));
                startActivity(sendIntent);
                break;
            case ITEM_CALL:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(LionProvider.CONTENT_URI + "/lions");
        CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(), uri, null, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private class DataLoader implements LoaderManager.LoaderCallbacks<Cursor> {

        public static final int ID = 1;

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

            Uri uri = Uri.parse(LionProvider.CONTENT_URI + "/lions");
            CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(), uri, null, null, null, null);

            return cursorLoader;
        }


        @Override
        public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    }
}
