package eg.kabooo.mynote.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eg.kabooo.mynote.pojo.Note;

@Dao
public interface NoteDao {

    @Insert
    void Insert(Note note);

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM note_table")
    void DeleteAllNotes();

    @Query("SELECT  * FROM note_table WHERE noteId=:id")
    LiveData<Note> getNote(int id);

    @Query("Delete FROM note_table WHERE noteId=:id")
    void deleteNote(int id);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM note_table WHERE noteTitle LIKE :searchedText OR noteBody LIKE :searchedText")
    LiveData<List<Note>> getSearchedNote(String searchedText);

}
