package com.abdullahalamodi.simpletest

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StudentListFragment : Fragment(), StudentDialogFragment.Callbacks {

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var addBtn: ImageButton;
    private lateinit var clueGroup: Group;
    private var adapter: StudentAdapter? = StudentAdapter()
    private val studentListViewModel: StudentListViewModel by lazy {
        ViewModelProviders.of(this).get(StudentListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        studentRecyclerView = view.findViewById(R.id.student_recycler_view)
        addBtn = view.findViewById(R.id.add_btn);
        clueGroup = view.findViewById(R.id.add_clue_group);
        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        studentRecyclerView.adapter = adapter

        addBtn.setOnClickListener {
            StudentDialogFragment.newInstance().apply {
                setTargetFragment(this@StudentListFragment, 0)
                show(this@StudentListFragment.requireFragmentManager(), "addStudent")
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val students = studentListViewModel.getStudents()
        // display clue when list is empty
        updateUI(students)

    }

    companion object {
        fun newInstance(): StudentListFragment {
            return StudentListFragment()
        }
    }

    private fun updateUI(students: List<Student>) {
        if (students.isEmpty()) {
            setAddClueViability(View.VISIBLE);
        } else {
            setAddClueViability(View.GONE);
            adapter = StudentAdapter()
            adapter?.submitList(students)
            studentRecyclerView.adapter = adapter
        }

    }

    private fun setAddClueViability(visible: Int) {
        clueGroup.visibility = visible;
    }


    //menu inflate
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_student_list, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                StudentDialogFragment.newInstance().apply {
                    setTargetFragment(this@StudentListFragment, 0)
                    show(this@StudentListFragment.requireFragmentManager(), "addStudent")
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private inner class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var student: Student
        private val numberTextView: TextView = itemView.findViewById(R.id.student_number)
        private val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        private val passTextView: TextView = itemView.findViewById(R.id.student_pass)
        private val deleteBtn: TextView = itemView.findViewById(R.id.delete_btn)

        init {
            deleteBtn.setOnClickListener {
                onDeleteStudent(adapterPosition)
            }
        }

        fun bind(student: Student) {
            this.student = student
            numberTextView.text = "" + student.number
            nameTextView.text = student.name
            passTextView.apply {
                text = student.pass.toString();
                if (student.pass) {
                    setBackgroundColor(resources.getColor(R.color.green))
                } else {
                    setBackgroundColor(resources.getColor(R.color.red))
                }
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

    override fun onAddStudent(student: Student) {
        studentListViewModel.addStudent(student)
        updateUI(studentListViewModel.getStudents());
    }

    override fun onDeleteStudent(position: Int) {
        studentListViewModel.deleteStudent(position);
        updateUI(studentListViewModel.getStudents());
    }
}
