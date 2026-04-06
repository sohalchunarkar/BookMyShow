# BookMyShow - Backend Architecture

This Document outlines the core feature implementations, data structures, and algorithms engineered for the BookMyShow Spring Boot application!

## 📌 Features Built 

### 1. Dynamic Theater & Hall Management
* **Implementation:** `TheaterService.java`, `HallService.java`
* **Details:** We allow specialized users (`UserType.THEATER_OWNER`) to register Theaters. When an owner adds a `Hall` (Screen) to their theater, they only need to specify the rows and columns.
* **Smart Seating Automation:** The backend dynamically maps physical grid coordinates mathematically. It converts loops to alphabetical row identifiers (Row 'A', 'B', 'C') and maps `Seat` objects sequentially (e.g. `A-1`, `A-2`). These seats are bound seamlessly to the parent Hall via `CascadeType.ALL`. 

### 2. Intelligent JSON Serialization 
* **The Problem:** Modern Spring Data mapping often suffers from massive `StackOverflowErrors` and Infinite Loops when parent-child relationships map iteratively into each other (Theater -> User -> Theater).
* **The Solution:** We cleanly bypassed rigid strategies like `@JsonBackReference` which completely hide fields from the frontend interface. Through careful placement of `@JsonIgnoreProperties`, objects recursively maintain their identity (so a `Theater` still visibly shares its `Owner`), but it smartly trims cyclic nested arrays. 

### 3. Application Show & Timeline Engine
* **Implementation:** `ShowService.java`, `ShowController.java`, `ShowRepository.java`
* **Details:** This engine serves as the absolute backbone of movie booking. It tracks a core `Show` entity mapping the correlation between a scheduled `Movie` and the respective localized `Hall`.
* **The Feature Flow:** 
  1. **Dual Validation Checks**: It robustly validates whether exactly the user making the query natively owns the Theater hosting the selected Hall via relational traversing: `hall.getTheater().getOwner()`. 
  2. **Smart Calculation**: The UI only submits a starting time block (`request.getStartTime()`). The backend inherently queries the master `Movie` database to find its native Duration length, adding it up automatically to construct the `LocalTime calculatedEndTime`. 
  3. **Scheduling Collision Matrix**: Before a Show is firmly saved into the database, the engine queries the `Hall` for its native daily schedule. It loops backward inspecting time-blocks mathematically via: `(newStart < existingEnd) && (newEnd > existingStart)`. If an overlap registers, it bounces the execution with a polished `ShowTimingOverlapException` HTTP 400 Bad Request protocol.

### 4. RESTful API Endpoints 
The backend exposes comprehensive endpoints seamlessly crafted for Frontend UI rendering paradigms: 
* `POST /show/add`: Accepts a precise `ShowRequestDto` payload mapping out `hallId`, `movieId`, `ownerId`, `showDate`, `startTime`, and a custom `basePrice`. 
* `GET /show/movie/{movieId}`: Powers a "Movie Driven Search UI". Allows a user frontend instance to click on an isolated Movie and cleanly retrieve the entire payload of global showings seamlessly.
* `GET /show/theater/{theaterId}`: Powers a "Venue Driven UI". Returns localized mapping of every registered operational show within that designated venue building.
