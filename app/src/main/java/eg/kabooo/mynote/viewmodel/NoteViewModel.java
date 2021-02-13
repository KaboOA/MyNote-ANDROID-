package eg.kabooo.mynote.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import eg.kabooo.mynote.pojo.Note;
import eg.kabooo.mynote.repo.NoteRepo;

public class NoteViewModel extends ViewModel {

    private LiveData<List<Note>> listLiveData;
    private LiveData<Note> liveData;
    private NoteRepo repo;

    @ViewModelInject
    public NoteViewModel(NoteRepo repo) {
        this.repo = repo;
    }

    public LiveData<List<Note>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<Note> getLiveData() {
        return liveData;
    }

    public void insert(Note note) {
        repo.insert(note);
    }

    public void getAllNotes() {
        listLiveData = repo.getAllNotes();
    }

    public void deleteAllNotes() {
        repo.deleteAllNotes();
    }

    public void getNote(int id) {
        liveData = repo.getNote(id);
    }

    public void deleteNote(int id) {
        repo.deleteNote(id);
    }

    public void updateNote(Note note) {
        repo.updateNote(note);
    }

    public void getSearchedNote(String noteTitle) {
        listLiveData = repo.getSearchedNote(noteTitle);
    }

}
