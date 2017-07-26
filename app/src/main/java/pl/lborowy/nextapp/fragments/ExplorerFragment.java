package pl.lborowy.nextapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.lborowy.nextapp.adapters.FilesAdapter;
import pl.lborowy.nextapp.models.FileItem;
import pl.lborowy.nextapp.R;


public class ExplorerFragment extends Fragment implements FilesAdapter.OnFileItemClicked {
    private static final String ARG_PATH_PARAM = "param1";
    public static final boolean USE_ACTIVITY_TO_NAVIGATE = true;

    @BindView(R.id.explorerFragment_filePathText)
    TextView filePathText;

    @BindView(R.id.explorerFragment_recyclerView)
    RecyclerView recyclerView;

    private String currentFilePath;

    private ExploratorInteractionListener mListener;
    private FilesAdapter filesAdapter;

    public ExplorerFragment() {
        // Required empty public constructor
    }

    public static ExplorerFragment newInstance(String filePath) {
        ExplorerFragment fragment = new ExplorerFragment();
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
        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateFilePath();
        loadFileList();
    }

    private void updateFilePath() {
        filePathText.setText(currentFilePath);
    }

    private void loadFileList() {
        List<FileItem> fileItems = new ArrayList<>();
        File file = new File(currentFilePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File currentFile : files) {
                    FileItem fileItem = new FileItem(currentFile);
                    fileItems.add(fileItem);
                }
        } else
            fileItems.add(new FileItem(file));
        Log.d("PLICZKI", "Ile plikow lub folderow " + fileItems.size());

        fileItems.add(0, new FileItem(file.getParent()));

        filesAdapter = new FilesAdapter(getActivity().getApplicationContext(), fileItems, this);
        recyclerView.setAdapter(filesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExploratorInteractionListener) {
            mListener = (ExploratorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ExploratorInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        filesAdapter = null;
    }

    @Override
    public void onFileItemClicked(FileItem fileItem) {
        if (USE_ACTIVITY_TO_NAVIGATE)
            mListener.onPathClicked(fileItem.getPath());
        else {
            updateFilePath();
            currentFilePath = fileItem.getPath();
            loadFileList();
        }
    }

    public interface ExploratorInteractionListener {
        void onPathClicked(String newFilePath);
    }
}
