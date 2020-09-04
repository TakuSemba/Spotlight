package com.takusemba.spotlightsample

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ChooserActivity : AppCompatActivity(R.layout.activity_chooser) {

  private val samples: Array<String> = arrayOf(
      SAMPLE_SPOTLIGHT_ON_ACTIVITY,
      SAMPLE_SPOTLIGHT_ON_FRAGMENT
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val listView = findViewById<ListView>(R.id.sample_list)
    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, samples)
    listView.adapter = adapter
    listView.setOnItemClickListener { _, _, position, _ ->
      when (samples[position]) {
        SAMPLE_SPOTLIGHT_ON_ACTIVITY -> {
          val intent = Intent(this, ActivitySampleActivity::class.java)
          startActivity(intent)
        }
        SAMPLE_SPOTLIGHT_ON_FRAGMENT -> {
          val intent = Intent(this, FragmentSampleActivity::class.java)
          startActivity(intent)
        }
      }
    }
  }

  companion object {
    private const val SAMPLE_SPOTLIGHT_ON_ACTIVITY = "Spotlight on Activity"
    private const val SAMPLE_SPOTLIGHT_ON_FRAGMENT = "Spotlight on Fragment"
  }
}
