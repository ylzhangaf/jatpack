package com.ylozhangaf.ppjoke.model

data class Feed(val id : Int) {
    var itemId: Long = 0
    var itemType = 0
    var createTime: Long = 0
    var duration = 0.0
    var feeds_text: String? = null
    var authorId: Long = 0
    var activityIcon: String? = null
    var activityText: String? = null
    var width = 0
    var height = 0
    var url: String? = null
    var cover: String? = null

    var author: User? = null
    var topComment: Comment? = null
    var ugc: Ugc? = null
}