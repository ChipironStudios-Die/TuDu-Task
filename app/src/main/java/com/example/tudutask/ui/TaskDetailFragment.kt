package com.example.tudutask.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tudutask.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val viewModel: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = arguments?.getInt("taskId") ?: -1

        val titleTextView = view.findViewById<TextView>(R.id.textViewDetailTitle)
        val statusTextView = view.findViewById<TextView>(R.id.textViewDetailStatus)
        val descriptionTextView = view.findViewById<TextView>(R.id.textViewDetailDescription)
        val fabEdit = view.findViewById<FloatingActionButton>(R.id.fabEditTask)

        viewModel.getTask(taskId).observe(viewLifecycleOwner) { task ->
            task?.let {
                titleTextView.text = it.title
                statusTextView.text = if (it.isCompleted) "Completed" else "Pending"
                descriptionTextView.text = it.description

                fabEdit.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt("taskId", it.id)
                        putString("title", "Edit Task")
                    }
                    findNavController().navigate(R.id.action_taskDetailFragment_to_addEditTaskFragment, bundle)
                }
            }
        }
    }
}
