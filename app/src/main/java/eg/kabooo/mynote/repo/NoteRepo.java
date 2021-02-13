package eg.kabooo.mynote.repo;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import eg.kabooo.mynote.database.NoteDao;
import eg.kabooo.mynote.pojo.Note;

public class NoteRepo {

    private NoteDao noteDao;

    @Inject
    public NoteRepo(NoteDao noteDao) {
        this.noteDao = noteDao;
    }


    public void insert(Note note) {
        noteDao.Insert(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public void deleteAllNotes() {
        noteDao.DeleteAllNotes();
    }

    public LiveData<Note> getNote(int id) {
        return noteDao.getNote(id);
    }

    public void deleteNote(int id) {
        noteDao.deleteNote(id);
    }

    public void updateNote(Note note) {
        noteDao.updateNote(note);
    }

    public LiveData<List<Note>> getSearchedNote(String noteTitle) {
        return noteDao.getSearchedNote(noteTitle);
    }

}
