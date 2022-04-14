package com.example.kotlin_github_search_mvvm.view.callback

import com.example.kotlin_github_search_mvvm.service.model.Project

/**
 * @link onClick(Project project) 詳細画面に移動
 */
interface ProjectClickCallback {
    fun onClick(project: Project)
}
