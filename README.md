# Quora Clone Application

A reactive, real-time question-and-answer platform demonstrating advanced backend architecture with asynchronous event processing, full-text search capabilities, and scalable data management. This application replicates core Quora functionality while emphasizing reactive programming patterns and event-driven design.

**Project Status**: Functional demonstration system | Built for learning reactive microservices | Not production-ready.

---

## System Overview

Users interact with a distributed question-and-answer ecosystem featuring:

1. **Users** create questions and answers, build follower networks
2. **Questions** can be tagged, searched, and tracked for view counts
3. **Answers & Comments** enable discussion threads on questions
4. **Likes/Dislikes** provide engagement metrics
5. **Follows** create social connections between users
6. **Real-time Search** powered by Elasticsearch for sub-millisecond query response
7. **Asynchronous Processing** via Kafka for view count tracking

---

## Architecture (2 Reactive Services)

### MongoDB Database Service (Primary Data Store - Port 27017)

Stores all relational entities with reactive support:
- **Users**: Profile management, follower/following counts
- **Questions**: Query content, tag associations, view counter
- **Answers**: Response to questions with attribution
- **Comments**: Discussion threads on questions/answers
- **Likes/Dislikes**: Engagement metrics on questions and answers
- **Follows**: Social relationship tracking
- **Tags**: Categorization with usage frequency

**Technology**: MongoDB with Spring Data Reactive MongoDB  
**Key Features**: 
- Indexed fields for fast query lookups
- Document-based schema for flexible data modeling
- Automatic timestamp auditing via Spring Data annotations

### Elasticsearch Service (Full-Text Search - Port 9200)

Specialized search index for efficient content discovery:
- **Question Documents**: Denormalized question data (title, content, creator)
- Full-text search with relevance scoring
- Real-time synchronization with MongoDB data

**Technology**: Elasticsearch with Spring Data Reactive Elasticsearch  
**Key Features**:
- Tokenization and stemming for natural language matching
- Boolean queries (AND/OR combinations of search terms)
- Search result ranking by relevance

### Kafka Event Streaming (Asynchronous Processing - Port 9092)

Event-driven architecture for decoupled operations:
- **View Count Topic**: Captures question view events
- **Consumer Group**: Processes events and updates MongoDB asynchronously
- Non-blocking event processing with configurable concurrency

**Technology**: Apache Kafka with Spring Kafka  
**Key Features**:
- Producer publishes view count events after question retrieval
- Consumer subscribes to events and updates database
- Configurable concurrent message processing (3 concurrent messages)

### Spring Boot REST API Service (Port 8080)

Central application coordinating all services:
- REST endpoints for CRUD operations on all entities
- Reactive request handling with Mono/Flux return types
- Transaction orchestration across services

---

## Technology Stack

| Layer | Component | Purpose |
|-------|-----------|---------|
| **Language** | Java 17 with Spring Boot 3.x | RESTful API, reactive frameworks |
| **Build** | Gradle 8.14.3 | Dependency management and compilation |
| **Primary Data** | MongoDB 4.0+ | Document-oriented database |
| **Search Index** | Elasticsearch 8.0+ | Full-text search engine |
| **Async Events** | Apache Kafka | Event streaming and processing |
| **Reactive Streams** | Project Reactor | Non-blocking async operations |
| **Frontend** | Not included | Backend-only service |

---

## Core Technical Concepts

### Reactive Programming with Project Reactor
Non-blocking asynchronous data flow using Mono and Flux:

**Mono**: Represents a single asynchronous result
```
Mono<User> user = userRepository.findById("123");
// Does NOT execute immediately - returns a "recipe"
// Only executes when subscribed
```

**Flux**: Represents multiple asynchronous results (stream)
```
Flux<Question> questions = questionRepository.findAll();
// Returns multiple questions as they arrive
```

