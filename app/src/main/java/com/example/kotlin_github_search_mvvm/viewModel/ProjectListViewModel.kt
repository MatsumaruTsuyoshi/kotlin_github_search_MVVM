package com.example.kotlin_github_search_mvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlin_github_search_mvvm.R
import com.example.kotlin_github_search_mvvm.service.model.Project
import com.example.kotlin_github_search_mvvm.service.repository.ProjectRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepository.instance
    var projectListLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    init {
        loadProjectList()
    }

    private fun loadProjectList() = viewModelScope.launch { //onCleared() のタイミングでキャンセルされる
        try {
            // ProjectRepositoryのgetProjectListを呼び出し。引数にはユーザー名
            val response = repository.getProjectList(getApplication<Application>().getString(R.string.github_user_name))
            if (response.isSuccessful) {
                projectListLiveData.postValue(response.body()) //データを取得したら、LiveDataを更新
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}
