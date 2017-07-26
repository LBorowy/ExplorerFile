package pl.lborowy.nextapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
