package com.ds.qqpega.mask
class Mask {
    companion object {
        fun addMask(textoAFormatar: String, mask: String): String? {
            var formatado: String? = ""
            var i = 0
            // vamos iterar a mascara, para descobrir quais caracteres vamos adicionar e quando...
            for (m in mask.toCharArray()) {
                if (m != '#') { // se não for um #, vamos colocar o caracter informado na máscara
                    formatado += m
                    continue
                }
                // Senão colocamos o valor que será formatado
                formatado += try {
                    textoAFormatar[i]
                } catch (e: Exception) {
                    break
                }
                i++
            }
            return formatado
        }
    }
}