package com.blaze.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blaze.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        binding?.recyclerView?.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }

        })

        binding?.addButton?.setOnClickListener {
            val noteText =binding?.input?.text.toString()
            if(noteText.isNotEmpty()) {
                viewModel.insertNote((Note(noteText)))
                Toast.makeText(this, "Note Inserted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
    }
}