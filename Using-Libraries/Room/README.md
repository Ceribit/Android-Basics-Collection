Steps:

1. Add Gradle Dependency
    - In this case:
    
            def room_version = "1.1.1"
        
            implementation "android.arch.persistence.room:runtime:$room_version"
            annotationProcessor "android.arch.persistence.room:compiler:$room_version" // use kapt for Kotlin
        
            // optional - RxJava support for Room
            implementation "android.arch.persistence.room:rxjava2:$room_version"
        
            // optional - Guava support for Room, including Optional and ListenableFuture
            implementation "android.arch.persistence.room:guava:$room_version"
        
            // Test helpers
            testImplementation "android.arch.persistence.room:testing:$room_version"
            
    - Dependencies described by CodeLabs for Lifecycle Components
            
            // Lifecycle components
            implementation "android.arch.lifecycle:extensions:$rootProject.archLifecycleVersion"
            annotationProcessor "android.arch.lifecycle:compiler:$rootProject.archLifecycleVersion"
                      
2. Create Entity 
    - e.g.

            @Entity(tableName = "word_table")
            public class Word {
            
               @PrimaryKey
               @NonNull
               @ColumnInfo(name = "word")
               private String mWord;
            
               public Word(String word) {this.mWord = word;}
            
               public String getWord(){return this.mWord;}
            }
3. Create a DAO (Data Access Object)
        
        @Dao
        public interface WordDao {
        
           @Insert
           void insert(Word word);
        
           @Query("DELETE FROM word_table")
           void deleteAll();
        
           @Query("SELECT * from word_table ORDER BY word ASC")
           List<Word> getAllWords();
        }     
4. You can attach a LiveData class to observe changes in the data and update the UI

5. Create the Room Database in an abstract class
    - e.g. 
    
        @Database(entities = {Word.class}, version = 1)
        public abstract class WordRoomDatabase extends RoomDatabase {
        
           public abstract WordDao wordDao();
        
           private static volatile WordRoomDatabase INSTANCE;
        
           static WordRoomDatabase getDatabase(final Context context) {
                if (INSTANCE == null) {
                    synchronized (WordRoomDatabase.class) {
                        if (INSTANCE == null) {
                            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WordRoomDatabase.class, "word_database")
                                    .build();
                        }
                    }
                }
                return INSTANCE;
            }
        }
        
6. Create a repository

- Not necessary, but it is good practice to do this
- A repository manages query threads and allows you to use multiple backends

        public class WordRepository {
        
           private WordDao mWordDao;
           private LiveData<List<Word>> mAllWords;
        
           WordRepository(Application application) {
               WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
               mWordDao = db.wordDao();
               mAllWords = mWordDao.getAllWords();
           }
        
           LiveData<List<Word>> getAllWords() {
               return mAllWords;
           }
        
        
           public void insert (Word word) {
               new insertAsyncTask(mWordDao).execute(word);
           }
        
           private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        
               private WordDao mAsyncTaskDao;
        
               insertAsyncTask(WordDao dao) {
                   mAsyncTaskDao = dao;
               }
        
               @Override
               protected Void doInBackground(final Word... params) {
                   mAsyncTaskDao.insert(params[0]);
                   return null;
               }
           }
        }
         
7. Create the ViewModel

- What is a ViewModel?
  A ViewModel holds your app's UI data in a lifecycle-conscious way. It acts as a communication channel that facilitates information flow between the Repository and UI
  
  e.g. 
  
        public class WordViewModel extends AndroidViewModel {
        
           private WordRepository mRepository;
        
           private LiveData<List<Word>> mAllWords;
        
           public WordViewModel (Application application) {
               super(application);
               mRepository = new WordRepository(application);
               mAllWords = mRepository.getAllWords();
           }
        
           LiveData<List<Word>> getAllWords() { return mAllWords; }
        
           public void insert(Word word) { mRepository.insert(word); }
        }