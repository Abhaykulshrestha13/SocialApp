package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.socialapp.daos.PostDaos

class CreatePostActivity : AppCompatActivity() {
    lateinit var postButton:Button
    lateinit var postInput:EditText
    private lateinit var postDao:PostDaos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        postButton = findViewById(R.id.postButton)
        postInput = findViewById(R.id.postInput)
        postDao = PostDaos()
        postButton.setOnClickListener {
            val input = postInput.text.toString()
            if (input.isNotEmpty()) {
                postDao.addPost(input)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}