package pl.lborowy.nextapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.lborowy.nextapp.R;


public class OpenFileFragment extends Fragment {
    private static final String ARG_PATH_PARAM = "param1";
    private static final String ARG_NEW_FILE_PARAM = "param2";
    private String currentFilePath;

    private InteractionListener mListener;

    @BindView(R.id.openFileFragment_outputText)
    EditText outputText;
    private boolean isNewFile;

    public OpenFileFragment() {
        // Required empty public constructor
    }

    public static OpenFileFragment newInstance(String newFilePath, boolean isNewFile) {
        //// TODO: 27.07.2017 isNewFile == false?! edycja pliku?
        OpenFileFragment fragment = new OpenFileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH_PARAM, newFilePath);
        args.putBoolean(ARG_NEW_FILE_PARAM, isNewFile);
        fragment.setArguments(args);
        return fragment;
    }

    public static OpenFileFragment newInstance(String filePath) {
        OpenFileFragment fragment = new OpenFileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH_PARAM, filePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentFilePath = getArguments().getString(ARG_PATH_PARAM);
            isNewFile = getArguments().getBoolean(ARG_NEW_FILE_PARAM, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_file, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        outputText.setText(currentFilePath);
        if (!isNewFile)
            openFile();
        else
            outputText.setText(null);
    }

    private void openFile() {
//        File file = new File(currentFilePath);
//        file.exists();

        //TODO sprawdzic czy wogole mozemy czytac plik
        try {
            FileInputStream fis = new FileInputStream(currentFilePath);//FileNotFoundException
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            long maxBytesSize = 1024 * 5;
            long currentByteSize = 0;
            int sizeOfGetData;
            while ((sizeOfGetData = fis.read(buffer)) != -1 // END_OF_FILE = -1
                    && currentByteSize < maxBytesSize) {
                String smallInput = new String(buffer, 0, sizeOfGetData);
                Log.d("READING", String.format("smallInput = (%s)", smallInput));
                fileContent.append(smallInput);
                currentByteSize += buffer.length;
            }

            if (currentByteSize >= maxBytesSize)
                fileContent.append("TOO MUCH DATA!");

            outputText.setText(fileContent.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //plik nie istnieje
        } catch (IOException e) {
            e.printStackTrace();
            //plik jest uzywany lub nie mam dostepu
        }
    }

    @OnClick(R.id.openFileFragment_saveButton)
    public void clickSaveButton() {
        File file = new File(currentFilePath);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String dataToSave = outputText.getText().toString();
            osw.write(dataToSave);
            osw.flush();
            osw.close();
            showToast("DONE!");
            //// TODO: 27.07.2017 interakcja z activity - zamknij ten fragment i odswiez liste
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToast("FAIL! " + e.getMessage());
            //// TODO: 27.07.2017 interakcja z activity  - zamknij fragment, pokaz blad
        } catch (IOException e) {
            e.printStackTrace();
            showToast("FAIL! " + e.getMessage());
        }
    }

    private void showToast(String s) {
        Log.d("TOAST", "Toast:" + s);
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface InteractionListener {
        void doNothing();
    }
}
