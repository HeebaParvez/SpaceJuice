package com.example.spacejuice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacejuice.activity.AllHabitsActivity;

import java.util.ArrayList;

public class Member extends AppCompatActivity {
   private static Member instance;
   public static ArrayAdapter<Habit> habitAdapter;
   public static ArrayList<Habit> habitList;

   Member(Context activity, int layout) {
      habitList = new ArrayList<>();
      if (habitAdapter == null) {
         habitAdapter = new ArrayAdapter<>(activity, layout, habitList);
      }
   }

   public static Member getInstance(Context activity, int layout) {
      if (instance == null) {
         instance = new Member(activity, layout);
      }
      return instance;
   }

   public static Member getInstance() {
      return instance;
   }

   public void ViewAllHabits(Context context) {
      Log.d("debugInfo", "Member.ViewAllHabits() was run");
      if (context == null) {
         Log.d("debugInfo", "Context is Null");
      }
      else {
         Log.d("debugInfo", "Context: " + context);
         Intent intent = new Intent(context, AllHabitsActivity.class);
         // THIS IS GIVING A NULL REFERENCE ERROR - STILL LOOKING AT HOW TO FIX THIS - Brad
         // COMMENTED OUT FOR NOW
         //startActivity(intent);
      }
   }
}