package com.abdullahalamodi.simpletest

import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class StudentDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.student_dialog, null)
        val numberEditText = view?.findViewById(R.id.number) as EditText
        val nameEditText = view?.findViewById(R.id.name) as EditText
        val passedCheckBox = view?.findViewById(R.id.passed) as CheckBox

        return AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setTitle("add student")
            .setPositiveButton("add") { dialog, _ ->
                val s = Student(
                    UUID.randomUUID(),
                    numberEditText.text.toString().toInt(),
                    nameEditText.text.toString(),
                    passedCheckBox.isChecked
                )
                targetFragment?.let {
                    (it as Callbacks).onAddStudent(s)
                }
                dialog.dismiss();

            }
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.cancel()

            }.create()
    }

    interface Callbacks {
        fun onAddStudent(student: Student)
        fun onDeleteStudent(position: Int)
    }

    companion object {
        fun newInstance(): StudentDialogFragment {
            return StudentDialogFragment();
        }
    }
}