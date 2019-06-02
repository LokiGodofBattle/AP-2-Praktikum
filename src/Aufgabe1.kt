import kotlin.math.ceil

fun main() {

    var parkhaus = Parkhaus()

    parkhaus.addParkschein(Parkschein(Uhrzeit(2, 30), Uhrzeit(3, 30)))
    parkhaus.addParkschein(Parkschein(Uhrzeit(3, 30), Uhrzeit(4, 30)))
    parkhaus.addParkschein(Parkschein(Uhrzeit(4, 30), Uhrzeit(5, 30)))
    parkhaus.addParkschein(Parkschein(Uhrzeit(5, 30), Uhrzeit(6, 30)))
    parkhaus.addParkschein(Parkschein(Uhrzeit(6, 30), Uhrzeit(7, 30)))

    println("Die Kuerzeste Parkzeit ist: ${parkhaus.kuerzesteParkzeit()} Minuten")
    println("Die durchschnittliche Parkzeit ist: ${parkhaus.durchschnittlicheParkzeit().toInt()} Minuten")
    println("Die Einnahmen sind: ${parkhaus.einnahmen()}€")


    checkScheine(parkhaus)

    parkhaus.addParkschein(Parkschein(Uhrzeit(6, 30), Uhrzeit(5, 30)))

    checkScheine(parkhaus)
}

fun checkScheine(parkhaus: Parkhaus) : Unit{
    if(parkhaus.ueberpruefen()) println("Alles in Ordnung")
    else println("Mind. ein Parkschein ist nicht in Ordnung")
}

open class Uhrzeit(val stunden : Int, val minuten : Int){
    init {

    }
}

open class Parkschein(val einfahrtZeit : Uhrzeit, var ausfahrtZeit : Uhrzeit){
    init {

    }

    open fun parkzeit() : Int{
        return ausfahrtZeit.stunden*60 + ausfahrtZeit.minuten - (einfahrtZeit.stunden*60 + einfahrtZeit.minuten)
    }

    open fun angefangeneStunden() : Int{
        return ceil(parkzeit().toDouble()/60).toInt()
    }
}

open class Parkhaus(){
    var parkscheine = arrayListOf<Parkschein>()
    val parktarif = 3.5

    init {

    }

    open fun addParkschein(parkschein: Parkschein) : Unit{
        parkscheine.add(parkschein)
    }

    open fun kuerzesteParkzeit() : Int{
        var kuerzesteZeit = Int.MAX_VALUE

        for(parkschein in parkscheine) if(parkschein.parkzeit() < kuerzesteZeit) kuerzesteZeit = parkschein.parkzeit()

        return kuerzesteZeit
    }

    open fun durchschnittlicheParkzeit() : Double{
        var parkzeitSumme = 0

        for(parkschein in parkscheine) parkzeitSumme += parkschein.parkzeit()

        return (parkzeitSumme.toDouble()/parkscheine.size)
    }

    open fun einnahmen() : Double{
        var einnahmen : Double = 0.0

        for(parkschein in parkscheine) einnahmen += parkschein.angefangeneStunden() * parktarif

        return einnahmen
    }

    open fun ueberpruefen() : Boolean{
        var correct = true

        for(parkschein in parkscheine){
            if(parkschein.ausfahrtZeit.stunden*60 + parkschein.ausfahrtZeit.minuten
                - (parkschein.einfahrtZeit.stunden*60 + parkschein.einfahrtZeit.minuten)
                <= 0) {
                correct = false
            }
        }

        return correct
    }

}