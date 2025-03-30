# Api do generowania dokumentów przez kierowców przy odbiorze oleju z restauracji

- Możliwosć dodania klienta po NIPie poprzez GET /nip1234567890 pobierając jego dane z rejestr.io
lub stworzyć klienta wpisując na sztywno dane potrzebne do faktury

- możemy wyszukać klienta po dowolnym fragmencie jego nazwy, adresu, nipu np. po wpisaniu "Grudz" 
wyswietli nam wszystkich klientow ktorzy maja w danych taki ciag znakow

- Mamy możliwość zarejestrowania użytkownika jako kierowce lub admina. Encje róźnią się tylko tym, że 
kierowca ma saldo

- Kierowca po odebraniu oleju wpisuje klienta, ilosc odebranych kg, cene netto za kg
generuje nam sie odbior, ktory zawiera kierowce, klienta, wygenerowana automatycznie fakture
i karte przekazania odpadu, date, ilosc kg, cene netto za kg, wartosc netto i brutto
- wygenerowana faktura ma dane klienta, date, indyfidualny numer, ilosc kg, cena jednostkowa netto,
calosciowa netto i brutto
- wygenerowana karta odpadu ma indywidualny numer taki jak faktury z "/S/", date, dane klienta, wage w Mg
  (100kg = 0,1Mg), numer rejestracyjny kierowcy
- Kierowca w trakcie dnia pracy ma różne wydatki. Ma możliwość wpisania ich do systemu.
Musi wpisać cenę brutto, %VAT, i opis np. "Paliwo", "Prostytutka z ulicy"
- Kierowca na koniec dnia generuje rejestr dzienny. Rejestr zawiera kierowce, całą listę odbiórów i wydatków
kierowcy, saldo początkowe i końcowe, ilość zebranych kg, suma brutto wydana na olej, suma netto na 
dodatkowe wydatki

- Admin ma możliwość dodać do systemu samochód i przypisać go do kierowcy
- może modyfikować saldo kierowcy 

######################################################################################

- walidacja salda kierowcy (nie może wykonać odbioru/dodatkowego wydatku jeśli wydatek jest wyższy 
niż dostępne środki)
- po wygenerowaniu rejestru jest on automatycznie wysyłany na jakiegoś maila
- admin ma wgląd do wszystkich rejestrów, wydatków, odbiorów i dokumentow kierowców 
- admin ma możliwość edytowania dowolnych parametrów odbiorów, wydatków i rejetrów 
u kierowców i w razie potrzeby może je usunąć
- moze modyfikowac dane klientów
- testy jednostkowe
- testy integracyjne
- globalExpception 
- walidacja dlugosci nipu przy tworzeniu nowego klienta
- jesli do 23:59 kierowca nie zrobi rejstru to wykona się automatycznie 
- po nowym roku numeracja faktur zaczyna sie od 1
- walidacja posiadania samochodu przez kierowce, jesli nie ma samochodu nie moze wykonac odbioru
- #   o i l - a p i  
 