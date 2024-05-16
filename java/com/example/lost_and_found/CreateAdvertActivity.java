package com.example.lost_and_found;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private EditText nameEdit, phoneEdit, descriptionEdit, dateEdit, locationEdit;
    private RadioGroup radioGroupType;
    private Button saveButton;
    private TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_advert);

        nameEdit = findViewById(R.id.name_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        descriptionEdit = findViewById(R.id.description_edit);
        dateEdit = findViewById(R.id.date_edit);
        locationEdit = findViewById(R.id.location_edit);
        radioGroupType = findViewById(R.id.radioGroupType); // Updated ID reference
        saveButton = findViewById(R.id.button);

        taskDAO = new TaskDAO(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString().trim();
                String phone = phoneEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();
                String dateString = dateEdit.getText().toString().trim();
                String location = locationEdit.getText().toString().trim();
                int selectedRadioButtonId = radioGroupType.getCheckedRadioButtonId();

                if (selectedRadioButtonId == -1) {
                    Toast.makeText(CreateAdvertActivity.this, "Please select a post type", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String type = selectedRadioButton.getText().toString();

                long date = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date parsedDate = sdf.parse(dateString);
                    if (parsedDate != null) {
                        date = parsedDate.getTime();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Task task = new Task(0, name, phone, description, date, location, type);
                taskDAO.addTask(task);

                finish(); // Go back to the previous activity
            }
        });
    }
}
