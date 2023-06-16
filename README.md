# TicketMaster
[![Java CI with Gradle](https://github.com/uj-wmii-jwzp-2023/TicketMaster/actions/workflows/gradle.yml/badge.svg)](https://github.com/uj-wmii-jwzp-2023/TicketMaster/actions/workflows/gradle.yml)

Aplikacja pozwalająca na rezerwację i zakup biletów na wydarzenia odbywające się w różnych lokacjach.

### Użytkownik (users, role = "user")

Osoba zalogowana do aplikacji. Może przeglądać przyszłe wydarzenia, rezerwować i kupować bilety na wybrane wydarzenia. W bazie danych zapisany w tabeli `users` z rolą ustawioną na "user".

### Administrator (users, role = "admin")

Osoba posiadająca dodatkowe uprawnienia, takie jak dodawanie/usuwanie czy aktualizowanie wydarzeń i lokacji. W bazie danych zapisany w tabeli `users` z rolą ustawioną na "admin".

### Miejsca (locations)

Informacje o miejscach, w których odbywać się będą koncerty/wydarzenia, na które bedzie można kupić bilety. Zawierają podstawowe informacje o miejscu jak nazwa i adres.
Każde
### Strefy (location_zones)

Każde miejsce podzielone jest na różne strefy oznaczone typowo kolejnymi wielkimi literami alfabetu (pole `zone`). Strefa dzieli dostępne siedzenia w miejscu, dla których cena biletu jest taka sama. Im wcześniejsza litera alfabetu tym wyższa klasa biletu (np. bilet pod sceną) oraz wyższa cena. Liczba miejsc (`seats`) jest określona w strefie, natomiast cena dla danej strefy jest zależna od danego koncertu.

### Pule biletów (ticket_pools)

Pule biletów przypisują cenę w danej strefie dla danego koncertu. Liczba miejsc, a zatem liczba dostępnych biletów jest zapisana w strefie.

### Bilety (tickets)

Bilety można wykupić na dane wydarzenie, na miejsce w konkretnej strefie od której zależy cena biletu. Tabela `tickets` przechowuje bilety zarezerwowane oraz już zakupione przez danego użytkownika.

### Kupowanie biletów

Aby dokonać zakupu biletu należy dokonać jego rezerwacji (która jest ważna przez określony czas), a następnie
zapłacić za bilet za pomocą środków znajdujących się w portfelu użytkownika - należy wcześniej doładować
odpowiednią kwotą portfel.

## Diagram bazy danych:
![](./docs/db-diagram.drawio.svg)

## Endpointy
Dokumentacja wszystkich ścieżek jest dostepna również jako automatycznie wygenerowana [specyfikacja OpenAPI](http://localhost:8080/v3/api-docs), którą można zobaczyć poprzez Swagger UI wchodząc na [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).   

| Ścieżka                                            | HTTP   | Opis                                              | Uprawnieni użytkownicy |
|----------------------------------------------------|--------|---------------------------------------------------|------------------------|
| /locations                                         | GET    | zwrócenie wszystkich lokacji                      | ADMIN, USER            |
| /locations/{id}                                    | GET    | zwrócenie podstawowych informacji o danej lokacji | ADMIN, USER            |
| /locations                                         | POST   | dodanie nowej lokacji                             | ADMIN                  |
| /locations/{id}                                    | PATCH  | aktualizacja informacji o lokacji                 | ADMIN                  |
| /locations/{id}                                    | DELETE | usunięcie lokacji                                 | ADMIN                  |
| /locations/{id}/zones                              | POST   | dodanie strefy                                    | ADMIN                  |
| /locations/{id}/zones/{id}                         | DELETE | usunięcie strefy                                  | ADMIN                  |
| /locations/{id}/zones                              | GET    | zwrócenie wszystkich stref                        | ADMIN, USER            |
| /locations/{id}/concerts                           | GET    | zwrócenie wszystkich wydarzeń w danej lokacji     | ADMIN, USER            |
| /locations/{id}/concerts/{id}                      | GET    | szczegóły wydarzenia                              | ADMIN, USER            |
| /locations/{id}/concerts                           | POST   | dodanie wydarzenia                                | ADMIN                  |
| /locations/{id}/concerts/{id}                      | DELETE | usunięcie wydarzenia                              | ADMIN                  |
| /concerts/{id}/tickets                             | GET    | wyświetlenie zakupionych/zarezerwowanych biletów  | ADMIN            |
| /concerts/{id}/tickets/{locationZoneId}/reservation | POST   | rezerwacja biletu                                 | ADMIN, USER            |
| /concerts/{id}/tickets/{locationZoneId}/purchase   | POST   | zakup biletu                                      | ADMIN, USER            |
| /users                                             | GET    | zwrócenie wszystkich użytkowników                 | ADMIN                  |
| /login                                             | POST   | logowanie                                         | -                      |
| /mytickets                                         | GET    | zwrócenie wszystkich biletów użytkownika          | USER, ADMIN            |
| /register                                          | POST   | rejestracja                                       | -                      |
| /wallet/add                                        | POST   | dodanie środków                                   | ADMIN, USER            |
| /wallet                                            | GET    | stan środków                                      | ADMIN, USER            |


## Obraz Docker

### Budowanie obrazu
```
docker build -t ticketmaster .
```

### Uruchamianie całego systemu
W celu uruchomienia aplikacji wraz z bazą danych w kontenerach możemy użyć narzedzia docker-compose:
```
docker-compose up
```
Zostanie zbudowany obraz aplikacji, utworzona izolowana sieć oraz uruchomione kontenery aplikacji oraz bazy PostgreSQL.  
Żeby uruchomić aplikację w tle (daemonized), można użyć argumentu `-d`:
```
docker-compose up -d
```
Dane bazy są przechowywane w wolumenie Dockerowym w folderze `./data/db`.  
W celu wymuszenia przebudowania obrazu, gdy istnieje on już w systemie, należy użyć opcji `--build`.

### Uruchamianie konetera aplikacji ręcznie
```
docker run --name ticketmaster-app -e POSTGRES_HOST=localhost -e POSTGRES_DB=ticketmaster -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 8080:8080 ticketmaster
```

## Konfiguracja aplikacji
Aplikacja zarówno uruchamiana "bare-metal" jak i w kontenerze (docker run oraz docker-compose) jest konfugurowana przy użyciu zmiennych środowiskowych. Wartości w nawiasach są domyślne.  
Przykładowe wartości zmiennych można znaleźć w pliku [`.env.sample`](.env.sample). Można go skopiować do pliku `.env` oraz dostosować w celu użycia tych zmiennych środowiskowych w docker-compose (są one wczytywane automatycznie).

#### Ogólne:
* `API_PORT` _(8080)_ – port na którym nasłuchiwać będzie aplikacja
#### Baza danych:
* `POSTGRES_USER` _(postgres)_ – nazwa użytkownika Postgres
* `POSTGRES_PASSWORD` _(postgres)_ – hasło dla użytkownika Postgres
* `POSTGRES_HOST` _(localhost)_ – adres serwera Postgres
* `POSTGRES_PORT` _(5432)_ – port na którym nasłuchuje Postgres
* `POSTGRES_DB`_(ticketmaster)_ – nazwa bazy danych
