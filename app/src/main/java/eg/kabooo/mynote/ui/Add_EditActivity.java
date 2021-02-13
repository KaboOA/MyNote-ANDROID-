package eg.kabooo.mynote.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;
import eg.kabooo.mynote.R;
import eg.kabooo.mynote.databinding.ActivityAddEditBinding;
import eg.kabooo.mynote.pojo.Note;
import eg.kabooo.mynote.viewmodel.NoteViewModel;

@AndroidEntryPoint
public class Add_EditActivity extends AppCompatActivity {

    Intent intent;

    NoteViewModel viewModel;

    ActivityAddEditBinding b;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddEditBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        intent = getIntent();
        id = intent.getIntExtra("noteId", 0);
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        if (id != 0) {
            viewModel.getNote(id);
            viewModel.getLiveData().observe(this, note -> {
                b.titleEditText.setText(note.getNoteTitle());
                b.bodyEditText.setText(note.getNoteBody());
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.trueSign) {
            if (b.titleEditText.getEditableText().toString().equals("")) {
                b.titleEditText.setError("Enter Your Title");
            } else if (b.bodyEditText.getEditableText().toString().equals("")) {
                b.bodyEditText.setError("Enter Your Body");
            } else {
                if (id == 0) {
                    alertDialog(true);
                } else {
                    alertDialog(false);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertDialog(boolean isAdd) {
        new AlertDialog.Builder(this)
                .setTitle("Alert Message")
                .setMessage("Save And Exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (isAdd) {
                        viewModel.insert(new Note(b.titleEditText.getEditableText().toString(), b.bodyEditText.getEditableText().toString()));
                    } else {
                        Note note = new Note(b.titleEditText.getEditableText().toString(), b.bodyEditText.getEditableText().toString());
                        note.setNoteId(id);
                        viewModel.updateNote(note);
                    }
                    finish();
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }

}