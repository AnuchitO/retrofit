package app

import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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

//    suspend fun getUser(): User? = withContext(Dispatchers.IO) {
//        val call = apiService.getUser(5)
//        val response: Response<User> = call.execute()
//        if (response.isSuccessful) {
//            response.body()
//        } else {
//            null
//        }
//    }

    fun createClient(): JsonPlaceholderAPI {
        return retrofit.create(JsonPlaceholderAPI::class.java)
    }
//
//    suspend fun getUser5(): Call<User> {
//        return createClient().getUser(5)
//    }
}


fun main() {
    println("start")
     runBlocking {
         val defer = async {
             val userApiClient = UserApiClient().createClient()

             val user = userApiClient.getUser(5)

             if (user != null) {
                 println("User: $user")
             } else {
                 println("Failed to fetch user data")
             }

             println("end: runBlocking")
             user.await()
         }
         val user = defer.await()
         println("await User: $user")
         println("end: runBlocking")
//         Terminate the process
//         System.exit(0)
     }
    println("done")
}
