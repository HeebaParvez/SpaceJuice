package com.example.spacejuice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacejuice.Habit;
import com.example.spacejuice.R;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    Button go_to_all_habits_button;
    Button go_to_add_habit_button;
    ImageView go_to_profile_imageView;
    ImageView go_to_addHabit_imageView;
    /*
    This Activity is used my main page which shows an overlay of today's habits
    and various menus
     */
    public ListView today_habit_list;
    public static ArrayAdapter<Habit> habitAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_habits);

        go_to_profile_imageView = findViewById(R.id.profile_imageView);
        go_to_addHabit_imageView = findViewById(R.id.add_habit_image_view);

        go_to_all_habits_button = (Button) findViewById(R.id.all_habits_button);

        today_habit_list = findViewById(R.id.overview_habit_listview);
        ArrayList<Habit> today_habit_items = new ArrayList<>();

        today_habit_items.add(new Habit("test habit #1", "reason #1", Habit.Indicator.LEVEL00));
        today_habit_items.add(new Habit("test habit #2", "reason #2", Habit.Indicator.LEVEL00));
        today_habit_items.add(new Habit("test habit #3", "reason #3", Habit.Indicator.LEVELNEW));
        today_habit_items.add(new Habit("test habit #4", "reason #4", Habit.Indicator.LEVELNEW));
        today_habit_list.setAdapter(new HabitListAdapter(this, R.layout.overview_habit_content,today_habit_items ));



        go_to_all_habits_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllHabitsActivity();
            }
        });

        go_to_addHabit_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OverviewActivity.this, AddHabitActivity.class);
                startActivity(intent);
            }
        });
        // We apply a clicklistener to the imageView
        // and then once it is clicked, we change it to another activity
        go_to_profile_imageView.setOnClickListener(new View.OnClickListener() {
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
}