**Operators**:
- `flatMap()`: Transform item and flatten nested Mono/Flux
- `map()`: Transform item (for non-Mono returning functions)
- `switchIfEmpty()`: Provide fallback on empty result
- `collectList()`: Gather Flux items into Mono<List>
- `zip()`: Combine multiple Monos waiting for all to complete

### Event-Driven View Counting
Decoupled asynchronous processing for metrics:
- Question retrieval publishes ViewCountEvent to Kafka topic
- Consumer service processes events independently
- Database updates happen without blocking user request
- Enables horizontal scaling (multiple consumer instances)

### Full-Text Search with Elasticsearch
Specialized index for content discovery:
- Questions denormalized into elastic documents (title + content)
- Elasticsearch handles tokenization and relevance scoring
- Supports phrase queries and boolean operators
- Separate index synchronization endpoint for data consistency

### MongoDB Query Patterns
Custom MongoDB queries for complex filtering:
- Regex queries for partial text matching (case-insensitive)
- Array membership queries ($in for ANY, $all for ALL)
- Cursor-based pagination for efficient large dataset traversal
- Indexed fields for sub-100ms query response times

### Pagination & Cursor-Based Navigation
Scalable data retrieval patterns:
- **Offset Pagination**: Traditional page-based navigation (simpler, less efficient)
- **Cursor Pagination**: Timestamp-based pointer for consistent results across pages (recommended for large datasets)

### Relationship Management
Denormalization strategy for reactive NoSQL:
- No foreign keys in MongoDB (handled in application layer)
- User IDs stored as strings in referencing documents
- Explicit queries to fetch related data
- Manual enrichment combining multiple queries (Mono.zip)

### Engagement Metrics
Follower/following counts maintained with atomic updates:
- Increment/decrement on follow/unfollow actions
- Counters stored at user level for O(1) retrieval
- Usage count tracking for tags

---

## Core Entities & Relationships

```
User
├── id (unique identifier)
├── username (min 8 chars)
├── email (unique, validated)
├── bio (optional, max 500 chars)
├── followerCount (aggregated)
├── followingCount (aggregated)
└── timestamps (createdAt, updatedAt)

Question
├── id
├── title (10-100 chars)
├── content (10-1000 chars)
├── tagIds[] (up to 10 tags)
├── views (counter, default 0)
├── createdById (reference to User)
└── timestamps

Answer
├── id
├── content (10-1000 chars)
├── questionId (reference)
├── createdById (reference to User)
└── timestamps

Tag
├── id
├── name (unique, 2-50 chars)
├── description (max 200 chars)
├── usageCount (incremented when question created)
└── timestamps

Comment
├── id
├── text (2-500 chars)
├── targetId (question/answer/comment ID)
├── targetType (QUESTION/ANSWER/COMMENT enum)
├── createdById (reference to User)
└── timestamps

Like
├── id
├── targetId (question/answer ID)
├── likeType (QUESTION/ANSWER enum)
├── isLike (true for upvote, false for downvote)
├── createdById (reference to User)
└── timestamps

Follow
├── id
├── followerId (reference to User)
├── followingId (reference to User)
└── timestamps
```

---

## API Endpoints

### User Management
- `POST /api/users` - Create new user account
- `GET /api/users/{id}` - Retrieve user profile
- `GET /api/users` - List all users (paginated)

### Question Operations
- `POST /api/questions` - Create question (also indexes in Elasticsearch)
- `GET /api/questions/{id}` - Retrieve question (publishes view count event to Kafka)
- `GET /api/questions` - List all questions
- `DELETE /api/questions/{id}` - Delete question (removes from Elasticsearch, decrements tag usage)
- `GET /api/questions/search?query=...` - Text search with offset pagination
- `GET /api/questions/cursor?cursor=...&size=10` - Cursor-based pagination
- `GET /api/questions/tag/{tagId}` - Questions by single tag
- `GET /api/questions/tag/any?tagIds=...` - Questions with ANY of tags
- `GET /api/questions/tag/all?tagIds=...` - Questions with ALL of tags
- `GET /api/questions/elasticsearch?query=...` - Elasticsearch full-text search
- `GET /api/questions/sync-elasticsearch` - Sync all questions to Elasticsearch index

