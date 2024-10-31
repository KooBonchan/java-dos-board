## README
* Dos console IO + Oracle CRUD practice
* Project reference: ThisIsJava3rd. chapter20
----
## What I changed from reference project
* Breakdown the project into subclasses, which had been centralized to main.
* Added login + signup
* Established a connection manager that keeps a single connection alive till the program ends
## What I expect for changes
* Project structure improvement for better expandability
* Reduced tight coupling between each pages(internal naming, states)
* Easy integration to connection pool / hikariCP
## What I missed
* Code refactoring
    * SQL queries are repeated not encapsulated
    * Retrieving idle connection from ConnectionManager is not intuitive:
        * When using driver manager, i should close it each time i use it
        * With connection managerr, the code structure is the same, but i should not close it, which leads to confusion in further development.
        * Encapsulize each SQL connection statically into ConnectionManager would be a solution, i don't think it is a good approach.
* Messy project structure
    * Structure is flat and not hierarchical, making it messy
    * I had to group files by their main functionality—pages should reside in the same package.
* High dependency on a global state Context
* No user verification when updating DB
* The "clear all" functionality should be removed, as it should only be allowed for admins.
