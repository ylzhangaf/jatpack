package com.ylozhangaf.ppjoke.model

/**
 * 用户对象
 */
data class User(val id : Int) {
    // 用户id
    var userId: Long = 0
    // 用户名
    var name: String? = null
    var avatar: String? = null
    // 描述
    var description: String? = null
    var likeCount = 0
    var topCommentCount = 0
    var followCount = 0
    var followerCount = 0
    var qqOpenId: String? = null
    var expires_time: Long = 0
    var score = 0
    var historyCount = 0
    var commentCount = 0
    var favoriteCount = 0
    var feedCount = 0
    var hasFollow = false
}