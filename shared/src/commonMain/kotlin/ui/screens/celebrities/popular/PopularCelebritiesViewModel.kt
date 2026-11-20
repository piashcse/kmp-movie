package ui.screens.celebrities.popular

import data.model.celebrities.Celebrity
import data.repository.Repository
import ui.screens.base.PaginatedViewModel

class PopularCelebritiesViewModel(private val repo: Repository) : PaginatedViewModel<Celebrity>() {
    override fun fetchPage(page: Int) = repo.popularCelebrities(page)
}
