package com.vainsolutions.myfirstparseproject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView arrayLv;
    private List<Note> posts;
    TextView titleTxv;
    Button submitBtn, refreshListBt;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Parse.initialize(this, Constant.APPLICATION_ID, Constant.CLIENT_KEY);

        arrayLv = (ListView) findViewById(R.id.arrayLv);
        titleTxv = (TextView) findViewById(R.id.titleTxv);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        refreshListBt = (Button) findViewById(R.id.refreshListBt);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                ParseObject addContent = new ParseObject("Post");
                addContent.put("title", titleTxv.getText().toString());
//                addContent.saveInBackground();

                addContent.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        hideDialog();
                    }
                });
            }
        });

        refreshListBt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                refreshPostList();
            }
        });

        refreshPostList();
    }

    public void refreshPostList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter

                    ArrayList<String> arrayPost = new ArrayList<String>();

                    for (ParseObject post : postList) {

                        arrayPost.add(post.getString("title"));

                    }
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayPost);

                    arrayLv.setAdapter(adapter);

                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }


    public void showDialog() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        // Set progressdialog title
        mProgressDialog.setTitle("Loading");
        // Set progressdialog message
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    public void hideDialog() {
        // Close the progressdialog
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        mProgressDialog.setCancelable(true);
//        mProgressDialog.cancel();
    }


}
