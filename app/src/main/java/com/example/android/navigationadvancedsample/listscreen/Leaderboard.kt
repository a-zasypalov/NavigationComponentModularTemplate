package com.example.android.navigationadvancedsample.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigationadvancedsample.R

class Leaderboard : Fragment(R.layout.fragment_leaderboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAdapter = MyAdapter(Array(10) { "Person ${it + 1}" })

        view.findViewById<RecyclerView>(R.id.leaderboard_list).run {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

}

class MyAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_view_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

        holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(listOfAvatars[position % listOfAvatars.size])

        holder.item.setOnClickListener {
            val bundle = bundleOf(USERNAME_KEY to myDataset[position])

            holder.item.findNavController().navigate(
                    R.id.action_leaderboard_to_userProfile,
                    bundle)
        }
    }

    override fun getItemCount() = myDataset.size

    companion object {
        const val USERNAME_KEY = "userName"
    }
}

private val listOfAvatars = listOf(
        R.drawable.avatar_1_raster,
        R.drawable.avatar_2_raster,
        R.drawable.avatar_3_raster,
        R.drawable.avatar_4_raster,
        R.drawable.avatar_5_raster,
        R.drawable.avatar_6_raster
)
