package com.example.simondice

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    var iniciar: Button? = null
    var retardo1 = Handler()
    var retardo2 = Handler()
    lateinit var botones: Array<Button?>
    lateinit var sonido: Array<MediaPlayer?>
    lateinit var numeros: IntArray
    lateinit var ordenJugador: IntArray
    var botonesPulsados = 0
    private var tiempoEncendido = 1000
    private var tiempoApagado = 1500
    var enabledPlay = false

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ordenJugador = IntArray(4)
        numeros = IntArray(4)
        botones = arrayOfNulls(4)
        iniciar = findViewById(R.id.bplay) as Button?
        botones[0] = findViewById(R.id.bazul) as Button?
        botones[1] = findViewById(R.id.brojo)  as Button?
        botones[2] = findViewById(R.id.bverde) as Button?
        botones[3] = findViewById(R.id.bamarillo) as Button?
        sonido = arrayOfNulls(4)
        sonido[0] = MediaPlayer.create(this, R.raw.moneda)
        sonido[1] = MediaPlayer.create(this, R.raw.saltolargo)
        sonido[2] = MediaPlayer.create(this, R.raw.vaja)
        sonido[3] = MediaPlayer.create(this, R.raw.ladrilloroto)
    }

    fun clickar(v: View) {
        val indice: Int
        val id = v.id
        val b = v as Button
        indice = if (id == R.id.bazul) {
            v.setBackgroundResource(R.color.azulfuerte)
            sonido[0]!!.start()
            0
        } else if (id == R.id.brojo) {
            v.setBackgroundResource(R.color.rojofuerte)
            sonido[1]!!.start()
            1
        } else if (id == R.id.bverde) {
            v.setBackgroundResource(R.color.verdefuerte)
            sonido[2]!!.start()
            2
        } else {
            v.setBackgroundResource(R.color.amarillofuerte)
            sonido[3]!!.start()
            3
        }
        val handler = Handler()
        handler.postDelayed({ resetear(b.id) }, 500)
        if (enabledPlay) {
            ordenJugador[botonesPulsados] = indice
            botonesPulsados++
            if (botonesPulsados == 4) {
                check()
            }
        }
    }

    fun iniciar(v: View?) {
        enabledPlay = true
        for (i in numeros.indices) {
            numeros[i] = (Math.random() * 4).toInt()
            val b = botones[numeros[i]]
            println(numeros[i])
            if (b!!.id == R.id.bazul) {
                retardo1.postDelayed({
                    b.setBackgroundResource(R.color.azulfuerte)
                    sonido[0]!!.start()
                }, tiempoEncendido.toLong())
            } else if (b.id == R.id.brojo) {
                retardo1.postDelayed({
                    b.setBackgroundResource(R.color.rojofuerte)
                    sonido[1]!!.start()
                }, tiempoEncendido.toLong())
            } else if (b.id == R.id.bverde) {
                retardo1.postDelayed({
                    b.setBackgroundResource(R.color.verdefuerte)
                    sonido[2]!!.start()
                }, tiempoEncendido.toLong())
            } else {
                retardo1.postDelayed({
                    b.setBackgroundResource(R.color.amarillofuerte)
                    sonido[3]!!.start()
                }, tiempoEncendido.toLong())
            }
            retardo2.postDelayed({ resetear(b.id) }, tiempoApagado.toLong())
            tiempoEncendido += 1500
            tiempoApagado += 1000
        }
        tiempoEncendido = 1500
        tiempoApagado = 1000
    }

    fun resetear(id: Int) {
        if (id == R.id.bazul) {
            botones[0]!!.setBackgroundResource(R.color.azul)
        } else if (id == R.id.brojo) {
            botones[1]!!.setBackgroundResource(R.color.rojo)
        } else if (id == R.id.bverde) {
            botones[2]!!.setBackgroundResource(R.color.verde)
        } else {
            botones[3]!!.setBackgroundResource(R.color.amarillo)
        }
    }

    fun check() {
        var acertados = 0
        for (i in numeros.indices) {
            if (numeros[i] != ordenJugador[i]) {
                Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show()
            } else {
                acertados++
            }
            if (acertados == 4) {
                Toast.makeText(this, "Has ganado", Toast.LENGTH_SHORT).show()
            }
            enabledPlay = false
            botonesPulsados = 0
            numeros = IntArray(4)
            ordenJugador = IntArray(4)
        }
    }
}