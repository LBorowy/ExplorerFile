package pl.lborowy.nextapp.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.lborowy.nextapp.R;



public class FileViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fileItem_icon)
    public ImageView icon;

    @BindView(R.id.fileItem_nameText)
    public TextView nameText;

    @BindView(R.id.fileItem_fileSizeText)
    public TextView fileSizeText;

    public FileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
