package eg.kabooo.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eg.kabooo.mynote.R;
import eg.kabooo.mynote.pojo.Note;
import eg.kabooo.mynote.viewmodel.NoteViewModel;


public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.MyViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private List<Note> Notes;
    private Context context;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(int position);
    }

    @Inject
    public NoteRecyclerAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false), onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleTextView.setText(Notes.get(position).getNoteTitle());
        holder.bodyTextView.setText(Notes.get(position).getNoteBody());
    }

    @Override
    public int getItemCount() {
        return (Notes == null) ? 0 : Notes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, bodyTextView;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.OnItemLongClick(position);
                    }
                }
                return true;
            });
        }
    }

    public void setList(List<Note> Notes) {
        this.Notes = Notes;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        onItemLongClickListener = itemLongClickListener;
    }

    public void swipeToDelete(NoteViewModel noteViewModel, RecyclerView recyclerView) {

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                noteViewModel.deleteNote(Notes.get(position).getNoteId());
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    public int getNoteID(int position) {
        return Notes.get(position).getNoteId();
    }
}