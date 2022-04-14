package com.example.kotlin_github_search_mvvm.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_github_search_mvvm.MainActivity
import com.example.kotlin_github_search_mvvm.R
import com.example.kotlin_github_search_mvvm.databinding.FragmentProjectListBinding
import com.example.kotlin_github_search_mvvm.service.model.Project
import com.example.kotlin_github_search_mvvm.view.adapter.ProjectAdapter
import com.example.kotlin_github_search_mvvm.view.callback.ProjectClickCallback
import com.example.kotlin_github_search_mvvm.viewModel.ProjectListViewModel

const val TAG_OF_PROJECT_LIST_FRAGMENT = "ProjectListFragment"

// ProjectListFragmentはLoading画面かProjectList画面かの管理をしている。
class ProjectListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ProjectListViewModel::class.java)
    }

    // fragment_project_list.xmlと繋がっている
    private lateinit var binding: FragmentProjectListBinding

    // ProjectAdapterはProjectListの内容を管理していうる
    private val projectAdapter: ProjectAdapter = ProjectAdapter(object : ProjectClickCallback {
        override fun onClick(project: Project) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                    // MainActivityのshowを実行している。
                (activity as MainActivity).show(project)
            }
        }
    })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false) //dataBinding
        //
        binding.apply {
            projectList.adapter = projectAdapter
            isLoading = true
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // projectListLiveDataを監視。更新されたら走る。
        viewModel.projectListLiveData.observe(viewLifecycleOwner, Observer { projects ->
            //  List<Project>型のprojectsがnullでなければ中身を実行。
            projects?.let {
                binding.isLoading = false
                projectAdapter.setProjectList(it)
            }
        })
    }
}
