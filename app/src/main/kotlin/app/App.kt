package app

import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class User(val id: Int, val name: String, val email: String)

interface JsonPlaceholderAPI {
    @GET("users/{id}")
    fun getUser(@Path("id") userId: Int): Call<User>
}

class UserApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: JsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

    suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        val call = apiService.getUser(5)
        val response: Response<User> = call.execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}


fun main() {
    println("start")
     runBlocking {
         println("start: runBlocking")
        val userApiClient = UserApiClient()

        val user = userApiClient.getUser()
        if (user != null) {
            println("User: $user")
        } else {
            println("Failed to fetch user data")
        }

         println("end: runBlocking")
//         Terminate the process
        System.exit(0)
    }
    println("done")
}
