package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post


class PostRepositoryImpl : PostRepository {
    override fun getAllAsync(callback: PostRepository.PostCallback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw RuntimeException("body is null")
                )
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(RuntimeException("error"))
            }
        })
    }

    override fun saveAsync(post: Post, callback: PostRepository.PostCallback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw RuntimeException("body is null")
                )
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException("error"))
            }
        })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.PostCallback<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw RuntimeException("body is null")
                )
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(RuntimeException("error"))
            }

        })
    }

    private fun findById(id: Long, callback: PostRepository.PostCallback<Post>) {
        PostsApi.retrofitService.getById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw RuntimeException("body is null")
                )
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException("error"))
            }

        })
    }


    override fun likeByIdAsync(id: Long, callback: PostRepository.PostCallback<Post>) {

        findById(id, object : PostRepository.PostCallback<Post> {
            override fun onSuccess(value: Post) {
                if (value.likedByMe) {
                    PostsApi.retrofitService.dislikeById(id).enqueue(object : Callback<Post> {
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if (!response.isSuccessful) {
                                callback.onError(RuntimeException(response.message()))
                                return
                            }
                            callback.onSuccess(
                                response.body() ?: throw RuntimeException("body is null")
                            )
                        }

                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            callback.onError(RuntimeException("response.message())"))
                        }

                    })
                } else {
                    PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if (!response.isSuccessful) {
                                callback.onError(RuntimeException(response.message()))
                                return
                            }
                            callback.onSuccess(
                                response.body() ?: throw RuntimeException("body is null")
                            )
                        }

                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            callback.onError(RuntimeException("response.message())"))
                        }

                    })
                }
            }

            override fun onError(e: Exception) {
                callback.onError(RuntimeException("error"))
            }
        })
    }
}
