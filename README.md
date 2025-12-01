```
           _    _               _       _
          / \  | |__  ___  ___ | |_   _| |_ ___
         / _ \ | '_ \/ __|/ _ \| | | | | __/ _ \
        / ___ \| |_) \__ \ (_) | | |_| | ||  __/
       /_/   \_\_.__/|___/\___/|_|\__,_|\__\___|
 ▄▄▄▄▄▄▄ ▄▄▄▄▄ ▄▄▄    ▄▄▄  ▄▄▄▄▄▄▄ ▄▄▄      ▄▄▄   ▄▄▄▄
███▀▀▀▀▀  ███  ████▄  ███ ███▀▀▀▀▀ ████▄  ▄████ ▄██▀▀██▄
███       ███  ███▀██▄███ ███▄▄    ███▀████▀███ ███  ███
███       ███  ███  ▀████ ███      ███  ▀▀  ███ ███▀▀███
▀███████ ▄███▄ ███    ███ ▀███████ ███      ███ ███  ███
```

```
📦
├─ .gitignore
└─ src
   ├─ App.java
   ├─ cinema                    — Main driver
   │  ├─ Cinema.java                ˚.✦.˳˖✶ Page routing and flow
   │  ├─ Config.java                ˚.✦.˳˖✶ Configurations for display
   │  ├─ DemoPages.java             ˚.✦.˳˖✶ Load Demo
   │  ├─ MainMenuPages.java         ˚.✦.˳˖✶ Main Menu Pages
   │  ├─ MainScreeningPages.java    ˚.✦.˳˖✶ Manage Screening Pages
   │  ├─ MainShowPages.java         ˚.✦.˳˖✶ Manage Show Pages
   │  ├─ MainTheaterPages.java      ˚.✦.˳˖✶ Manage Theater Pages
   │  └─ utils                  — Utility classes and enums
   │     ├─ CustomOption.java       ˚.✦.˳˖✶ Non-numbered option in pages
   │     ├─ Option.java             ˚.✦.˳˖✶ Numbered option in pages
   │     ├─ PageBuilder.java        ˚.✦.˳˖✶ Page display and input
   │     ├─ PageResult.java         ˚.✦.˳˖✶ Return type of all pages and inputs
   │     └─ PageType.java           ˚.✦.˳˖✶ Available Pages
   ├─ screening                 — Handles screening
   │  ├─ Screening.java             ˚.✦.˳˖✶ Screening class representing a screening
   │  └─ utils
   │     └─ ExperienceType.java     ˚.✦.˳˖✶ Different screening experiences
   ├─ seat                      — Handles seat
   │  ├─ Seat.java                  ˚.✦.˳˖✶ Seat class representing a theater seat
   │  ├─ InvalidSeatException.java  ˚.✦.˳˖✶ Custom Exception for Invalid Seat
   │  └─ SeatConflictException.java ˚.✦.˳˖✶ Custom Exception for Seat Availability
   ├─ show                      — Handles show
   │  ├─ AnimatedMovie.java         ˚.✦.˳˖✶ AnimatedMovie child class of show representing a animated movie
   │  ├─ ConcertFilm.java           ˚.✦.˳˖✶ ConcertFilm child class of show representing a concert film
   │  ├─ Movie.java                 ˚.✦.˳˖✶ Movie child class of show representing a movie
   │  └─ Show.java                  ˚.✦.˳˖✶ Show abstract class blueprint for shows
   └─ theater                   — Handles theater
      └─ Theater.java               ˚.✦.˳˖✶ Theater class representing a theater
```
