package com.shah.cashwise.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.shah.cashwise.data.api.AuthApi
import com.shah.cashwise.data.model.auth.FirebaseUserSignUp
import com.shah.cashwise.data.model.auth.User
import com.shah.cashwise.utils.ResponseResource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Monil on 18/03/25.
 */

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val api: AuthApi
) : BaseRepository() {

    // Register user
    suspend fun registerUser(user: FirebaseUserSignUp): ResponseResource<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            ResponseResource.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            ResponseResource.Failure(false,e.message ?: "Error occurred in creating user.")
        } catch (e: Exception) {
            ResponseResource.Failure(false,e.message ?: "Something went wrong. Please try again.")
        }
    }

    suspend fun registerUserWithBackend(user: User) =
        safeApiCall { api.registerUser(user) }

    // Login user
    suspend fun loginUser(user: FirebaseUserSignUp): ResponseResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
            ResponseResource.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            ResponseResource.Failure(false,e.message ?: "Error occurred in logging in.")
        } catch (e: Exception) {
            ResponseResource.Failure(false,e.message ?: "Something went wrong. Please try again.")
        }
    }

    suspend fun loginUserWithBackend(user: User) =
        safeApiCall { api.loginUser(user) }

    // Sign in with Google
    suspend fun signInWithGoogle(idToken: String): ResponseResource<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            ResponseResource.Success(result.user!!)
        } catch (e: FirebaseAuthException) {
            ResponseResource.Failure(false,e.message ?: "Error occurred in signing in with Google.")
        } catch (e: Exception) {
            ResponseResource.Failure(false,e.message ?: "Something went wrong. Please try again.")
        }
    }

    // Logout
    fun logout() {
        firebaseAuth.signOut()
    }

    // Get user
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
}