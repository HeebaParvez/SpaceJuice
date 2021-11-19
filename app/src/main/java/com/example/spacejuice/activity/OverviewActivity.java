package com.example.spacejuice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spacejuice.Habit;
import com.example.spacejuice.HabitListAdapter;
import com.example.spacejuice.MainActivity;
import com.example.spacejuice.R;
import com.example.spacejuice.controller.HabitController;
import com.example.spacejuice.controller.HabitEventController;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    ImageButton profile_imagebutton;
    ImageButton add_habit_imagebutton;
    ActivityResultLauncher<Intent> editLaunch;
    ActivityResultLauncher<Intent> addEventLaunch;
    Boolean filterToday = true;
    Button today_all_toggle;
    ArrayList<Habit> habitListItems;
    ArrayList<Habit> today_habit_items;
    /*
    This Activity is used my main page which shows an overlay of today's habits
    and various menus
     */
    public ListView today_habit_list;
    public HabitListAdapter habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_habits);

        addEventLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("debugInfo", "result code: " + result.getResultCode());
                        refreshData();
                    }
                });

        editLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("debugInfo", "result code: " + result.getResultCode());
                        refreshData();

                    }
                });

        profile_imagebutton = findViewById(R.id.profile_imagebutton);
        add_habit_imagebutton = findViewById(R.id.add_habit_imagebutton);
        today_all_toggle = findViewById(R.id.list_toggle_button_switch);
        today_habit_list = findViewById(R.id.overview_habit_listview);
        refreshData();

        int score = MainActivity.getUser().getScore();
        Log.d("debugInfo", "From OverviewActivity - user score = " + score);

        today_all_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterToday = !filterToday;
                refreshData();
            }
        });

        add_habit_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddHabit();
            }
        });

        // We apply a clicklistener to the imageView
        // and then once it is clicked, we change it to another activity
        profile_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverviewActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openAllHabitsActivity() {
        Intent intent = new Intent(this, AllHabitsActivity.class);
        startActivity(intent);
    }

    public void launchAddHabit() {
        Intent intent = new Intent(OverviewActivity.this, AddHabitActivity.class);
        editLaunch.launch(intent);
    }

    public void launchHabitDetails(int clickedUid) {
        Intent intent = new Intent(OverviewActivity.this, HabitDetailsActivity.class);
        intent.putExtra("habitUid", clickedUid);
        editLaunch.launch(intent);
    }

    public void launchAddNewEvent(int clickedUid) {
        Intent intent = new Intent(OverviewActivity.this, AddHabitEventActivity.class);
        intent.putExtra("habitUid", clickedUid);
        addEventLaunch.launch(intent);
    }

    public void populateTodayItems() {
        today_habit_items = new ArrayList<>();
        for (int i = 0; i < habitListItems.size(); i += 1) {
            if (habitListItems.get(i).isToday()) {
                today_habit_items.add(habitListItems.get(i));
            }
        }
        Log.d("debugInfo", "today's habits populated");

    }

    public void refreshData() {
        /* updates the list of Habits */
        habitListItems = HabitController.getHabitListItems();
        if (filterToday) {
            today_all_toggle.setText("Today's");
            populateTodayItems();
            this.habitAdapter = new HabitListAdapter(this, R.layout.overview_habit_content, today_habit_items);
            Log.d("debugInfo", "today's habit list updated");
            Log.d("debugInfo", "today size: " + today_habit_items.size());
            this.today_habit_list.setAdapter(habitAdapter);
            this.habitAdapter.notifyDataSetChanged();
        } else {
            /* updates the list of Habits */
            today_all_toggle.setText("All");
            this.habitAdapter = new HabitListAdapter(this, R.layout.overview_habit_content, habitListItems);
            this.today_habit_list.setAdapter(habitAdapter);
            habitAdapter.notifyDataSetChanged();
        }


    }
}
