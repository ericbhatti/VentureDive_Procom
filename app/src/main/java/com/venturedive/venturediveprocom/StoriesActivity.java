package com.venturedive.venturediveprocom;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoriesActivity extends AppCompatActivity {

    private static final String TAG = StoriesActivity.class.getSimpleName();
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    TextInputEditText editTextStory;
    Button buttonPost;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseRecyclerAdapter<Story, StoryViewHolder> mFirebaseAdapter;
    FirebaseUser firebaseUser;
    private static final String CHILD = "stories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        getSupportActionBar().setTitle("Stories");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        buttonPost = (Button) findViewById(R.id.buttonPost);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        editTextStory = (TextInputEditText) findViewById(R.id.editTextStory);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Story story = new Story(firebaseUser.getDisplayName(), editTextStory.getText().toString(), firebaseUser
                        .getPhotoUrl().toString());
                databaseReference.child(CHILD).push().setValue(story);
                editTextStory.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextStory.getWindowToken(), InputMethodManager
                        .HIDE_IMPLICIT_ONLY);
            }
        });


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Story, StoryViewHolder>(Story.class, R.layout.list_item_story,
                StoryViewHolder.class, databaseReference.child(CHILD)) {
            @Override
            protected void populateViewHolder(StoryViewHolder viewHolder, Story story, int position) {
                Log.e(TAG, "" + getItemCount());
                if (story != null) {
                    viewHolder.textViewName.setText(story.getName());
                    viewHolder.textViewStory.setText(story.getStory());
                    Glide.with(getApplicationContext()).load(story.getImageURL()).into(viewHolder.imageViewPhoto);
                }
            }
        };
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mMessageRecyclerView.smoothScrollToPosition(positionStart);
            }
        });
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot.child(CHILD)
                        .getChildrenCount() + "]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
