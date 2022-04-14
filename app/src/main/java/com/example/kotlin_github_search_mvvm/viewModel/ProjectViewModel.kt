package com.example.kotlin_github_search_mvvm.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.kotlin_github_search_mvvm.R
import com.example.kotlin_github_search_mvvm.service.model.Project
import com.example.kotlin_github_search_mvvm.service.repository.ProjectRepository
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val myApplication: Application,
    private val mProjectID: String
) : AndroidViewModel(myApplication) {

    private val repository = ProjectRepository.instance
    val projectLiveData: MutableLiveData<Project> = MutableLiveData()

    var project = ObservableField<Project>()

    init {
        loadProject()
    }

    private fun loadProject() = viewModelScope.launch { //onCleared() のタイミングでキャンセルされる
        try {
            val project = repository.getProjectDetails(myApplication.getString(R.string.github_user_name), mProjectID)
            if (project.isSuccessful) {
                projectLiveData.postValue(project.body())
            }
        } catch (e: Exception) {
            Log.e("loadProject:Failed", e.stackTrace.toString())
        }
    }


    fun setProject(project: Project) {
        this.project.set(project)
    }

    class Factory(private val application: Application, private val projectID: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProjectViewModel(application, projectID) as T
        }
    }
}
