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

## .˳˚✦ 6. Authors

## .˳˚✦ 7. Acknowledgement
