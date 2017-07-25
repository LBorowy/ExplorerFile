package pl.lborowy.nextapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.lborowy.nextapp.R;
import pl.lborowy.nextapp.models.FileItem;
import pl.lborowy.nextapp.viewHolders.FileViewHolder;



public class FilesAdapter extends RecyclerView.Adapter<FileViewHolder> {

    private List<FileItem> fileItems;
    private final LayoutInflater inflater;

    public FilesAdapter(Context context, List<FileItem> fileItems) { // context dla layoutinflater
        this.fileItems = fileItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.file_item, parent, false);

        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) { // aktualizacja w liscie
        FileItem fileItem = fileItems.get(position);
        holder.nameText.setText(fileItem.getName());

        if (fileItem.isDirectory()) {
            holder.icon.setImageResource(R.drawable.ic_folder_open_black_24dp);
        }
        else {
            holder.icon.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }
}
