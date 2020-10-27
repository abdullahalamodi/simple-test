package com.abdullahalamodi.simpletest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StudentListFragment : Fragment() {

    private lateinit var studentRecyclerView: RecyclerView
    private var adapter: StudentAdapter? = StudentAdapter()
    private val studentListViewModel: StudentListViewModel by lazy {
        ViewModelProviders.of(this).get(StudentListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        studentRecyclerView = view.findViewById(R.id.student_recycler_view)
        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        studentRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val students = studentListViewModel.getStudents()
        updateUI(students)

    }

    companion object {
        fun newInstance(): StudentListFragment {
            return StudentListFragment()
        }
    }

    private fun updateUI(students: List<Student>) {
        adapter = StudentAdapter()
        adapter?.submitList(students)
        studentRecyclerView.adapter = adapter
    }


    private inner class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var student: Student
        private val numberTextView: TextView = itemView.findViewById(R.id.student_number)
        private val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        private val passTextView: TextView = itemView.findViewById(R.id.student_pass)


        fun bind(student: Student) {
            this.student = student
            numberTextView.text = "" + student.number
            nameTextView.text = student.name
            passTextView.apply {
                text = student.pass.toString();
//                if (student.pass){
//
//                }else{
//
//                }
            }
        }
    }


    //adapter class
    private inner class StudentAdapter :
        ListAdapter<Student, StudentHolder>(StudentDiffUtil()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_student, parent, false)
            return StudentHolder(view)
        }

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student = getItem(position)
            holder.bind(student)
        }

    }

    class StudentDiffUtil : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }

    }
}
