package com.businesstinder.tinder.Matches

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.businesstinder.tinder.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class MatchesActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mMatchesAdapter: RecyclerView.Adapter<*>? = null
    private var mMatchesLayoutManager: RecyclerView.LayoutManager? = null
    private var cusrrentUserID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)
        cusrrentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.isNestedScrollingEnabled = false
        mRecyclerView!!.setHasFixedSize(true)
        mMatchesLayoutManager = LinearLayoutManager(this@MatchesActivity)
        mRecyclerView!!.layoutManager = mMatchesLayoutManager
        mMatchesAdapter = MatchesAdapter(dataSetMatches, this@MatchesActivity)
        mRecyclerView!!.adapter = mMatchesAdapter
        userMatchId
    }

    private val userMatchId: Unit
        private get() {
            val matchDb = FirebaseDatabase.getInstance().reference.child("Users").child(cusrrentUserID).child("connections").child("matches")
            matchDb.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (match in dataSnapshot.children) {
                            FetchMatchInformation(match.key)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    private fun FetchMatchInformation(key: String) {
        val userDb = FirebaseDatabase.getInstance().reference.child("Users").child(key)
        userDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userId = dataSnapshot.key
                    var name = ""
                    var profileImageUrl = ""
                    if (dataSnapshot.child("name").value != null) {
                        name = dataSnapshot.child("name").value.toString()
                    }
                    if (dataSnapshot.child("profileImageUrl").value != null) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").value.toString()
                    }
                    val obj = MatchesObject(userId, name, profileImageUrl)
                    resultsMatches.add(obj)
                    mMatchesAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private val resultsMatches = ArrayList<MatchesObject>()
    private val dataSetMatches: List<MatchesObject>
        private get() = resultsMatches
}