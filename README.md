# RideLink

> **Don’t travel alone when someone is already going your way.**

RideLink is a real-time, map-driven travel matching platform that connects people heading in the same direction so they can form smart, safe, and efficient travel groups.

---

## ⚡ What is RideLink?

RideLink is not just another ride-sharing app.

It is a **location-first social travel system** where users can:

* Discover people traveling to the same destination
* Create or join travel groups instantly
* Coordinate journeys in real-time using maps
* Chat and organize trips inside groups

Think of it as:

> “Google Maps + Group Chat + Smart Matching for Travel”

---

## 🌍 Core Idea

Most people travel alone even when others are going the same way.

RideLink solves this by:

* Mapping user journeys
* Matching similar routes
* Suggesting nearby travel groups
* Reducing cost, time, and isolation

---

## 🧠 Key Features (Planned / In Progress)

### 🗺️ Smart Map Matching

* Real-time map-based trip discovery
* Radius-based matching system
* Source → Destination intelligent grouping

### 👥 Travel Groups

* Create travel groups instantly
* Join existing groups going your way
* View members and availability

### 💬 Group Chat

* Real-time communication inside groups
* Trip coordination and updates

### 🔐 Authentication

* Google Sign-In only (no passwords, no friction)
* Secure identity through Google OAuth

### 👤 Profile System

* User travel preferences
* Group history
* Basic reputation system (future)

---

## 🏗️ Tech Stack

### Frontend

* React (Vite)
* TypeScript
* Tailwind CSS
* Mapbox / Google Maps API

### Backend

* Java Spring Boot
* Spring Security (OAuth2)
* Spring Data JPA

### Database

* PostgreSQL (recommended for geo-based matching)

### Future Additions

* WebSockets (real-time chat)
* Docker
* GitHub Actions CI/CD

---

## 🧭 System Philosophy

RideLink is built around 3 principles:

* **Clarity over complexity**
* **Real-time over static data**
* **People-first design**

Everything in the system revolves around:

> “Who is going where, and when?”

---

## 📌 Project Status

🚧 Early development phase
⚙️ Architecture and UI design in progress
🧪 Core features being implemented step-by-step

---

## 🚀 Local Setup (Dev Mode)

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Backend

```bash
cd backend
mvn spring-boot:run
```

---

## 🎯 Vision

RideLink aims to become a **real-world mobility coordination network** where people naturally connect through travel instead of traveling alone.

---

## ⚠️ Disclaimer

This project is under active development and is intended for learning, prototyping, and portfolio demonstration purposes.

---

## 🧠 Why RideLink Matters

Because millions of people travel every day…

But very few travel together, even when they should.
