# Final Project: 7oop's Absolute CINEMA

```
    _    _               _       _           ▄▄▄▄▄▄▄ ▄▄▄▄▄ ▄▄▄    ▄▄▄  ▄▄▄▄▄▄▄ ▄▄▄      ▄▄▄   ▄▄▄▄
   / \  | |__  ___  ___ | |_   _| |_ ___    ███▀▀▀▀▀  ███  ████▄  ███ ███▀▀▀▀▀ ████▄  ▄████ ▄██▀▀██▄
  / _ \ | '_ \/ __|/ _ \| | | | | __/ _ \   ███       ███  ███▀██▄███ ███▄▄    ███▀████▀███ ███  ███
 / ___ \| |_) \__ \ (_) | | |_| | ||  __/   ███       ███  ███  ▀████ ███      ███  ▀▀  ███ ███▀▀███
/_/   \_\_.__/|___/\___/|_|\__,_|\__\___|   ▀███████ ▄███▄ ███    ███ ▀███████ ███      ███ ███  ███
```

## .˳˚✦ 1. Overview

7oop's Absolute CINEMA is a Java-Based console application that simulates a full cinema management ecosystem.

The program allows users to:

-   Manage shows (Movies, Animated Movies, Concert Films)
-   Manage theaters
-   Manage screenings
-   Reserve seats with validation and conflict detection
-   View all available and reserved seats
-   Navigate through a structured menu with reusable page handling

This project emphasizes modular design and strong Object-Oriented Programming principles.

## .˳˚✦ 2. OOP Concepts

### 🎯 Encapsulation

-   All classes protect their fields using private or protected access.

-   Updating seat layout based on new rows and columns, screening reservation, and show data are properly hidden.

-   Custom exceptions (`InvalidSeatException`, `SeatConflictException`) and exception handling (`try {...} catch (...) {...}`) enforce safe access.

### 🎯 Inheritance

-   Class hierarchies model real-world cinema structure:
-   Hierarchy of Show and its subclasses

```
Show (abstract)
 ├── Movie
 ├── AnimatedMovie
 └── ConcertFilm
```

### 🎯 Polymorphism

-   Screening accepts any `Show` subclass.

-   Methods like `Show.getLongInfo()` and `Show.getShowTypeAsString()` behave differently depending on the Show type.

-   `ExperienceType` defines different viewer experiences

### 🎯 Abstraction

-   `Show` serves as a blueprint for all types of shows.

-   Page navigation is abstracted through `PageBuilder`, `Option`, and `PageResult`.

## .˳˚✦ 3. Program Structure

```
📦
├─ .gitignore
└─ src
   ├─ screening                 — Handles screening
   │  ├─ 📽️ Screening.java             ˚.✦.˳˖✶ Class representing a screening
   │  └─ 🎦 ExperienceType.java        ˚.✦.˳˖✶ Different screening experiences
   │
   ├─ seat                      — Handles seat
   │  ├─ 💺 Seat.java                  ˚.✦.˳˖✶ Class representing a theater seat
   │  ├─ ❗ InvalidSeatException.java  ˚.✦.˳˖✶ Custom Exception for Invalid Seat
   │  └─ ❕ SeatConflictException.java ˚.✦.˳˖✶ Custom Exception for Seat Availability
   │
   ├─ show                      — Handles show
   │  ├─ 🎞️ AnimatedMovie.java         ˚.✦.˳˖✶ Child class of Show representing a animated movie
   │  ├─ 🎤 ConcertFilm.java           ˚.✦.˳˖✶ Child class of Show representing a concert film
   │  ├─ 🎬 Movie.java                 ˚.✦.˳˖✶ Child class of Show representing a movie
   │  └─ ▶️ Show.java                  ˚.✦.˳˖✶ Abstract class blueprint for shows
   │
   └─ theater                   — Handles theater
      └─ 🖥️ Theater.java               ˚.✦.˳˖✶ Class representing a theater
```

Additional program flow and menu logic:

```
📦
├─ .gitignore
└─ src
   ├─ App.java                      ˚.✦.˳˖✶ Main
   │
   └─ cinema                    — Main driver
      ├─ 🎥 Cinema.java                ˚.✦.˳˖✶ Page routing and flow
      ├─ 🛠️ Config.java                ˚.✦.˳˖✶ Configurations for display
      ├─ 🩶 DemoPages.java             ˚.✦.˳˖✶ Load Demo
      ├─ 🤍 MainMenuPages.java         ˚.✦.˳˖✶ Main Menu Pages
      ├─ ❤️ MainScreeningPages.java    ˚.✦.˳˖✶ Manage Screening Pages
      ├─ 💚 MainShowPages.java         ˚.✦.˳˖✶ Manage Show Pages
      ├─ 💙 MainTheaterPages.java      ˚.✦.˳˖✶ Manage Theater Pages
      │
      └─ utils                  — Utility classes and enums
         ├─ ⚙️ CustomOption.java       ˚.✦.˳˖✶ Non-numbered option in pages
         ├─ ⚙️ Option.java             ˚.✦.˳˖✶ Numbered option in pages
         ├─ 🔨 PageBuilder.java        ˚.✦.˳˖✶ Page display and input
         ├─ 📁 PageResult.java         ˚.✦.˳˖✶ Return type of all pages and inputs
         └─ 📄 PageType.java           ˚.✦.˳˖✶ Available Pages
```

