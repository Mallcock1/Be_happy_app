package com.example.behappy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moodButton = findViewById<Button>(R.id.moodButton)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)

        moodButton.setOnClickListener {
            val mood = seekBar.progress
            resultsTextView.text = mood.toString()
        }


        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "todo-list.db"
        ).build()

        val thread = Thread {
            GlobalScope.launch {
                db.todoDao().insertAll(TodoEntry("Title", "Content"))
                data = db.todoDao().getAll()

                data?.forEach {
                    println(it)
                }
            }
        }

/*        var db = Room.databaseBuilder(applicationContext, AppDB::class.java, "MoodDB").build()

        val thread = Thread {
            var mood = Mood_Entity()
            mood.user_mood = 1
            mood.user_time = "time now"

            db.Mood_DAO().insertOrUpdateMood(mood)

            db.Mood_DAO().getMood().forEach()
                {
                Log.i("@AKTDEV", "Mood: : ${it.user_mood}")
                Log.i("@AKTDEV", "Time: : ${it.user_time}")
            }
            }*/

        thread.start()
    }
}
