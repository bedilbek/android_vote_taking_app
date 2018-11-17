package example.bedilbek.homework3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mainLayout;
    private EditText firstname;
    private EditText lastname;
    private RadioGroup agreement;
    private RadioButton agree;
    boolean erase = false;
    private RadioButton disagree;
    private Spinner drinks;
    private Spinner foods;
    private TextView foods_text;
    private TextView drinks_text;
    boolean agreementBool;
    private ArrayList<String> order_drinks;
    private ArrayList<String> order_foods;
    private ArrayList<Vote> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        order_foods = readStringFromFile(R.raw.foods);
        order_drinks = readStringFromFile(R.raw.liquids);
        logs = readVotesFromFile();
        initWidgets();
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeVotesToFile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 345 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                erase = data.getBooleanExtra("erase", false);
            }
            if (erase) {
                logs.clear();
            }
        }
    }

    private void initWidgets() {
        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setPadding(convertDpsToPixels(5), convertDpsToPixels(5), convertDpsToPixels(5), convertDpsToPixels(5));
        mainLayout.addView(ViewFactory.createTextView(this, "Will you come to the party? If you come, what type of food or drink you want?", 5));


        LinearLayout linearLayout = ViewFactory.createLinearLayout(this, ConstraintLayout.LayoutParams.HORIZONTAL);
        linearLayout = ViewFactory.setMargins(linearLayout, 20, 0, 0, 0);

        firstname = ViewFactory.createEditText(this, "First Name", 5);
        firstname.setBackground(getResources().getDrawable(R.drawable.field_shape));
        ViewFactory.setMargins(firstname, 0, 0, 0, 5);
        lastname = ViewFactory.createEditText(this, "Last Name", 5);
        lastname.setBackground(getResources().getDrawable(R.drawable.field_shape));

        linearLayout.addView(firstname);
        linearLayout.addView(lastname);

        mainLayout.addView(linearLayout);


        agreement = ViewFactory.createRadioGroup(this);
        agreement = ViewFactory.setMargins(agreement, 20, 0, 0, 0);

        agree = ViewFactory.createRadioButton(this, "Agree");

        disagree = ViewFactory.createRadioButton(this, "Disagree");
        disagree = ViewFactory.setMargins(disagree, 0, 0, 15, 0);

        agreement.addView(agree);
        agreement.addView(disagree);

        mainLayout.addView(agreement);


        drinks_text = ViewFactory.createTextView(this, "Drinks", 4,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                -1);

        drinks_text = ViewFactory.setMargins(drinks_text, 20, 0, 0, 0);

        mainLayout.addView(drinks_text);

        LinearLayout drinkSpinnerLayout = ViewFactory.createLinearLayout(this, ConstraintLayout.LayoutParams.HORIZONTAL);
        drinkSpinnerLayout.setBackground(getResources().getDrawable(R.drawable.field_shape));
        drinks = ViewFactory.createSpinner(this);
        drinks.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, order_drinks));
        drinkSpinnerLayout.addView(drinks);
        mainLayout.addView(drinkSpinnerLayout);


        foods_text = ViewFactory.createTextView(this, "Foods", 4,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                -1);

        foods_text = ViewFactory.setMargins(foods_text, 20, 0, 0, 0);

        mainLayout.addView(foods_text);

        LinearLayout foodSpinnerLayout = ViewFactory.createLinearLayout(this, ConstraintLayout.LayoutParams.HORIZONTAL);
        foodSpinnerLayout.setBackground(getResources().getDrawable(R.drawable.field_shape));
        foods = ViewFactory.createSpinner(this);
        foods.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, order_foods));
        foodSpinnerLayout.addView(foods);
        mainLayout.addView(foodSpinnerLayout);


        Button voteButton = ViewFactory.createButton(this, "VOTE", 4);
        voteButton = ViewFactory.setMargins(voteButton, 20, 0, 0, 0);
        voteButton.setWidth(convertDpsToPixels(210));
        voteButton.setBackground(getResources().getDrawable(R.drawable.button_shape));
        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstname.getText().toString().equals("") || lastname.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "FirstName and LastName should be entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                logs.add(new Vote(
                        firstname.getText().toString(),
                        lastname.getText().toString(),
                        agreementBool,
                        foods.getSelectedItem().toString(),
                        drinks.getSelectedItem().toString())
                );

                Toast.makeText(getApplicationContext(), "Voted", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), logs.get(logs.size() - 1).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(voteButton);


        Button checkVotesButton = ViewFactory.createButton(this, "CHECK VOTES", 4);
        checkVotesButton.setWidth(convertDpsToPixels(210));
        checkVotesButton = ViewFactory.setMargins(checkVotesButton, 20, 0, 0, 0);
        checkVotesButton.setBackground(getResources().getDrawable(R.drawable.button_shape));
        checkVotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voteLogIntent = new Intent(getApplicationContext(), VoteLogActivity.class);
                voteLogIntent.putParcelableArrayListExtra("voteLogs", logs);
                startActivityForResult(voteLogIntent, 345);
            }
        });
        mainLayout.addView(checkVotesButton);

        agreementBool = false;
        disagree.setChecked(true);
        agreementCustomizations();

        agreement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                agreementBool = checkedId == agree.getId();
                agreementCustomizations();
            }
        });
    }

    private void agreementCustomizations() {
        if (!agreementBool) {
            foods.setVisibility(View.GONE);
            foods_text.setVisibility(View.GONE);
            drinks.setVisibility(View.GONE);
            drinks_text.setVisibility(View.GONE);
        } else {
            foods.setVisibility(View.VISIBLE);
            foods_text.setVisibility(View.VISIBLE);
            drinks.setVisibility(View.VISIBLE);
            drinks_text.setVisibility(View.VISIBLE);
        }

    }

    private int convertDpsToPixels(int dps) {
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dps + 0.5);
    }

    private ArrayList<Vote> readVotesFromFile() {
        try {
            File file = new File(getFilesDir(), "votes.txt");
            if (!file.exists()) throw new IOException();
            InputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            return (ArrayList<Vote>) o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeVotesToFile() {
        try {
            OutputStream outputStream = openFileOutput("votes.txt", MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> readStringFromFile(int resourceID) {
        Scanner scan = new Scanner(getResources().openRawResource(resourceID));
        ArrayList<String> items = new ArrayList<>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            items.add(line);
        }
        scan.close();

        return items;
    }

    private boolean fileExist(String fname) {
        File file = getApplicationContext().getFileStreamPath(fname);
        return file.exists();
    }
}