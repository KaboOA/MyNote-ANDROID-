package eg.kabooo.mynote.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import eg.kabooo.mynote.pojo.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();


}
