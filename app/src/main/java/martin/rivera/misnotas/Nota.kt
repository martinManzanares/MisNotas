package martin.rivera.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import java.io.File

data class Nota (var titulo: String, var contenido: String)

class AdaptadorNotas: BaseAdapter {
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View{
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[p0]

        vista.tv_titulo_det.text = nota.titulo
        vista.tv_contenido_det.text = nota.contenido

        vista.btn_borrar.setOnClickListener{
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    override fun getItem(p0: Int): Any{
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }

    private fun eliminar(titulo: String){
        if (titulo == ""){
            Toast.makeText(context, "Error: Titulo Vacio", Toast.LENGTH_SHORT).show()
        }else{
            try {
                //Elimina el archivo con el titulo seleccionado
                val archivo = File(ubicacion(), titulo+".txt")
                archivo.delete()

                Toast.makeText(context, "Se Elimino el archivo", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String{
        val album = File(contexto?.getExternalFilesDir(null), "notas")
        if (!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }
}