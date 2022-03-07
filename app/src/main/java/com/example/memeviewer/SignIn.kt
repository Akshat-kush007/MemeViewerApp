package com.example.memeviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.memeviewer.Daos.UserDao
import com.example.memeviewer.Models.Users
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    private val RC_SIGN_IN: Int = 123
    private lateinit var googleSignInClient: GoogleSignInClient
    private val TAG="SignInActivity Tag"
    lateinit var btSignInButton:SignInButton
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        title="Sign In"

        // Configure Google Sign In-------------------------------------------------------------
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("478290929718-ke4q0netrdfr98l3dhtdad9ae4ft7vir.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Configure Google Sign In----------------------------------------------
        // Initialize Firebase Auth------------------
        auth = Firebase.auth
        //--------------------------------------------------------------------------------------


        //Sign In button click
        btSignInButton=findViewById(R.id.btSignInButton)
        btSignInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)

        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                     updateUI(null)
                }
            }

    }


    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {

//            val user = Users(firebaseUser.uid, firebaseUser.displayName, firebaseUser.photoUrl.toString())
//            val usersDao = UserDao()
//            usersDao.addUser(user)
            val user= Users(firebaseUser.uid,firebaseUser.displayName,firebaseUser.photoUrl.toString())
            val userDao= UserDao()
            userDao.addUser(user)


            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            Log.d(TAG, "Redirecting to Main:" )

            finish()
        } else {
            Log.w(TAG, "Redirecting Failed-----", )
//            btSignInButton.visibility = View.VISIBLE
//            progressBar.visibility = View.GONE
        }
    }





}
