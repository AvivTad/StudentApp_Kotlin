package com.example.studentapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.model.Student
import com.example.studentapp.databinding.StudentItemBinding

class StudentAdapter(
    private val students: List<Student>,
    private val onStudentClick: (Student) -> Unit,
    private val onCheckedChanged: (Student, Boolean) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size
    inner class StudentViewHolder(private val binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.studentName.text = student.name
            binding.studentCheckBox.isChecked = student.isChecked
            binding.studentId.text = student.id.toString()
            binding.root.setOnClickListener {
                onStudentClick(student)
            }
            binding.studentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChanged(student, isChecked)
            }
        }
    }
}
