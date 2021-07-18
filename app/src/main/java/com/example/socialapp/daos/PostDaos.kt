package com.example.socialapp.daos

import com.example.socialapp.model.Post
import com.example.socialapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDaos {
    val db = FirebaseFirestore.getInstance()
    val postCollections = db.collection("posts")
    val auth = Firebase.auth
    fun addPost(text:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDaos = UserDaos()
            val user = userDaos.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Post(text,user,currentTime)
            postCollections.document().set(post)
        }
    }
    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollections.document(postId).get()
    }
    fun updateLikes(postId: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)

            if(isLiked) {
                post.likedBy.remove(currentUserId)
            } else {
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }

    }
}