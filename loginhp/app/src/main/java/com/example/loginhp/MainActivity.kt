import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewMessage = findViewById<TextView>(R.id.textViewMessage)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Validar el inicio de sesión
            if (email == "usuario@example.com" && password == "123456") {
                textViewMessage.text = "Felicidades"
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            } else {
                textViewMessage.text = "Correo o contraseña incorrectos"
                Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}