### Answer Operations
- `POST /api/answers` - Post answer to question
- `GET /api/answers/{id}` - Retrieve specific answer
- `GET /api/answers/question/{questionId}` - Get all answers for question

### Comment Operations
- `POST /api/comments` - Create comment on question/answer/comment
- `GET /api/comments/{id}` - Retrieve comment
- `GET /api/comments` - List comments (paginated)

### Engagement (Likes)
- `POST /api/likes` - Like or dislike question/answer
- `GET /api/likes/{id}` - Retrieve like record

### Social (Follow)
- `POST /api/follow` - Follow a user
- `GET /api/follow/{id}` - Retrieve follow relationship
- `GET /api/follow` - List all follows (paginated)
- `GET /api/follow/{userId}/followers` - Get all followers of user
- `GET /api/follow/{userId}/following` - Get all users that user follows

### Tag Management
- `POST /api/tags` - Create tag
- `GET /api/tags/{id}` - Retrieve tag
- `GET /api/tags` - List tags (paginated)
- `GET /api/tags/name/{name}` - Retrieve tag by name

---

## Technical Learning & Implementation Challenges

### Reactive Programming with Mono & Flux
Understanding non-blocking streams required separating concepts:
- **Imperative thinking**: "Do action, then next action"
- **Reactive thinking**: "Describe what happens when data arrives"

Initial challenge was grasping when to use `flatMap()` vs `map()`:
- `flatMap()`: When transformation returns Mono/Flux (to flatten nesting)
- `map()`: When transformation returns plain object

Successfully implemented:
- User enrichment pattern combining data from multiple sources
- Tag fetching for question responses via Flux collection
- Combined Mono.zip for parallel async operations

**Key Learning**: Reactive streams are lazy - chain is only executed on subscription.

### MongoDB Queries with Custom Regex and Array Operations
Complex query patterns required learning MongoDB aggregation syntax:
- **Regex queries**: Case-insensitive partial text matching
- **Array membership**: Finding documents with ANY/ALL tag matches
- **Indexed fields**: Performance optimization for frequently queried properties

Implementation details:
- @Query annotation for custom MongoDB aggregation
- Stream questions by creation timestamp for cursor pagination
- Pageable object for limit and offset in queries

**Challenge**: Initially returned wrong query results when confusing $in (ANY) with $all (ALL).

### Asynchronous Event Processing with Kafka
Decoupled view counting required understanding producer-consumer pattern:
- Publisher (QuestionService) sends events to Kafka topic
- Consumer (KafkaEventConsumer) processes asynchronously
- Database update happens independently of request cycle

Implementation details:
- KafkaTemplate for synchronous message sending
- @KafkaListener annotation for event subscription
- Configurable concurrency (3 concurrent message processing)
- Reactive flatMap for async database operations within consumer

**Challenge**: Handling race conditions between event processing and subsequent queries required ensuring event order or accepting eventual consistency.

### Elasticsearch Integration
Full-text search demanded understanding document indexing and querying:
- Denormalized question documents stored separately from MongoDB
- Tokenization breaks content into searchable terms
- Relevance scoring ranks results by match quality

Implementation details:
- Separate ReactiveElasticsearchRepository for search operations
- QuestionIndexService handles sync between MongoDB and Elasticsearch
- Automatic indexing on question creation
- Manual sync endpoint for consistency verification

**Learning**: MongoDB full-text search sufficient for small datasets; Elasticsearch essential at scale.

### Data Enrichment Patterns
Combining data from multiple repositories into response DTOs:
- User info fetched separately and merged into question/answer responses
- Tag details fetched from tag IDs embedded in question
- Parallel queries using Mono.zip waiting for all sources

**Key Pattern**: Use userService.getUserById() instead of directly calling userRepository to avoid code duplication and maintain separation of concerns.

