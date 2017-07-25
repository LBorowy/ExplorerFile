package pl.lborowy.nextapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.lborowy.nextapp.R;


public class ExplorerFragment extends Fragment {

    private static final String ARG_PATH_PARAM = "param1";

    @BindView(R.id.explorerFragment_filePathText)
    TextView filePathText;

    @BindView(R.id.explorerFragment_recyclerView)
    RecyclerView recyclerView;

    private String currentFilePath;
    private ExploratorInteractionListener mListener;

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
        if (getArguments() != null) {
            currentFilePath = getArguments().getString(ARG_PATH_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // funkcja z cyklu zycia
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //// TODO: 2017-07-25 show filePath
        filePathText.setText(currentFilePath);
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
    }

    public interface ExploratorInteractionListener {
        // TODO: Update argument type and name
        void onPathClicked(String newFilePath);
    }
}
