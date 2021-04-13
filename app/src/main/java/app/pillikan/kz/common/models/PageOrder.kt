package app.pillikan.kz.common.models

class PageOrder<T> {

    private var content: List<T>? = null
    private var hasNextPage = false

    constructor(content: List<T>?, hasNextPage: Boolean) {
        this.content = content
        this.hasNextPage = hasNextPage
    }

    fun getContent(): List<T>? {
        return content
    }

    fun hasNextPage(): Boolean {
        return hasNextPage
    }
}