### Pagination Strategies
Implemented both offset and cursor-based pagination:
- **Offset pagination**: Simple, works well for static datasets
- **Cursor pagination**: Resilient to data insertion/deletion between page requests

**Learning**: Cursor pagination prevents "skipping" and "duplicates" common with offset pagination during concurrent data changes.

---

## Setup & Deployment

### System Requirements
- Java Development Kit 17 or higher
- MongoDB 4.0 or higher (running on port 27017)
- Elasticsearch 8.0 or higher (running on port 9200)
- Apache Kafka 2.8 or higher (running on port 9092)

### Installation Steps

**1. Start MongoDB**
```bash
# Using Docker (recommended)
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Or use local MongoDB installation
mongod --port 27017
```

**2. Start Elasticsearch**
```bash
# Using Docker
docker run -d -p 9200:9200 -e discovery.type=single-node docker.elastic.co/elasticsearch/elasticsearch:8.0.0

# Or use local Elasticsearch installation
./bin/elasticsearch
```

**3. Start Apache Kafka**
```bash
# Start Zookeeper (required for Kafka)
./bin/zookeeper-server-start.sh config/zookeeper.properties

# In another terminal, start Kafka broker
./bin/kafka-server-start.sh config/server.properties

# Create the topic (if not auto-created)
./bin/kafka-topics.sh --create --topic view-count-topic --bootstrap-servers localhost:9092
```

**4. Build and Run Application**
```bash
cd QuoraApp
./gradlew clean build
java -jar build/libs/QuoraApp-0.0.1-SNAPSHOT.jar
```

Expected console output:
```
Tomcat started on port(s): 8080 (http)
MongoDB connected to localhost:27017/quora_db
Elasticsearch connected to http://localhost:9200
Kafka bootstrap servers: localhost:9092
```

**5. Verify Installation**
```bash
# Health check
curl http://localhost:8080/api/users

# Create test user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser123",
    "email": "test@example.com",
    "bio": "Test user bio"
  }'
```

---

## API Testing Examples

### Create User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johnsmith789",
    "email": "john.smith@example.com",
    "bio": "Software engineer interested in distributed systems"
  }'
```

### Create Tag
```bash
curl -X POST http://localhost:8080/api/tags \
  -H "Content-Type: application/json" \
  -d '{
    "name": "distributed-systems",
    "description": "Questions about distributed systems design"
  }'
```

### Create Question
```bash
curl -X POST http://localhost:8080/api/questions \
  -H "Content-Type: application/json" \
  -d '{
    "title": "What is eventual consistency?",
    "content": "Can someone explain the concept of eventual consistency in distributed systems?",
    "tagIds": ["tag-id-1", "tag-id-2"],
    "createdById": "user-id-1"
  }'
```

Expected behavior:
1. Question saved to MongoDB
2. Question indexed to Elasticsearch
3. Tag usage counts incremented
4. Response returned with question details and user info

### Search Questions (Text Search)
```bash
# MongoDB-based search with regex
curl "http://localhost:8080/api/questions/search?query=consistency&offset=0&pageSize=10"

# Elasticsearch-based full-text search
curl "http://localhost:8080/api/questions/elasticsearch?query=consistency"
```

### Cursor-Based Pagination
```bash
# Get first page (no cursor)
curl "http://localhost:8080/api/questions/cursor?size=10"

# Response includes questions sorted by creation time
# Use createdAt timestamp from last item as cursor for next page

# Get next page
curl "http://localhost:8080/api/questions/cursor?cursor=2024-01-15T10:30:00&size=10"
```

### Post Answer (Publishes View Event)
```bash
curl -X POST http://localhost:8080/api/answers \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Eventual consistency means updates will eventually be reflected everywhere...",
    "questionId": "question-id-1",
    "createdById": "user-id-2"
  }'

