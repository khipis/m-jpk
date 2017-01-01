README for mjpk 0.2.0
==========================
Integracja z mFirma -> Jednolity Plik Kontrolny

Obecnie dostepne funkcjonalnosci
    
    -formalna walidacja pliku na podstawie schemy

Z punktu widzenia wdrozenia najwazniejszy jest katalog release, opis zawartosci:

    -input-files : katalog na uzytek testow, zawiera przykladowy plik do zwalidowania
    -schemes : katalog z plikami scheme sluzacymi do walidacji (obecnie tylko JPK_VAT)
    -working-dir : katalog na uzytek testow, w katalogu zapisywane sa wszystkie 
        operacje przeprowadzane na pliku JPK takie jak walidacja i w pozniejszym etapie: zip, aes, base64 itd. (powinna utworzyc sie podobna struktura do plikow w katalog input-files)
    -jre : katalog zawierajacy rozne wersje srodowiska uruchomieniowego wirtualnej maszyny Java
    -mjpk.jar : glowny plik aplikacji 
    -config.properties : przykladowy plik konfiguracyjny
    -mjpk.log : Plik logowania, w ktorym zapisane sa wszystkie operacje wykonywane przez aplikacje
    -mjpk-jre*-*bit.bat : Skrypty pokazujace jak uruchomic aplikacje z wybrana wersja Javy 
    

NAJNOWSZA WERSJA
==========================
Najnowsza wersja znajduje sie pod adresem: https://github.com/khipis/m-jpk

Najlatwiej pobrac ja uzywajac aplikacji git.

Komenda:

    git clone https://github.com/khipis/m-jpk.git
    
Utworzy folder m-jpk i pobierze aplikacje

    git pull
    
Pobierze najswiezsza zawartosc.

Alternatywnym rozwiazaniem jest klikniecie zielonego przycisku "Clone or download"
i pobranie 
    
Aplikacja jest wyslana do repozytorium razem z binarnymi plikami, aby nie zmuszac
uzytkownikow do budowania aplikacji we wlasnym zakresie. (katalog release)
 
JRE
==========================
Aplikacja jest kompilowana z wersja bytecode 1.7, co oznacza ze moze byc uruchamiana zarowno 
na wersji 8 oraz 7. Wynika to z faktu potrzeby uruchamiania aplikacji na systemach Windows XP.

Kolejnym problemem jest dostepnosc Javy na komputerze klienta (moze byc zainstalowana ale nie musi)
Bezpieczniejszym rozwiazaniem jest zatem dostarczenie JRE (Java Runtime Environment) wraz z aplikacja.

Architektura systemow klienta moze byc zarowno 32 jak i 64 bitowa. Z tego powodu w katalogu JRE
znajduja sie 4 wersje Javy 7,8 32 bit oraz 7,8 64bit. Z kazda aplikacja mjpk moze wspolpracowac.

W docelowej integracji z systemem, powinna byc mozliwosc wyboru wersji JRE dostarczonej
z aplikacja lub tez zainstalowanej u klienta.

(Wersje dostarczone z aplikacja nie musza byc instalowane!)



