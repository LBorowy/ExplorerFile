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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.lborowy.nextapp.R;


public class OpenFileFragment extends Fragment {
    private static final String ARG_PATH_PARAM = "param1";
    private String currentFilePath;

    private InteractionListener mListener;

    @BindView(R.id.openFileFragment_outputText)
    EditText outputText;

    public OpenFileFragment() {
        // Required empty public constructor
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
        if (getArguments() != null)
            currentFilePath = getArguments().getString(ARG_PATH_PARAM);
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
        openFile();
    }

    private void openFile() {
//        File file = new File(currentFilePath);
//        file.exists();

        try {
            FileInputStream fis = new FileInputStream(currentFilePath); // FileNotFoundException
            // bufor na dane
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            long maxByteSize = 1024 * 50;
            long currentByteSize = 0;
            int sizeOfGetData;

            while ((sizeOfGetData = fis.read(buffer)) != -1 && currentByteSize < maxByteSize) { // END_OF_FILE = -1
                String smallInput = new String(buffer, 0, sizeOfGetData);
                Log.d("READING", String.format("smallInput = (%s)", smallInput));
                fileContent.append(smallInput);
                currentByteSize += buffer.length;
            }

            if (currentByteSize > maxByteSize) {
                fileContent.append("TOO MUCH DATA");
            }

            outputText.setText(fileContent.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // plik nie istnieje
        } catch (IOException e) {
            e.printStackTrace();
            // plik jest uzywany lub nie mam dostepu
        }
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