# Kafka consumer automatically increments question view count asynchronously
```

### Create Follow Relationship
```bash
curl -X POST http://localhost:8080/api/follow \
  -H "Content-Type: application/json" \
  -d '{
    "followerId": "user-id-1",
    "followingId": "user-id-2"
  }'

# Both users' follower/following counts incremented atomically
```

### Tag-Based Question Filtering
```bash
# Questions with ANY of the tags
curl "http://localhost:8080/api/questions/tag/any?tagIds=tag-1,tag-2&page=0&size=10"

# Questions with ALL of the tags
curl "http://localhost:8080/api/questions/tag/all?tagIds=tag-1,tag-2&page=0&size=10"

# Questions with specific tag
curl "http://localhost:8080/api/questions/tag/tag-1?page=0&size=10"
```

---

## Implementation Highlights

**Reactive Data Flow**: Questions enrich with user and tag information through coordinated Mono/Flux operations, delivering fully populated responses without blocking.

**Event-Driven Metrics**: View counts processed asynchronously via Kafka, enabling real-time analytics without impacting request latency.

**Dual Search Capability**: MongoDB supports quick tag-based queries while Elasticsearch handles complex full-text searches, optimizing for different access patterns.

**Denormalization Strategy**: Question data duplicated to Elasticsearch for search performance, with automatic synchronization ensuring consistency.

**Scalable Pagination**: Cursor-based navigation prevents skipping/duplication during concurrent data changes, superior to offset pagination for large datasets.

**Atomic User Metrics**: Follow operations use Mono.zip to atomically update both users' counts in parallel, ensuring consistency.

---

## Project Structure

```
QuoraApp/
├── src/main/java/com/example/QuoraApp/
│   ├── adapter/                      # DTO ↔ Entity mappers
│   ├── config/                       # Kafka configuration
│   ├── consumers/                    # Kafka event consumers
│   ├── controllers/                  # REST endpoints
│   ├── dto/                          # Request/Response DTOs
│   ├── events/                       # Event models
│   ├── models/                       # MongoDB document entities
│   ├── producers/                    # Kafka event publishers
│   ├── repositories/                 # Data access layer
│   ├── services/                     # Business logic
│   └── utils/                        # Utility functions (cursor parsing)
├── src/main/resources/
│   └── application.yml               # Configuration (MongoDB, Elasticsearch, Kafka)
└── build.gradle                      # Dependencies
```

---

## Key Design Patterns

**Adapter Pattern**: Separate adapters handle DTO ↔ Entity transformations, maintaining clean separation between API contracts and data models.

**Repository Pattern**: Spring Data repositories abstract data access, supporting both MongoDB and Elasticsearch implementations transparently.

**Service Layer Pattern**: Service interfaces define contracts while implementations orchestrate repositories and external services.

**Event-Driven Pattern**: Kafka producers and consumers enable asynchronous, loosely-coupled operations.

**Enrichment Pattern**: Services combine data from multiple sources (user info, tags) into comprehensive DTOs.

**Reactive Streams Pattern**: Non-blocking Mono/Flux operations enable high-concurrency request handling.

---

## Technology Stack Summary

| Category | Technologies |
|----------|---------------|
| **Language** | Java 17 with Spring Boot 3.x |
| **Build** | Gradle 8.14.3 |
| **Primary Data** | MongoDB with Spring Data Reactive MongoDB |
| **Search** | Elasticsearch with Spring Data Reactive Elasticsearch |
| **Events** | Apache Kafka with Spring Kafka |
| **Reactive Streams** | Project Reactor (Mono, Flux) |

---

## Project Status & Scope

- **Purpose**: Educational project to demonstrate reactive microservices architecture
- **Development Timeline**: Self-paced learning project (estimated 4-6 weeks)
- **Functional Status**: All core features implemented and tested
- **Production Ready**: No; requires authentication, error handling, and observability
- **Code Quality**: Clean architecture with proper separation of concerns, lacks comprehensive testing
- **Documentation**: API endpoints documented, deployment instructions provided
