package com.example.tudutask.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tudutask.R
import com.example.tudutask.model.Task
import com.google.android.material.textfield.TextInputEditText

class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val viewModel: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = arguments?.getInt("taskId", -1) ?: -1

        val titleEditText = view.findViewById<TextInputEditText>(R.id.editTextTitle)
        val descriptionEditText = view.findViewById<TextInputEditText>(R.id.editTextDescription)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)

        if (taskId != -1) {
            viewModel.getTask(taskId).observe(viewLifecycleOwner) { task ->
                task?.let {
                    titleEditText.setText(it.title)
                    descriptionEditText.setText(it.description)
                }
            }
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (title.isNotEmpty()) {
                if (taskId == -1) {
                    viewModel.insert(Task(title = title, description = description))
                } else {
                    viewModel.update(Task(id = taskId, title = title, description = description))
                }
                findNavController().popBackStack()
            }
        }
    }
}
