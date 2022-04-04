package ni.edu.uca.pokeapi

import com.google.gson.annotations.SerializedName

data class ModelPokemon(
    @SerializedName("id") var id:Int?,
    @SerializedName("name") var name:String?,
    @SerializedName("base_experience") var base_experience:Int?,
    @SerializedName("height") var height:Float?,
    @SerializedName("weight") var weight:Float?
)
