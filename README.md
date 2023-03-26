# TicketMaster

Aplikacja pozwalająca na rezerwację i zakup biletów na wydarzenia odbywające się w różnych lokacjach.

### Użytkownik

Osoba zalogowana do aplikacji. Może przeglądać przyszłe wydarzenia, rezerwować i kupować bilety na wybrane wydarzenia.

### Administrator

Osoba posiadająca dodatkowe uprawnienia, takie jak dodawanie/usuwanie czy aktualizowanie wydarzeń i lokacji.

### Bilety

Bilety podzielone są na kategorie oznaczone kolejnymi wielkimi literami alfabetu. 
Im wcześniejsza litera alfabetu tym wyższa klasa biletu (np. bilet pod sceną) oraz wyższa cena.
Każda z kategorii ma określoną pulę biletów.

### Kupowanie biletów

Aby dokonać zakupu biletu należy dokonać jego rezerwacji (która jest ważna przez określony czas), a następnie
zapłacić za bilet za pomocą środków znajdujących się w portfelu użytkownika - należy wcześniej doładować
odpowiednią kwotą portfel.

### Endpointy

| Ścieżka                                              | HTTP   | Opis                                    |
|------------------------------------------------------|--------|-----------------------------------------|
| /locations                                           | GET    | zwrócenie wszystkich lokacji            |
| /locations/{id}                                      | GET    | zwrócenie podstawowych informacji o danej lokacji |
| /locations/{id}                                      | POST   | dodanie nowej lokacji                   |
| /locations/{id}                                      | PATCH  | aktualizacja informacji o lokacji       |
| /locations/{id}                                      | DELETE | usunięcie lokacji                       |
| /locations/{id}/events                               | GET    | zwrócenie wszystkich wydarzeń w danej lokacji |
| /locations/{id}/events/{id}                          | GET    | szczegóły wydarzenia                    |
| /locations/{id}/events/{id}                          | POST   | dodanie wydarzenia                      |
| /locations/{id}/events/{id}                          | PATCH  | aktualizacja wydarzenia                 |
| /locations/{id}/events/{id}                          | DELETE | usunięcie wydarzenia                    |
| /locations/{id}/events/{id}/tickets                  | GET    | wyświetlenie dostępnych biletów         |
| /locations/{id}/events/{id}/tickets/{id}/reservation | POST   | rezerwacja biletu                       |
| /locations/{id}/events/{id}/tickets/{id}/purchase    | POST   | zakup biletu                            |
| /users/login                                         | POST   | logowanie                               |
| /users/register                                      | POST   | rejestracja                             |
| /users/wallet/add                                    | POST   | dodanie środków                         |
| /users/wallet                                  | GET    | stan środków                            |
