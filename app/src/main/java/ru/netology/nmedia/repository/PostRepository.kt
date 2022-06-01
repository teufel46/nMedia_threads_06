package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAllAsync(callback: PostCallback<List<Post>>)
    fun saveAsync(post : Post, callback: PostCallback<Post>)
    fun removeByIdAsync(id: Long, callback: PostCallback<Unit>)
    fun likeByIdAsync(id: Long, callback: PostCallback<Post>)

    interface PostCallback <T> {
        fun onSuccess(value: T) {}
        fun onError(e: Exception) {}
    }
}
