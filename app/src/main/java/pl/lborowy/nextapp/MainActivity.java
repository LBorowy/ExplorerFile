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

import pl.lborowy.nextapp.fragments.ExplorerFragment;
import pl.lborowy.nextapp.fragments.OpenFileFragment;
import pl.lborowy.nextapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ExplorerFragment.ExploratorInteractionListener, OpenFileFragment.InteractionListener {

    private static final int ENTER_ANIM = android.R.anim.slide_in_left;
    private static final int EXIT_ANIM = android.R.anim.slide_out_right;
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
        Fragment fragment = ExplorerFragment.newInstance(path);

        openFragment(fragment, canGoBack);
    }

    private void openFragment(Fragment fragment, boolean canGoBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(ENTER_ANIM, EXIT_ANIM, ENTER_ANIM, EXIT_ANIM);
//        transaction.addToBackStack(null); // aby moc dac wstecz
        if (canGoBack) {
            transaction.add(R.id.mainActivity_fragmentContainer, fragment);
            transaction.addToBackStack(null);
        } else {
            transaction.replace(R.id.mainActivity_fragmentContainer, fragment);
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
    public void onDirectoryClicked(String newPath) {
        openExplorerFragment(newPath, true);
    }

    @Override
    public void onFileClicked(String filePath) {
        //// TODO: 2017-07-26 open file
        openFragment(OpenFileFragment.newInstance(filePath), true);
    }

    @Override
    public void onBackClicked() {
        getSupportFragmentManager().popBackStack(); // funkcja do cofania
    }


    @Override
    public void doNothing() {
        // nothing
    }
}
