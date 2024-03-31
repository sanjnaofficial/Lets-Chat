package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainPage : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        val headerView = navView.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.user_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.user_email)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottonNav)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    // for name of current user
                    if(mAuth.currentUser?.uid==currentUser?.uid){
                        currentUser?.let {
                            nameTextView.text = it.name
                        }
                    }
                    //for contact list
                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }

                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Set the user's name and email in the navigation header
        val currentUser: FirebaseUser? = mAuth.currentUser
        val email = currentUser?.email
        emailTextView.text = email

        // nav bar items
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(
                        applicationContext,
                        "Welcome to Home",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this,MainPage::class.java)
                    finish()
                    startActivity(intent)
                }
                R.id.logout -> {
                    Toast.makeText(applicationContext, "Logging out", Toast.LENGTH_SHORT).show()
                    mAuth.signOut()
                    val intent = Intent(this, HomeActivity::class.java)
                    finish()
                    startActivity(intent)
                }

                R.id.rate->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.linearLayout, // ID of the container where the Fragment will be placed
                        RateFragment()
                    ).commit()

                }
            }
            true
        }

        //on selecting bottom navbar

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    Toast.makeText(
                        applicationContext,
                        "Welcome to Home",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this,MainPage::class.java)
                    finish()
                    startActivity(intent)
                }
                R.id.menu_logout -> {
                    Toast.makeText(applicationContext, "Logging out", Toast.LENGTH_SHORT).show()
                    mAuth.signOut()
                    val intent = Intent(this, HomeActivity::class.java)
                    finish()
                    startActivity(intent)
                }

                R.id.menu_rating->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.linearLayout, // ID of the container where the Fragment will be placed
                        RateFragment()
                    ).commit()

                }


            }
            true
        }



    }

    //for logout option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        if(item.itemId == R.id.logout){
            //write the login for logout
            mAuth.signOut()

            val intent = Intent(this,HomeActivity::class.java)
            finish()
            startActivity(intent)

            return true

        }

        return true
    }
}