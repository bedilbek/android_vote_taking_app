package example.bedilbek.homework3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class VoteLogActivity extends AppCompatActivity {
    private LinearLayout voteLogActivityLayout;
    private TextView voteLog;
    private ArrayList<Vote> voteLogs;
    private boolean erase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_log);
        initWidgets();
        Intent intent = getIntent();
        voteLogs = intent.getParcelableArrayListExtra("voteLogs");
        setVoteLogText();
    }

    private void initWidgets() {
        voteLogActivityLayout = findViewById(R.id.vote_log_activity_layout);
        voteLogActivityLayout.setPadding(
                ViewFactory.convertDpsToPixels(this, 5),
                0,
                ViewFactory.convertDpsToPixels(this, 5),
                ViewFactory.convertDpsToPixels(this, 5));

        Button eraseButton = ViewFactory.createButton(this, "Clear Votes", 5,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, Gravity.RIGHT | Gravity.TOP);
        eraseButton.setBackground(getResources().getDrawable(R.drawable.widget_shape));

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erase = true;
                voteLogs.clear();
                setVoteLogText();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("erase", erase);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        voteLogActivityLayout.addView(eraseButton);

        TextView textView = ViewFactory.createTextView(this, "Vote List", 10,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                -1);
        voteLogActivityLayout.addView(textView);


        voteLog = ViewFactory.createTextView(
                this,
                "",
                4,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                -1);
        voteLog = ViewFactory.setMargins(voteLog, 15, 0, 0, 0);
        voteLogActivityLayout.addView(voteLog);
    }

    private void setVoteLogText() {
        StringBuilder logging = new StringBuilder();
        for (Vote item : voteLogs) {
            logging.append("-> ").append(item.toString()).append("\n");
            logging.append("\n");
        }

        voteLog.setText(logging.toString());
    }
}