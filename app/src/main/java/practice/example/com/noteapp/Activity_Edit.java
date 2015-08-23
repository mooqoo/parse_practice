package practice.example.com.noteapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import practice.example.com.noteapp.Model.Note;


public class Activity_Edit extends ActionBarActivity {
    public static final String TAG = "A_Edit";

    //UI
    private EditText et_content, et_title;
    private Button btn_save;

    //Variables
    private Note note;
    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        //load note from intent
        Intent intent = this.getIntent();
        if (intent.getExtras() != null) {
            note = new Note(intent.getStringExtra(Note.EXTRA_ID), intent.getStringExtra(Note.EXTRA_TITLE), intent.getStringExtra(Note.EXTRA_CONTENT));
            et_title.setText(note.getTitle());
            et_content.setText(note.getContent());
        }
    }

    private void initView() {
        et_content = (EditText) findViewById(R.id.et_noteContent);
        et_title = (EditText) findViewById(R.id.et_noteTitle);
        btn_save = (Button) findViewById(R.id.btn_saveNote);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        title = et_title.getText().toString();
        content = et_content.getText().toString();

        title = title.trim();
        content = content.trim();

        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!title.isEmpty()) {
            // Check if post is being created or edited
            if (note == null) {
                // create new post

                ParseObject post = new ParseObject("Post");
                post.put("title", title);
                post.put("content", content);
                post.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Saved successfully.
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });

            }
            else {
                // update post
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

                // Retrieve the object by id
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            post.put("title", title);
                            post.put("content", content);
                            post.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // Saved successfully.
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        // The save failed.
                                        Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        else if (title.isEmpty() && !content.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Edit.this);
            builder.setMessage(R.string.edit_error_message)
                    .setTitle(R.string.edit_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
