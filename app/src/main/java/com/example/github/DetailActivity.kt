package com.example.github

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github.Adapter.SectionsPagerAdapter
import com.example.github.Favtab.FavoriteUser
import com.example.github.Favtab.FavoriteUserDao
import com.example.github.Model.UserData
import com.example.github.View.DetailViewModel
import com.example.github.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    lateinit var bd: ActivityDetailBinding
    var followers = arrayListOf<UserData>()
    var following = arrayListOf<UserData>()
    var userId: Int = 0
    var avatar: String? = ""
    var username: String? = ""
    var namedetail: String? = ""

    private lateinit var viewModel: DetailViewModel
    private lateinit var favoriteUserDao: FavoriteUserDao

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bd.root)
        supportActionBar?.elevation = 0f

        favoriteUserDao = MyApp.database.favoriteUserDao()

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        username = intent.getStringExtra("username")
        namedetail = intent.getStringExtra("username")
        userId = intent.getIntExtra("id", 0)
        avatar = intent.getStringExtra("avatar")

        bd.tvName.text = namedetail
        bd.tvUsername.text = username.toString()
        Glide.with(this)
            .load(avatar)
            .into(bd.imgDetail)

        showProgressBar()
        viewModel.fetchGitHubUserDetail(username.toString())

        viewModel.userDetail.observe(this) { detailUser ->
            bd.tvFollowingDetail.text = detailUser?.following.toString()
            bd.tvFollowersDetail.text = detailUser?.followers.toString()
            bd.tvName.text = detailUser?.name

            hideProgressBar()
        }

        val tabs: TabLayout = findViewById(R.id.tabs)
        val viewPager: ViewPager2 = findViewById(R.id.view)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.toString())
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        bd.toggleFavorite.setOnClickListener {
            val userId = userId.toString()
            val favoriteUser = FavoriteUser(userId = userId)

            favoriteUserDao.insertFavorite(favoriteUser)
        }
    }

    private fun setView() {
        bd.tvFollowersDetail.text = followers.size.toString()
        bd.tvFollowingDetail.text = following.size.toString()
    }

    private fun showProgressBar() {
        bd.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        bd.progressBar.visibility = View.GONE
    }
}