package com.eggy.belajarfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eggy.belajarfirebase.databinding.ActivityMainBinding
import com.eggy.belajarfirebase.ui.account.AccountFragment
import com.eggy.belajarfirebase.ui.cloudstorage.CloudStorageFragment
import com.eggy.belajarfirebase.ui.firestore.HomeFragment
import com.eggy.belajarfirebase.ui.realtime.RealtimeDbFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        binding.bottomNavigaton.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_firestore ->{
                    showFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_account->{
                    showFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_realtime_db->{
                    showFragment(RealtimeDbFragment())
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.action_storage->{
                    showFragment(CloudStorageFragment())
                    return@setOnNavigationItemSelectedListener  true
                }



            }
            return@setOnNavigationItemSelectedListener false
        }
        showDefaultFragment()
    }

    private fun showDefaultFragment() {
        binding.bottomNavigaton.selectedItemId = R.id.action_firestore
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

}