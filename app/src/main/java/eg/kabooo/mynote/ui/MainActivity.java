package eg.kabooo.mynote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import eg.kabooo.mynote.R;
import eg.kabooo.mynote.adapter.NoteRecyclerAdapter;
import eg.kabooo.mynote.databinding.ActivityMainBinding;
import eg.kabooo.mynote.pojo.Note;
import eg.kabooo.mynote.viewmodel.NoteViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ActivityMainBinding b;

    @Inject
    NoteRecyclerAdapter adapter;

    NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        b.recyclerView.setAdapter(adapter);
        adapter.swipeToDelete(viewModel, b.recyclerView);
        adapter.setOnItemClickListener(this::intent);

        viewModel.getAllNotes();
        viewModel.getListLiveData().observe(this, notes -> adapter.setList(notes));
        b.floatingActionButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Add_EditActivity.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addDummyData) {
            viewModel.insert(new Note("Ahmed", "Hello Ahmed"));
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.deleteAllData) {
            viewModel.deleteAllNotes();
            Toast.makeText(this, "Items Deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void intent(int position) {
        Intent intent = new Intent(MainActivity.this, Add_EditActivity.class);
        intent.putExtra("noteId", adapter.getNoteID(position));
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null) {
            searchDatabase(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null) {
            searchDatabase(newText);
        }
        return true;
    }

    private void searchDatabase(String query) {
        String searchQuery = "%" + query + "%";
        viewModel.getSearchedNote(searchQuery);
        viewModel.getListLiveData().observe(MainActivity.this, notes -> adapter.setList(notes));
    }

}