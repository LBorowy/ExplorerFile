package pl.lborowy.nextapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pl.lborowy.nextapp.fragments.ExplorerFragment;
import pl.lborowy.nextapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ExplorerFragment.ExploratorInteractionListener {

    private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openExplorerFragment(Environment.getRootDirectory().getPath(), false);


    }

    private void openExplorerFragment(String path, boolean canGoBack) {
        int enterAnim = android.R.anim.slide_in_left;
        int exitAnim = android.R.anim.slide_out_right;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim);
        transaction.add(R.id.mainActivity_fragmentContainer, ExplorerFragment.newInstance(path));
//        transaction.addToBackStack(null); // aby moc dac wstecz
        if (canGoBack){
            transaction.add(R.id.mainActivity_fragmentContainer, ExplorerFragment.newInstance(path));
            transaction.addToBackStack(null);
        }
        else {
            transaction.replace(R.id.mainActivity_fragmentContainer, ExplorerFragment.newInstance(path));
        }
        transaction.commit();
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
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
                openExplorerFragment(Environment.getExternalStorageDirectory().getPath(), false);
                return true;

            case R.id.action_goto_root:
                openExplorerFragment(Environment.getRootDirectory().getPath(), false);
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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    showFiles(currentDir);
                } else
                    Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void openSettingsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainActivity_fragmentContainer, new SettingsFragment());
        transaction.addToBackStack(null); // aby moc dac wstecz
        transaction.commit();

    }

    @Override
    public void onPathClicked(String newFilePath) {
        openExplorerFragment(newFilePath, true);
    }


}
