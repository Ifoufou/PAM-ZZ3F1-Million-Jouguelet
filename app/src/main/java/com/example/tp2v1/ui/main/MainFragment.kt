package com.example.tp2v1.ui.main

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2v1.R
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
/***
Class representing an Item
    [name]: name of the game
    [release_date]: date of release
    [cover]: name of the image (cover of the game) file in the server
    [story]: paragraph describing the story

    Need to Parcelable since we need to sen game info from MainFragment to DescriptionFragment
 ****/
data class TouhouGame(val name: String?, val release_date: String?, val cover: String?, val story: String?)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    // methods that need to be override
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(release_date)
        parcel.writeString(cover)
        parcel.writeString(story)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<TouhouGame> {
        override fun createFromParcel(parcel: Parcel): TouhouGame {
            return TouhouGame(parcel)
        }

        override fun newArray(size: Int): Array<TouhouGame?> {
            return arrayOfNulls(size)
        }
    }
}


class MainFragment : Fragment(), OnGameListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView;
    private lateinit var myAdapter: MyAdapter;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView);

        viewModel.touhouGames().observe(viewLifecycleOwner, Observer { list ->
            myAdapter = MyAdapter(list, this)
            recyclerView.adapter = myAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    // override from OnGameListener
    override fun onRecycleViewItemClick(position: Int) {
        var game: TouhouGame = viewModel.touhouGames().value!!.get(position);
        val bundle = bundleOf("game" to game) // the corresponding game is passed by argument when DescriptionFragment's view is created
        findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment,bundle)
    }

    override fun onRecycleViewLongPress(position: Int) {
        TODO("Not yet implemented")
    }

}