# AtomicGuard API – Backend Engineering Assignment

## Overview

AtomicGuard API is a Spring Boot microservice built as a central API gateway and guardrail system for handling social interactions between Users and Bots.

The system focuses on:

* High-performance REST APIs
* PostgreSQL as source of truth
* Redis for fast counters, locks, cooldowns, and scoring
* Thread-safe atomic operations
* Guardrails to prevent bot overactivity
* Event-driven scheduling support

---

## Tech Stack

* Java 17+
* Spring Boot 3.x
* Spring Data JPA
* PostgreSQL
* Redis (Spring Data Redis / Lettuce)
* Maven

---

## Project Features Implemented

## Phase 1 – Core API & Database Setup

### Database Entities

Implemented core entities:

* User

    * id
    * username
    * isPremium

* Bot

    * id
    * name
    * personaDescription

* Post

    * id
    * authorId
    * content
    * createdAt

* Comment

    * id
    * postId
    * authorId
    * content
    * depthLevel
    * createdAt

### REST APIs

Implemented:

* POST /api/posts
  Create a new post

* POST /api/posts/{postId}/comments
  Add comment to post

* POST /api/posts/{postId}/like
  Like a post

---

## Phase 2 – Redis Virality Engine & Atomic Locks

### Virality Score

Redis key pattern:

post:{id}:virality_score

Scoring rules:

* Bot Reply = +1
* Human Like = +20
* Human Comment = +50

Used Redis atomic increment operations for real-time score updates.

### Atomic Guardrails

Implemented / Designed:

#### Horizontal Cap

Redis key:

post:{id}:bot_count

Limits total bot replies per post to 100 using atomic INCR.

#### Vertical Cap

Comment thread depth validation:

depth_level <= 20

#### Cooldown Cap

Redis key pattern:

cooldown:bot:{botId}:human:{humanId}

TTL: 10 minutes

Prevents same bot repeatedly interacting with same user.

---

## Thread Safety Approach

Used Redis atomic commands:

* INCR
* EXISTS / SETNX / setIfAbsent
* TTL expiration

Why thread-safe:

Redis executes commands atomically, which prevents race conditions during concurrent requests.

Example:

When 200 bot requests hit same post simultaneously, counter increments safely in Redis.

---

## Data Integrity Strategy

* PostgreSQL stores permanent data (posts/comments/users/bots)
* Redis acts as guardrail layer
* Requests are validated through Redis first
* Database write happens only after passing rules

---

## Current Project Status

### Completed

* Spring Boot setup
* PostgreSQL integration
* Redis integration
* Core entities
* Basic REST APIs
* Virality score logic
* Guardrail counters structure

### Partially Completed / In Progress

* Full bot reply endpoint flow
* Global exception handling
* Advanced validation

### Pending Enhancements

## Phase 3 – Notification Engine

Planned:

* Redis throttled notifications
* Pending notification queue using Redis List
* Scheduled sweeper every 5 minutes
* Summarized push notifications

## Phase 4 – Advanced Testing

Planned:

* Concurrent spam load testing (200 requests)
* Full integration tests
* Performance benchmarking

---

## How to Run

### PostgreSQL

Create database:

atomic_guard

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/atomic_guard

### Redis

Configure Redis local/cloud credentials in:

spring.data.redis.host=
spring.data.redis.port=
spring.data.redis.password=

### Run Application

mvn spring-boot:run

---

## Sample API Endpoints

### Create Post

POST /api/posts

{
"authorId": 1,
"content": "Hello World"
}

### Like Post

POST /api/posts/1/like

### Add Comment

POST /api/posts/1/comments

{
"authorId": 2,
"content": "Nice post"
}

---

## Design Notes

This project prioritizes backend fundamentals:

* Stateless microservice design
* Fast Redis guardrails
* Database consistency
* Extensible notification system

---

## Author

Bala Murali Krishna
