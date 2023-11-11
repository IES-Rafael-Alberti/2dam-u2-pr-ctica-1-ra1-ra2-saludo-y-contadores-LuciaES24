package com.lespsan543.saludocontadores.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
@Preview(showBackground = true)
fun Inicio(){
    var textoSaludo by rememberSaveable { mutableStateOf("") }
    var show by rememberSaveable { mutableStateOf(false) }
    var contaAceptar by rememberSaveable { mutableStateOf(0) }
    var contaCancelar by rememberSaveable { mutableStateOf(0) }

    PantallaBoton(texto = textoSaludo,
        show = false,
        onDismiss = { show = true } )

    if (show != false){
        Dialogo(show = show,
            contaAceptar = contaAceptar,
            contaCancelar = contaCancelar,
            sumar = {
                    if (contaAceptar == it ){
                        contaAceptar++
                    }else if(contaCancelar == it){
                        contaCancelar++
                    }
            },
            onDismiss = {show = true},
            onDismissOn = {show = false},
            onChange = { textoSaludo = it },
            onChangeName = { if (textoSaludo!=""){
                    textoSaludo = "Hola $textoSaludo"
                }
            },
            onChangeClear = { textoSaludo = "" },
            texto = textoSaludo)
    }
}

/**
 * @param texto cadena con el saludo y el nombre
 * @param show booleano que determina si se tiene que mostrar el Dialog o no
 * @param onDismiss hace que aparezca el Dialogo en la pantalla
 */
@Composable
fun PantallaBoton(
    texto : String,
    show: Boolean,
    onDismiss : (Boolean) -> Unit){

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onDismiss(show)},
            Modifier.padding(bottom = 10.dp))
        {
            Text(text = "Saludar")
        }
        Text(text = texto,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(2.dp, Color.Black)
                .height(50.dp)
                .width(250.dp)
                .wrapContentHeight(Alignment.CenterVertically))
    }
}

/**
 * @param show booleano que determina si se tiene que mostrar el Dialog o no
 * @param contaAceptar contador con el número de veces que se pulsó "Aceptar"
 * @param contaCancelar contador con el número de veces que se pulsó "Cancelar"
 * @param sumar suma 1 al contador que se le indique
 * @param onDismiss mantiene el Dialogo en la pantalla
 * @param onDismissOn quita el diálogoo de la pantalla
 * @param onChange guarda en 'texto' lo que escribe el usuario
 * @param onChangeName añade "Hola " al nombre si se ha introducido uno
 * @param onChangeClear elimina el texto que ha escrito el usuario
 * @param texto texto que escribe el usuario
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogo(show : Boolean,
            contaAceptar : Int,
            contaCancelar : Int,
            sumar : (Int) -> Unit,
            onDismiss : (Boolean) -> Unit,
            onDismissOn : (Boolean) -> Unit,
            onChange: (String) -> Unit,
            onChangeName : (String) -> Unit,
            onChangeClear : (String) -> Unit,
            texto: String){
    Dialog(onDismissRequest = { onDismiss(show)},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false)) {
        Column(modifier = Modifier
            .background(Color.Gray)
            .padding(top = 15.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Configuración",
                modifier = Modifier
                    .padding(bottom = 15.dp, start = 185.dp),
                color = Color.White,
                fontSize = 20.sp)

            OutlinedTextField(
                value = texto,
                onValueChange = { onChange(it)},
                label = { Text(text = "Introduce tu nombre")},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White))

            Row(Modifier.padding(top = 60.dp))
            {
                Button(onClick = { onDismissOn(show)
                                 onChangeName(texto)
                                 sumar(contaAceptar)},
                    Modifier.padding(end = 30.dp)) {
                    Text(text = "A$contaAceptar")
                }
                Button(onClick = { onChangeClear(texto) },
                    Modifier.padding(end = 30.dp)) {
                    Text(text = "L")
                }
                Button(onClick = { onDismissOn(show)
                                 sumar(contaCancelar)}) {
                    Text(text = "C$contaCancelar")
                }
            }
        }
    }
}