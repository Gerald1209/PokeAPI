package ni.edu.uca.pokeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import ni.edu.uca.pokeapi.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            buscarDatosPokemon()
        }
    }

    fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     fun buscarDatosPokemon() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val txtFiltro: String = binding.edtxNumeroPokemon.text.toString()
                val call = getRetrofit().create(ApiService::class.java).getDatosPokemon(txtFiltro)

                if (call.isSuccessful){
                    val id: Int? = call.body()?.id?.toInt()
                    val nombre:String = call.body()?.name.toString()
                    val exp:String = call.body()?.base_experience.toString()
                    val peso:String = call.body()?.weight.toString()
                    val altura:String = call.body()?.height.toString()
                    binding.tvNombre.text = "$nombre"
                    binding.tvExp.text = "$exp"
                    binding.tvPeso.text = "$peso Kg"
                    binding.tvAltura.text = "$altura cm"

                    Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png").into(binding.imgPokemon)
                }else {
                    val msn = Toast.makeText(this@MainActivity, "No encontrado", Toast.LENGTH_LONG)
                    msn.setGravity(Gravity.CENTER, 0, 0)
                    msn.show()
                }
            }catch (ex: Exception){
                val msn = Toast.makeText(this@MainActivity,"Error de conexion",Toast.LENGTH_LONG)
                msn.setGravity(Gravity.CENTER,0,0)
                msn.show()
            }
        }
    }
}
