package com.example.tudutask.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tudutask.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val viewModel: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        val adapter = TaskAdapter(
            onTaskClick = { task ->
                val bundle = Bundle().apply {
                    putInt("taskId", task.id)
                }
                findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
            },
            onTaskCheckedChange = { task, isChecked ->
                viewModel.update(task.copy(isCompleted = isChecked))
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        view.findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", "Add Task")
            }
            findNavController().navigate(R.id.action_taskListFragment_to_addEditTaskFragment, bundle)
        }
    }
}
