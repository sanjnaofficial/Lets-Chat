package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast


class RateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rate, container, false)

        val rating = view.findViewById<RatingBar>(R.id.simpleRatingBar)
        val submitBtn = view.findViewById<Button>(R.id.btnRate)
        val tvrate = view.findViewById<TextView>(R.id.tvRes)

        submitBtn.setOnClickListener {
            val totalStars = "Total Stars: "+ rating.numStars
            val rate = "Given Rating: " + rating.rating

            tvrate.setText(totalStars + " " +rate)

            Toast.makeText(context,"""$totalStars and $rate""".trimIndent(), Toast.LENGTH_LONG).show()
        }


        return view
    }

}