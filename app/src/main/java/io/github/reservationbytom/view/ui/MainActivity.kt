package io.github.reservationbytom.view.ui

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.reservationbytom.R
import io.github.reservationbytom.R.id
import io.github.reservationbytom.service.GetLocationJobService
import io.github.reservationbytom.service.model.Rest
import java.util.*


class MainActivity : AppCompatActivity() {
  val MY_BACKGROUND_JOB = 0
  private val REQUEST_CODE = 1000
  private lateinit var db: SQLiteDatabase

  // https://medium.com/nextbeat-engineering/android%E3%82%A2%E3%83%97%E3%83%AA%E3%81%B8%E3%81%AEbottom-navigation%E3%81%AE%E5%B0%8E%E5%85%A5-872c17b21278
  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_cart -> {
        println("da")
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_my_Page -> {
        println("dada")
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_item_list -> {
        println("dadada")
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_style -> {
        println("dadadada")
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  // Jobをsetする関数 : https://developer.android.com/topic/performance/background-optimization?hl=ja
  fun scheduleJob(context: Context) {
    // Backgroundでjobを実行する
    val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    val job = JobInfo.Builder(
      MY_BACKGROUND_JOB,
      ComponentName(context, GetLocationJobService::class.java) // メタデータ Class<?> をpassする必要がある
      // ComponentName(context, GetLocationJobService::class.java) // ここでコンストラクタとしてDBのインスタンスを渡す
    )
      .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
      .setRequiresCharging(true)
      .setPeriodic(Calendar.MINUTE.toLong() * 15) // 15分ごとしか無理: https://medium.com/@yonatanvlevin/the-minimum-interval-for-periodicwork-is-15-minutes-same-as-jobscheduler-periodic-job-eb2d63716d1f
      .build()
    jobScheduler.schedule(job)

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Permissionの許諾を取る: https://developer.android.com/training/location/retrieve-current#permissions
    if (Build.VERSION.SDK_INT >= 23) { // 23以降厳格なPermission許諾が求められる
      if (ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        val permissions = arrayOf(
          Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        // permissions が指定されていない場合、permissionsをrequestする
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
      }
    }

    scheduleJob(this)



    // savedInstanceState == null について : https://qiita.com/Nkzn/items/c09629d91d5cf42ff05d
    if (savedInstanceState == null) {
      val fragment = RestListFragment()
      // https://stackoverflow.com/questions/64529217/unresolved-reference-error-in-kotlin-bottom-navigation-setonnavigationitemselec
      findViewById<BottomNavigationView>(R.id.navigation)
        .setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
      println("no fire fragment")
//      supportFragmentManager
//        .beginTransaction()
//        .add(
//          id.fragment_container, fragment,
//          TAG_OF_REST_LIST_FRAGMENT
//        )
//        .commit()
    }
  }

  //詳細画面への遷移
  fun show(rest: Rest) {
    val restFragment = RestFragment.forRest(rest.name)
    supportFragmentManager
      .beginTransaction()
      .addToBackStack("rest")
      .replace(id.fragment_container, restFragment, null)
      .commit()
  }
}
