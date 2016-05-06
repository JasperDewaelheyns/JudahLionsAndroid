package com.judah.jasperdewaelheyns.lionsapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LionListFragment lionListFragment;
    private AddChildFragment addChildFragment;
    private static final int CONTEXT_MENU = 300;
    private static final int ITEM_TEXT = 2;
    private static final int ITEM_CALL = 1;

    private FragmentManager fragmentManager;

    private ListView listView;
    private SimpleCursorAdapter adapter;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lionListFragment = new LionListFragment();
        addChildFragment = new AddChildFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFragment, lionListFragment).commit();

        /*ContentResolver cr = getContentResolver();
        String[] from = {LionContract.Lions.COL_NAME, LionContract.Lions.COL_CHILDNAME};
        int[] to = {R.id.tv_parentName, R.id.tv_childName};
        adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.lion_row, null, from, to, 0);*/


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                checkOption(menuItem.getTitle().toString());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Cursor cursor = (Cursor) adapter.getItem(info.position);

        menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(LionContract.Lions.COL_NAME)));
        menu.add(CONTEXT_MENU, ITEM_TEXT, 2, "Text");
        menu.add(CONTEXT_MENU, ITEM_CALL, 1, "Call");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = info.id;
        Cursor cursor = (Cursor) adapter.getItem(info.position);
        String phoneNumber = cursor.getString(cursor.getColumnIndex(LionContract.Lions.COL_PHONE));
        switch (item.getItemId()) {
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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void checkOption(String chosenOption) {
        if (chosenOption.equals(getResources().getString(R.string.view_list))) {
           if (!lionListFragment.isVisible()) {
               FragmentTransaction viewTransaction = fragmentManager.beginTransaction();
               viewTransaction.replace(R.id.mainFragment, lionListFragment);
               viewTransaction.commit();
           }
        } else if (chosenOption.equals(getResources().getString(R.string.add_lion))) {
            if (!addChildFragment.isVisible()) {
                FragmentTransaction addTransaction = fragmentManager.beginTransaction();
                addTransaction.replace(R.id.mainFragment, addChildFragment);
                addTransaction.commit();
            }
        }
    }
}