## .˳˚✦ 4. How to Run the Program

### Requirements:

-   Java JDK 17+ (recommended)

-   Any IDE (VS Code, IntelliJ, NetBeans) or terminal

### Run via Terminal:

```
cd src
javac App.java
java App
```

### Run via VS Code

-   Open folder in VS Code

-   Install Java extensions

-   Run App.java directly

## .˳˚✦ 5. Sample Output
Manage Show (Animated) Page
```
================================================================================
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

================================================================================
                   Project by Group 7oop for CS 211, CS-2105
--------------------------------------------------------------------------------
                               [Show Management]
                             Manage Animated Movie

                                     {Info}
    Animated Movie Title: Moana (2015)
     - : A girl who discovers what she needs to doo to help her tribe.
     - Animation Studio: Disney
     - Duration: 1:47

        [1] Edit Title
        [2] Edit Description
        [3] Edit Release Year
        [4] Edit Animation Studio
        [5] Edit Duration

        [R] Return
        [E] Show Management
        [M] Main Menu
--------------------------------------------------------------------------------
  >> Input Option:   
```
Manage Theater Page
```
================================================================================
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

================================================================================
                   Project by Group 7oop for CS 211, CS-2105
--------------------------------------------------------------------------------
                              [Theater Management]
                      Manage Theater: Geev's Entertainment

    Dimensions: 15 rows x 10 columns
    Total Seats: 150
                              {Currently Screening}
    Animated Movie Title: Moana (2015)
     - : A girl who discovers what she needs to doo to help her tribe.
     - Animation Studio: Disney
     - Duration: 1:47
    Experience Type: Standard 3D


        [1] End Current Screening
        [2] Show Seat Layout
        [3] Edit Theater Dimensions
        [4] Edit Theater Name

        [R] Return
        [M] Main Menu
--------------------------------------------------------------------------------
  >> Input Option:
```
Manage Screening Page
```
================================================================================
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

================================================================================
                   Project by Group 7oop for CS 211, CS-2105
--------------------------------------------------------------------------------
                             [Screening Management]
             Manage Screening: Moana (2015) | Geev's Entertainment

                                      {Show}
    Animated Movie Title: Moana (2015)
     - : A girl who discovers what she needs to doo to help her tribe.
     - Animation Studio: Disney
     - Duration: 1:47

                                    {Theater}
    Theater: Geev's Entertainment
    Reserved: 75/150
    Currently Screening: Yes


        [1] Show Seat Reservation Layout
        [2] Add Seat Reservation
        [3] Delete Seat Reservation
        [4] Edit Show
        [5] Edit Theater
        [6] Edit Experience Type

        [R] Return
        [M] Main Menu
--------------------------------------------------------------------------------
  >> Input Option: 
```
Screening Seat Reservation Layout
```
================================================================================
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

================================================================================
                   Project by Group 7oop for CS 211, CS-2105
--------------------------------------------------------------------------------
                             [Screening Management]
                Seat Layout: Moana (2015) | Geevs's Entertainment

                                A B C D E F G H I J
                              1 o x o o x o x o o x
                              2 o o o x x o o o x o
                              3 x o x o o x x x o o
                              4 x x x x o o x o o o
                              5 x x x o x o o x o o
                              6 x x x x x o o x x x
                              7 x o x x x o o o x x
                              8 x x o o o o x x x o
                              9 x x o x o o o x x x
                             10 x x x o x o x o o x
                             11 o x o o x x x o o x
                             12 x x x o x o o x o o
                             13 x x x o o o x x o o
                             14 x o o x o x o o o o
                             15 x x x o o o o o x o

        [R] Return
        [E] Screening Management
        [M] Main Menu
--------------------------------------------------------------------------------
  >> Input Option:
```

## .˳˚✦ 6. Authors

-   Estrada, Aubrey Nicole P.
-   Plaza, Geevoi A.
-   Perez, Kim Jimuel A.

## .˳˚✦ 7. Acknowledgement

We like to convey our profound gratitude to our adviser, Fatima Marie P. Agdon, for her direction, support, and insightful input during the project's development. Her knowledge significantly aided us in developing a system that adheres to real-world programming standards and object-oriented programming concepts.

Gratitude is extended to all project members for their collaboration and dedication in developing this Project ABSOLUTE CINEMA, which emulates authentic cinema operations such as:

- Managing shows (Movies, Animated Movies, Concert Films)
- Managing theaters
- Managing screenings
- Reserving seats with validation and conflict detection
- Viewing all available and reserved seats
- Navigating through a structured menu with reusable page handling

This project was made possible through collaboration, dedication, and continuous improvement from the entire team.
