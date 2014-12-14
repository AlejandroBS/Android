package com.test.alejandro.test;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class ResultadoTest{
    private char chr_respuestaEscogida;
    private String str_respuestaEscogida;
    private char chr_respuestaCorrecta;
    private String str_respuestaCorrecta;
    private String enunciado;
    private int numPregunta;

    public ResultadoTest(char chr_respuestaEscogida, String str_respuestaEscogida, char chr_respuestaCorrecta, String str_respuestaCorrecta, String enunciado, int numPregunta) {
        this.chr_respuestaEscogida = chr_respuestaEscogida;
        this.str_respuestaCorrecta = str_respuestaCorrecta;
        this.str_respuestaEscogida = str_respuestaEscogida;
        this.chr_respuestaCorrecta = chr_respuestaCorrecta;
        this.enunciado = enunciado;
        this.numPregunta = numPregunta;
    }

    public char getChr_respuestaEscogida() {
        return chr_respuestaEscogida;
    }

    public void setChr_respuestaEscogida(char chr_respuestaEscogida) {
        this.chr_respuestaEscogida = chr_respuestaEscogida;
    }

    public String getStr_respuestaEscogida() {
        return str_respuestaEscogida;
    }

    public void setStr_respuestaEscogida(String str_respuestaEscogida) {
        this.str_respuestaEscogida = str_respuestaEscogida;
    }

    public char getChr_respuestaCorrecta() {
        return chr_respuestaCorrecta;
    }

    public void setChr_respuestaCorrecta(char chr_respuestaCorrecta) {
        this.chr_respuestaCorrecta = chr_respuestaCorrecta;
    }

    public String getStr_respuestaCorrecta() {
        return str_respuestaCorrecta;
    }

    public void setStr_respuestaCorrecta(String str_respuestaCorrecta) {
        this.str_respuestaCorrecta = str_respuestaCorrecta;
    }

    public int getNumPregunta() {
        return numPregunta;
    }

    public void setNumPregunta(int numPregunta) {
        this.numPregunta = numPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }



}
