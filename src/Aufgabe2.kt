import java.util.*
import kotlin.math.ceil

fun main(){

    val musikverwaltung = Musikverwaltung()

    val song1 = Song("Stronger", "Stonebank", 321, 80)
    val song2 = Song("Head of NASA", "Infected Mushroom", 466, 75)
    val song3 = Song("Insanity Syndrome", "The Enigma TNG", 276, 82)
    val song4 = Song("Spitfire", "Infected Mushroom", 435, 65)
    val song5 = Song("New Age", "Muzzy", 335, 90)
    val song6 = Song("Circle Hit", "Kansai Hardcores", 331, 60)
    val song7 = Song("Baphomet", "The Enigma TNG", 237, 95)
    val song8 = Song("Uta", "Imperial Circus Dead Decadence", 321, 85)

    musikverwaltung.addSong(song1)
    musikverwaltung.addSong(song2)
    musikverwaltung.addSong(song3)
    musikverwaltung.addSong(song4)
    musikverwaltung.addSong(song5)
    musikverwaltung.addSong(song6)
    musikverwaltung.addSong(song7)
    musikverwaltung.addSong(song8)

    var lieblingsLieder = Playlist(mutableListOf(song7, song8, song5))

    var zufallsListe = musikverwaltung.randomPlaylist()

    zufallsListe.playAll()
    lieblingsLieder.playAll()

    var besterSong = musikverwaltung.besterSong()
    println("Der bestbewertete Song ist ${besterSong.title} von ${besterSong.interpret} mit einer Bewertung von ${besterSong.bewertung} Punkten!")

    do{

        println("Bitte geben sie einen Suchbegriff ein:")
        val benutzereingabe = readLine().toString()

        if(benutzereingabe != "stopp"){
            val gesuchterSong = musikverwaltung.searchSong(benutzereingabe)

            gesuchterSong.abspielen()
        }

    } while(benutzereingabe != "stopp")

}


//-------------------------------Song-----------------------------------------------------------------------------------


open class Song(val title : String, val interpret : String, val spieldauer : Int, var bewertung : Int){
    init {
        checkBewertung()
    }

    open fun abspielen(){
        for (i in 1..(ceil(spieldauer.toDouble()/60).toInt())) println("Spiele $title von $interpret (Bewertung: $bewertung Punkte)")
    }

    open fun aendereBewertung(nbewertung : Int){
        bewertung = nbewertung
        checkBewertung()
    }

    open fun suche(begriff : String) : Boolean = (title.contains(begriff) || interpret.contains(begriff))

    private fun checkBewertung(){
        if(bewertung<0) bewertung = 0 else if(bewertung > 100) bewertung = 100
    }
}


//---------------------------------------Playlist-----------------------------------------------------------------------


open class Playlist(val list : MutableList<Song>){
    open fun getSpieldauer() : Int{
        var laenge = 0
        for(s in list) laenge+=s.spieldauer
        return laenge
    }

    open fun playAll(){
        for(s in list) s.abspielen()
    }
}


//---------------------------------------Musikverwaltung----------------------------------------------------------------


open class Musikverwaltung(){
    val songListe : MutableList<Song> = mutableListOf()

    open fun addSong(s : Song){
        songListe.add(s)
    }

    open fun searchSong(begriff : String) : Song{
        for(s in songListe){
            if(s.suche(begriff)) return s
        }

        return songListe.first()
    }

    open fun besterSong() : Song{
        var bester = songListe.first()
        for(s in songListe) if(s.bewertung > bester.bewertung) bester = s

        return bester
    }

    open fun randomPlaylist() : Playlist{
        var randomList = mutableListOf<Song>()

        val rdm = Random()

        for(i in 0..5) randomList.add(songListe[rdm.nextInt(songListe.size)])

        return Playlist(randomList)
    }
}