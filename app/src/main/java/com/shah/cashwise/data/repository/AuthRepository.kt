package com.shah.cashwise.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.shah.cashwise.utils.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    // Register user
    suspend fun registerUser(email: String, password: String): AuthResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            AuthResult.Failure(e.message ?: "Error occurred in creating user." )
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Something went wrong. Please try again.")
        }
    }

    // Login user
    suspend fun loginUser(email: String, password: String): AuthResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            AuthResult.Failure(e.message ?: "Error occurred in logging in." )
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Something went wrong. Please try again.")
        }
    }

    // Sign in with Google
    suspend fun signInWithGoogle(idToken: String): AuthResult<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            AuthResult.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            AuthResult.Failure(e.message ?: "Error occurred in signing in with Google." )
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Something went wrong. Please try again.")
        }
    }

    // Logout
    fun logout() {
        firebaseAuth.signOut()
    }

    // Get user
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
}