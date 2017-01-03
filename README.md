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
    
Pobierze aktualna zawartosc.

Alternatywnym rozwiazaniem jest klikniecie zielonego przycisku "Clone or download"
i pobranie 
    
Aplikacja jest wyslana do repozytorium razem z binarnymi plikami, aby nie zmuszac
uzytkownikow do budowania aplikacji we wlasnym zakresie. (katalog release)
 
PLIK KONFIGURACYJNY
==========================
Sterowanie aplikacja odbywa sie poprzez plik konfiguracyjny. Aplikacja pobiera plik konfiguracyjny podany jako argument:

    java -jar mjpk.jar "PATH\config.properties"
    
Nazwa i rozszerzenie pliku moga byc dowolne. W przypadku braku podania argumentu aplikacja sproboje otworzyc plik "config.properties"
z katalogu w ktorym zostala uruchomiona. Jezeli go nie znajdzie zaladuje defaultowe wartosci (w celach developerskich) lecz zostanie to usuniete z wersji ostatecznej.

Przykladowy zawartosc pliku konfiguracyjnego:
    
    working.directory.path=Sciezka do katalogu w ktorym mjpk bedzie zapisywac wyniki swojej pracy
    scheme.file.path=Sciezka do pliku scheme sluzacego do walidacji wygenerowanego pliku JPK
    input.file.path=Sciezka do pliku xml na ktorym aplikacja przeprowadzi swoje operacje
    
Sciezke nalezy rozdzielac podwojnym \\ przyklad -> C:\\windows\\temp (Do potwierdzenia)
    
Wszystkie nowe parametry beda dodawane w tym pliku. (np wartosc bool czy wykonywac formalna walidacje, parametry polaczenia z brama jpk)

URUCHAMIANIE APLIKACJI
==========================
W katalogu release przygotowane sa 4 skrypty uruchamiajace aplikacje na dostarczonej JRE. Przyklad zawartosci pliku:
    
    G:\work\m-jpk\release\jre\64bit\jre8\bin\java -jar mjpk.jar
    
W tym przypadku aplikacja pobierze zawartosc z pliku config.properties z biezacego katalogu. Uzywajac pliku ".bat" aplikacja otworzy
konsole w ktorej na biezaco bedzie wyswietlac zadanie ktorym w danej chwili sie zajmuje. Okno konsoli nie bedzie widoczne w przypadku
uruchomienia aplikacji jako proces z zewnetrznego systemu. Aplikacje mozna tez uruchomic dwukrotnie na nia klikajac (config.properties takze zostanie pobrany z biezacego katalogu). W kazdym z tych przypadkow aplikacja powinna utowrzyc plik mjpk.log (plik logow co aplikacja wykonywala w danym czasie).

Przewidywania konfiguracja w zewnetrznym systemie:

    executeProcess(PATH_TO_JRE + "java -jar mjpk.jar " + CONFIG_FILE_PATH);

Obecny przeplyw aplikacji:

    -parsuj plik konfiguracyjny
    -zwaliduj plik konfiguracyjny
        -sprawdz dostepnosc pliku inputFileName
        -sprawdz dostepnosc pliku schemeFileName
    -zwaliduj poprawnosc pliku schemeFileName
    -utworz lub wyczysc folder working.dir
    -przeprowadz walidacje pliku inputFileName
    -zapisz wynik walidacji jako plik inputFileName.xml.validation 
        -z zawartoscia "VALID" w przypadku poprawnego pliku 
        -z opisem bledu w przypadku niepoprawnego pliku
    -generuj 256 bitowy klucz i zapisz do pliku key
    -spakuj plik xml do pliku zip

JRE
==========================
Aplikacja jest kompilowana z wersja bytecode 1.7, co oznacza ze moze byc uruchamiana zarowno 
na wersji 8 oraz 7. Wynika to z faktu potrzeby uruchamiania aplikacji na systemach Windows XP.

Kolejnym problemem jest dostepnosc Javy na komputerze klienta (moze byc zainstalowana ale nie musi)
Bezpieczniejszym rozwiazaniem jest zatem dostarczenie JRE (Java Runtime Environment) wraz z aplikacja.

Architektura systemow klienta moze byc zarowno 32 jak i 64 bitowa. Z tego powodu w katalogu JRE
znajduja sie 4 wersje Javy 7,8 (32bit) oraz 7,8 (64bit). Z kazda aplikacja mjpk moze wspolpracowac.

W docelowej integracji z systemem, powinna byc mozliwosc wyboru wersji JRE dostarczonej
z aplikacja lub tez zainstalowanej u klienta.
(Wersje dostarczone z aplikacja nie musza byc instalowane!)
