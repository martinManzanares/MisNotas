package martin.rivera.misnotas

import android.app.appsearch.SearchResults
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.FileOutputStream

class AgregarNotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        btn_guardar.setOnClickListener {
            guardar_nota();
        }
    }

    fun guardar_nota(){
        //Verifica que tenga los permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ){
            //Si no los tiene, los pide al usuario
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235
            )
        } else {
            guardar()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permission: Array<String>, grantResults: )
        when (requestCode) {
            235 -> {
                //Pregunta si el usuario aceptó los permisos
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    guardar()
                } else {
                    //Si no aceptó, coloca el mensaje
                    Toast.makeText(this, "Error: Permisos Denegados", Toast.LENGTH_SHORT).show()
                }
        }
}

public fun guardar(){
    var titulo = et_titulo.text.toString()
    var cuerpo = et_contenido.text.toString()
    if (titulo == "" || cuerpo == "") {
        Toast.makeText(this, "Error: Campos Vacios", Toast.LENGTH_SHORT).show()
    }else{
        try {
            val archivo = File(ubicacion(), titulo + ".txt")
            val fos = FileOutputStream(archivo)
            fos.write(cuerpo.toByteArray())
            fos.close()
            Toast.makeText(
                this,
                "Se Guardo el archivo en la carpeta publica",
                Toast.LENGTH_SHORT
            ).show()
        }catch (e: java.lang.Exception){
            Toast.makeText(this, "Error: No se guardó el archivo", Toast.LENGTH_SHORT).show()
        }
    }
    finish()
}

private fun ubicacion(): String {
    val carpeta = File(getExternalFilesDir(null), "notas")
    if (!carpeta.exists()){
        carpeta.mkdir()
    }
    return carpeta.absolutePath
}