package com.example.kotlin_github_search_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_github_search_mvvm.service.model.Project
import com.example.kotlin_github_search_mvvm.view.ui.ProjectFragment
import com.example.kotlin_github_search_mvvm.view.ui.ProjectListFragment
import com.example.kotlin_github_search_mvvm.view.ui.TAG_OF_PROJECT_LIST_FRAGMENT

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = ProjectListFragment() //一覧のFragment
            // MainActivityの上にProjectListFragmentを表示するイメージ
            supportFragmentManager
                .beginTransaction()
                // R.id.fragment_containerはactivity_main.xml
                .add(R.id.fragment_container, fragment, TAG_OF_PROJECT_LIST_FRAGMENT)
                .commit()
        }
    }

    // ProjectListの要素をクリックしたら呼び出される。画面遷移の仕事Activityに持たせている。
    fun show(project: Project) {
        val projectFragment = ProjectFragment.forProject(project.name) //詳細のFragment
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("project")
            .replace(R.id.fragment_container, projectFragment, null)
            .commit()
    }
}
