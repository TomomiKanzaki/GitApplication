package com.example.gitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitapplication.ui.users.UsersListFragment
import com.example.gitapplication.util.addFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(
            R.id.container,
            UsersListFragment(),
            isAddToBackStack = false
        )
    }
}