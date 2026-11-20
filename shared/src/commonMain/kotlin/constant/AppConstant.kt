package constant

object AppConstant {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    /**
     * Builds a TMDB image URL. Returns an empty string when [path] is missing so callers
     * never produce URLs such as ".../w342null".
     *
     * @param size TMDB size segment, e.g. "w185" for thumbnails, "w342" for posters,
     * "w780" for backdrops.
     */
    fun imageUrl(path: String?, size: String = "w342"): String =
        if (path.isNullOrBlank()) "" else "$IMAGE_BASE_URL$size$path"
}
