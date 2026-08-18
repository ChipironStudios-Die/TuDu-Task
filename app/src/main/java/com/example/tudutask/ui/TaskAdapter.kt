package com.example.tudutask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tudutask.R
import com.example.tudutask.model.Task

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskCheckedChange: (Task, Boolean) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onTaskClick, onTaskCheckedChange)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxDone)

        fun bind(
            task: Task,
            onTaskClick: (Task) -> Unit,
            onTaskCheckedChange: (Task, Boolean) -> Unit
        ) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            checkBox.isChecked = task.isCompleted

            itemView.setOnClickListener { onTaskClick(task) }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckedChange(task, isChecked)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}
