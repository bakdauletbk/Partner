package kz.smartideagroup.partner.common.models

class Page<T> {

    private var content: List<T>? = null
    private var totalCount:Int? = null

    constructor(content: List<T>?, totalCount: Int) {
        this.content = content
        this.totalCount = totalCount
    }

    fun getContent(): List<T>? {
        return content
    }

    fun totalCount(): Int? {
        return totalCount
    }
}