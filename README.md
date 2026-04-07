# BookMyShow Backend Engine 🎟️🎥

An enterprise-grade, highly concurrent Java backend engine mimicking the core architecture of BookMyShow. Designed to handle intense traffic, prevent double-bookings, and dynamically map UI seat layouts seamlessly.

## ⚙️ Tech Stack
- **Framework**: Spring Boot (Java)
- **Database**: MySQL Connect
- **ORM Layer**: Spring Data JPA / Hibernate
- **Build Tool**: Maven

---

## 🚀 Architectural Highlights

### 1. Mathematically Distributed Seat Pricing 💺
Instead of calculating seat prices locally at checkout, the backend automatically calculates and strictly embeds tiered prices directly into the physical seat columns the exact moment a `Hall` is registered! The native algorithm flawlessly segments the massive grid into 3 unique positional tiers.
* **Front Section**: Base Price + 50 
* **Middle Section**: Base + 100
* **Back Section**: Base + 150

### 2. Time-Collision Defense Matrix ⏰
Theater Owners cannot accidentally overlap movies. The `ShowService` engine possesses strict mathematical date-time validations. It auto-calculates the projected end-time based on the Movie's inherent duration, and rigidly cross-references it deeply with every single existing `Show` registered in that exact `Hall` to strictly block Time Collisions!

### 3. Serializable Transaction Locking (Anti-Race Condition) 🔒
To protect against the worst-case scenario high-traffic attacks *(Where User A and User B execute a checkout on the exact same Seat UUID at the exact same physical millisecond)*, the `TicketService.bookTicket()` heavily employs Database-level isolation locking via `@Transactional(isolation = Isolation.SERIALIZABLE)`. 

### 4. Real-Time UI Grid Compilation 🚦
Instead of relying on a fragile static `Boolean isBooked` database column parsing, the system mathematically reconstructs the entire Theater Map visually on the fly via `GET /shows/{showId}/seats`. It dynamically cross-references the pristine physical `Seat` layouts directly against deeply immutable `Ticket` records, converting it all into pure JSON to securely render Green/Red chairs to the UI!

---

## 📡 RESTful API Endpoints
All backend routes possess strictly standardized RESTful mapping, keeping Action-Verbs entirely out of the URL architecture.

### 👤 Users API
- `POST /users` - Register a Customer or Theater Owner
- `GET /users/{id}` - Fetch User Details

### 🏢 Theaters & Halls API 
- `POST /theaters` - Register a generic Theater wrapper
- `GET /theaters` - Fetch all Theaters globally
- `GET /theaters/owners/{id}` - Fetch Theaters exclusively owned by specific User UUID
- `POST /halls` - Register a specific Physical Screen/Hall with Automated Matrix generation

### 🎬 Movies API
- `POST /movies/{adminId}` - Admin registers Movie metadata
- `DELETE /movies/{movieId}/admin/{adminId}` - Admin forcibly destroys Movie record
- `GET /movies` - Fetch all active catalog Movies

### 🍿 Shows API (Only Returns Valid Active Dates >= TODAY)
- `POST /shows` - Embed a Movie uniquely into a Hall Time-Slot
- `GET /shows/movies/{movieId}` - Fetch all upcoming Shows hosting a specific Movie
- `GET /shows/theaters/{theaterId}` - Fetch all upcoming Shows running inside a specific Theater
- `GET /shows/{showId}/seats` - Directly fetch the complex JSON visual mapping array of Seats colored by Status.

### 🧾 Booking Engine API
- `POST /tickets` - Complete a Seat Checkout
- `GET /tickets/users/{userId}` - Fetch "My Tickets" History.

---

## 💻 Postman Collection Pipeline
A highly detailed, production-accurate `BookMyShow_Postman_Collection.json` file is permanently strapped into the root repository. You can natively Import this file straight into Postman to instantly access all endpoints populated heavily with variable placeholders!
