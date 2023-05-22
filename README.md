# TicketMaster

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

| Ścieżka                                                            | HTTP   | Opis                                              |
|--------------------------------------------------------------------|--------|---------------------------------------------------|
| /locations                                                         | GET    | zwrócenie wszystkich lokacji                      |
| /locations/{id}                                                    | GET    | zwrócenie podstawowych informacji o danej lokacji |
| /locations/{id}                                                    | POST   | dodanie nowej lokacji                             |
| /locations/{id}                                                    | PATCH  | aktualizacja informacji o lokacji                 |
| /locations/{id}                                                    | DELETE | usunięcie lokacji                                 |
| /locations/{id}/zones                                              | POST   | dodanie strefy                                    |
| /locations/{id}/zones/{id}                                         | DELETE | usunięcie strefy                                  |
| /locations/{id}/zones                                              | GET    | zwrócenie wszystkich stref                        |
| /locations/{id}/concerts                                           | GET    | zwrócenie wszystkich wydarzeń w danej lokacji     |
| /locations/{id}/concerts/{id}                                      | GET    | szczegóły wydarzenia                              |
| /locations/{id}/concerts/{id}                                      | POST   | dodanie wydarzenia                                |
| /locations/{id}/concerts/{id}                                      | PATCH  | aktualizacja wydarzenia                           |
| /locations/{id}/concerts/{id}                                      | DELETE | usunięcie wydarzenia                              |
| /locations/{id}/concerts/{id}/tickets                              | GET    | wyświetlenie dostępnych biletów                   |
| /locations/{id}/concerts/{id}/tickets/{locationZoneId}/reservation | POST   | rezerwacja biletu                                 |
| /locations/{id}/concerts/{id}/tickets/{locationZoneId}/purchase    | POST   | zakup biletu                                      |
| /users/login                                                       | POST   | logowanie                                         |
| /users/register                                                    | POST   | rejestracja                                       |
| /users/wallet/add                                                  | POST   | dodanie środków                                   |
| /users/wallet                                                      | GET    | stan środków                                      |
