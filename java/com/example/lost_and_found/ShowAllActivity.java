package com.example.lost_and_found;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    private ListView listView;
    private TaskAdapter taskAdapter;
    private TaskDAO taskDAO;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all);

        listView = findViewById(R.id.listView);
        buttonBack = findViewById(R.id.buttonBack);
        taskDAO = new TaskDAO(this);
        List<Task> taskList = taskDAO.getAllTasks();

        taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //
            }
        });

        // Handle clicking on a task
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = taskList.get(position);
            Intent intent = new Intent(ShowAllActivity.this, AdvertDetailsActivity.class);
            intent.putExtra("item_id", task.getId());
            startActivity(intent);
        });
    }
}
