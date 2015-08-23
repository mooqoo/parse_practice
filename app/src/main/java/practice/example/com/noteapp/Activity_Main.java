package practice.example.com.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import practice.example.com.noteapp.Model.Note;


public class Activity_Main extends AppCompatActivity {
    public static final String TAG = "Main";

    //View
    private ListView lv_post;

    //Variables
    private List<Note> posts;
    ArrayAdapter<Note> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this must be called right after super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        initVariables();

        putParseObject();

    }

    public void initVariables() {
        posts = new ArrayList<>();
        adapter = new ArrayAdapter<Note>(this, R.layout.list_item_layout, posts);
        lv_post.setAdapter(adapter);//setListAdapter(adapter);
        lv_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Note note = posts.get(position);
                Intent intent = new Intent(Activity_Main.this, Activity_Edit.class);
                intent.putExtra(Note.EXTRA_ID, note.getId());
                intent.putExtra(Note.EXTRA_TITLE, note.getTitle());
                intent.putExtra(Note.EXTRA_CONTENT, note.getContent());
                startActivity(intent);
            }
        });
        //refreshPostList();
    }



    /**
     *  Get the post list from Parse and update it to the ArrayList
     */
    private void refreshPostList() {
        //this will display a progress spinner control
        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    posts.clear();
                    for (ParseObject post : postList) {
                        Note note = new Note(post.getObjectId(), post.getString("title"), post.getString("content"));
                        posts.add(note);
                    }
                    Log.i(TAG,"refreshPostList: posts=" + posts.toString());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }

    public void initViews() {
        lv_post = (ListView) findViewById(R.id.lv_post);
    }

    public void putParseObject() {
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Setting
        if (id == R.id.action_settings) {
            Log.i(TAG,"Menu: action setting clicked!");
            return true;
        }

        //Add
        else if(id == R.id.action_add) {
            Log.i(TAG,"Menu: action add clicked!");
            goto_AcitivityEdit();
            return true;
        }

        //Refresh
        else if(id == R.id.action_refresh) {
            Log.i(TAG,"Menu: action refresh clicked!");
            refreshPostList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goto_AcitivityEdit() {
        Intent intent = new Intent(this, Activity_Edit.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPostList();
    }
}
