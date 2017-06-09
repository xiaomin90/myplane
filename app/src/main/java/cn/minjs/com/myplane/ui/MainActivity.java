package cn.minjs.com.myplane.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.minjs.com.myplane.R;
import cn.minjs.com.myplane.uitls.PlaneLog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    PlaneLog.d(TAG," on drawerClosed ");
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    PlaneLog.d(TAG,"on drawerOpened");
                }
        };
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState != null) {
            mMainFragment =  (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState,"MainFragment");
        } else {
            mMainFragment = MainFragment.newInstance();
        }

        if(!mMainFragment.isAdded()) {
            this.getSupportFragmentManager().beginTransaction()
                                            .add(R.id.layout_fragment,mMainFragment)
                                            .commit();
        }


    }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this,toolbar);
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.toobar_menu, popupMenu.getMenu());
            //popupMenu.inflate(R.menu.toobar_menu);  API 14 可以采用这种方式
            popupMenu.setGravity(Gravity.END);
            popupMenu.show();
            //设置item的点击事件
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.add_item:
                            Toast.makeText(MainActivity.this,"Add button clicked",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.remo_item:
                            invalidateOptionsMenu();
                            Toast.makeText(MainActivity.this,"Remove button clicked",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.more_item:
                            Toast.makeText(MainActivity.this,"More button clicked",Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    return  true;
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle("首页");
                break;
            case R.id.nav_bookmarks:
                toolbar.setTitle("收藏");
                break;
            case R.id.nav_change_theme:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_about:
                break;
            default:
                break;
        }
        return true;
    }
}
