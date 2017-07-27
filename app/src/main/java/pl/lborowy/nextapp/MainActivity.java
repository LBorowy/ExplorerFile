package pl.lborowy.nextapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import pl.lborowy.nextapp.fragments.ExplorerFragment;
import pl.lborowy.nextapp.fragments.OpenFileFragment;
import pl.lborowy.nextapp.fragments.SettingsFragment;

// tylko w externalu

public class MainActivity extends BaseActivity implements ExplorerFragment.ExploratorInteractionListener,
        OpenFileFragment.InteractionListener{
    private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1500;
    private String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        openExplorerFragment(Environment.getRootDirectory().getPath(), false);
    }

    private void openExplorerFragment(String path, boolean canGoBack) {
        Fragment fragment = ExplorerFragment.newInstance(path);
        openFragment(fragment, canGoBack);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettingsFragment();
                return true;

            case R.id.action_goto_external:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
                return true;


            case R.id.action_goto_root:
                openExplorerFragment(Environment.getRootDirectory().getPath(), false);
                return true;

            case R.id.action_add_file:
                openFragment(OpenFileFragment.newInstance(currentPath + File.separator + "filename.txt", true), true);
                return true;

            case R.id.action_exit:
                finish();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    openExplorerFragment(Environment.getExternalStorageDirectory().getPath(), false);
                else
                    Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void openSettingsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainActivity_fragmentContainer, new SettingsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDirectoryClicked(String newPath) {
        openExplorerFragment(newPath, true);
        currentPath = newPath;
    }

    @Override
    public void onFileClicked(String filePath) {
        openFragment(OpenFileFragment.newInstance(filePath), true);
    }

    @Override
    public void onBackClicked() {
        //// TODO: 26.07.2017 get fragment and get info
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doNothing() {
        //lol
    }
}