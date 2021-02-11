package com.ylozhangaf.ppjoke.model

data class Comment(val id : Int) {

    companion object{
        const val COOMENTTYPE_TWO = 2
    }

    var itemId: Long = 0
    var commentId: Long = 0
    var userId: Long = 0
    var commentType = 0
    var createTime: Long = 0
    var commentCount = 0
    var likeCount = 0
    var commentText: String? = null
    var imageUrl: String? = null
    var videoUrl: String? = null
    var width = 0
    var height = 0
    var hasLiked = false
    var author: User? = null
    var ugc: Ugc? = null
}

