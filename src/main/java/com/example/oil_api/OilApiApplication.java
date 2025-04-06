package com.example.oil_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

//kolejnosc adnotacji wg dlugosci - czepialstwo, ale zwracaja na to niektorzy uwage
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class OilApiApplication {

    /*
    Poczytaj o architekturze modularnej koniecznie - teraz juz sie nie uzywa takiego pakietowania jak napisales.
    Wiem, ze tak jest na kursie, ale byscie sie pogubili jakbyscie mieli inna architekture na start, bo ta jest podstawowa
    i najprostsza (monolit niemodularny), ale powoduje, ze wszystko jest publicze i sa zależności każdy z każdym
    Obejrz sobie https://www.youtube.com/watch?v=ILBX9fa9aJo&pp=ygUUbW9kdWxhcml0eSBuYWJyZGFsaWs%3D
    Nie zrozumiesz wszystkiego, ale kluczowy będzie fragment jak będzie właśnie opowiadał o pakietach jako modułach

    PODSUMOWANIE:
    KONIECZNIE TESTY, przynajmniej jednostkowe - każdy scenariusz musi być przetestowany
    Poczytaj o SOLID, szczególnie pierwsza reguła jest bardzo ważna
    Wydzielaj jak najwięcej do metod części wspólnych
    Bądź konsekwentny w jednym podejściu
    Patrzyłem tylko technicznie, nie wdrażałem się w modelowanie całości, ale dla mnie, patrząc na zależności, trzeba to poprawić
    i uprościć proces.

    Ale generalnie gratki za całość i super, że to zrobiłeś
     */
    public static void main(String[] args) {
        SpringApplication.run(OilApiApplication.class, args);
    }
}
