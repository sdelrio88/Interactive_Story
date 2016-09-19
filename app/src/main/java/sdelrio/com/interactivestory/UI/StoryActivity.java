package sdelrio.com.interactivestory.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sdelrio.com.interactivestory.Model.Choice;
import sdelrio.com.interactivestory.Model.Page;
import sdelrio.com.interactivestory.Model.Story;
import sdelrio.com.interactivestory.R;

public class StoryActivity extends AppCompatActivity {

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;

    public static final String TAG = StoryActivity.class.getSimpleName();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_story);

            mImageView = (ImageView) findViewById(R.id.storyImageView);
            mTextView = (TextView) findViewById(R.id.storyTextView);
            mChoice1 = (Button) findViewById(R.id.choiceButton1);
            mChoice2 = (Button) findViewById(R.id.choiceButton2);


            Intent intent = getIntent();

            mName = intent.getStringExtra(getString(R.string.key_name));

            if (mName == null) {
                mName = "Friend";
            }
            Log.d(TAG, mName);

            loadPage(0);
    }


    private void loadPage(int choice){
            final Page page = mStory.getPage(choice);

            //setting the image in the Page
            Drawable drawable = getResources().getDrawable(page.getImageId());
            mImageView.setImageDrawable(drawable);

            //setting the text in the Page
            String pageText = page.getText();
            // Add the name if placeholder included. Won't add if no placeholder.
            pageText = String.format(pageText, mName);
            mTextView.setText(pageText);

            if (page.isFinal()){
                mChoice1.setVisibility(View.INVISIBLE);
                mChoice2.setText("PLAY AGAIN");
                mChoice2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
            else {
                //Setting the two choices in the Page
                mChoice1.setText(page.getChoice1().getText());
                mChoice2.setText(page.getChoice2().getText());

                //Add onclick listeners for both buttons.  Run loadPage() again for the new choice
                mChoice1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int nextPage = page.getChoice1().getNextPage();
                        loadPage(nextPage);
                    }
                });

                mChoice2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int nextPage = page.getChoice2().getNextPage();
                        loadPage(nextPage);
                    }
                });
            }
    }
}
