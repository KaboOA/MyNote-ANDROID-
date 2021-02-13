package eg.kabooo.mynote.module;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import eg.kabooo.mynote.database.NoteDao;
import eg.kabooo.mynote.database.NoteDatabase;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    public static NoteDatabase noteDatabaseProvides(Application application) {
        return Room.databaseBuilder(application, NoteDatabase.class, "note_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    public static NoteDao noteDao(NoteDatabase noteDatabase) {
        return noteDatabase.noteDao();
    }

}
