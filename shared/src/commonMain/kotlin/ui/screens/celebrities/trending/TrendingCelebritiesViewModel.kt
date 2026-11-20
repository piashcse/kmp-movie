package ui.screens.celebrities.trending

import data.model.celebrities.Celebrity
import data.repository.Repository
import ui.screens.base.PaginatedViewModel

class TrendingCelebritiesViewModel(private val repo: Repository) : PaginatedViewModel<Celebrity>() {
    override fun fetchPage(page: Int) = repo.trendingCelebrities(page)
}
