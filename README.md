# About Music in Movies - Blog Platform

This project is a full-stack blog website about music in movies.

It has two main parts:

- `backend`: a Spring Boot API written in Java
- `frontend`: a React website built with Vite

The backend stores users, posts, and comments. The frontend is what people see and use in the browser.

## Live Deployment

- Website: `https://music-movies-blog-frontend.onrender.com`
- Backend API: `https://music-movies-blog-api.onrender.com`
- Swagger API docs: `https://music-movies-blog-api.onrender.com/swagger-ui.html`

## What You Can Do With This App

- Register and log in
- Leave comments
- Create posts from any account
- View posts created by all users
- Edit and delete posts as an admin
- Write rich text blog posts with a text editor

## Tools Used

### Backend

- Java
- Spring Boot
- Spring Security
- JWT authentication
- Spring Data JPA
- File-based H2 database for local development
- PostgreSQL for deployment
- Swagger/OpenAPI for API documentation

### Frontend

- React
- React Router
- Axios
- Vite
- Custom HTML editor component

## Before You Start

Make sure these are installed on your computer:

- Java 17 or newer
- Maven
- Node.js
- npm

You can check by running:

```bash
java -version
mvn -version
node -v
npm -v
```

If one of these commands fails, install that tool before continuing.

## Project Structure

```text
blog-platform/
├── backend/
│   ├── src/main/java/com/marius/blog/
│   │   ├── config/        # Startup configuration and sample data
│   │   ├── controller/    # API endpoints
│   │   ├── dto/           # Request and response objects
│   │   ├── exception/     # Error handling
│   │   ├── model/         # Database tables as Java classes
│   │   ├── repository/    # Database access
│   │   ├── security/      # Login, JWT, and permissions
│   │   ├── service/       # Main business logic
│   │   └── util/          # Helper code
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
└── frontend/
    ├── src/
    │   ├── components/    # Reusable UI pieces
    │   ├── config/        # Frontend configuration
    │   ├── context/       # Shared React state
    │   ├── pages/         # Full pages
    │   └── services/      # API calls
    ├── env.example
    └── package.json
```

## How To Run The Project Locally

You need two terminals:

- one terminal for the backend
- one terminal for the frontend

### 1. Start The Backend

Open a terminal in the project folder and run:

```bash
cd backend
mvn spring-boot:run
```

The backend should start at:

```text
http://localhost:8080
```

Swagger API documentation will be available at:

```text
http://localhost:8080/swagger-ui.html
```

Keep this terminal open while using the app.

### 2. Start The Frontend

Open a second terminal in the project folder and run:

```bash
cd frontend
npm install
```

Now create your local environment file.

On Windows PowerShell:

```powershell
Copy-Item env.example .env
```

On macOS/Linux:

```bash
cp env.example .env
```

Make sure `.env` contains this value:

```text
VITE_API_URL=http://localhost:8080
```

Then start the frontend:

```bash
npm run dev
```

Open the website in your browser:

```text
http://localhost:5173
```

## Add A YouTube Video To A Post

In the post editor, paste a regular YouTube link into the `Paste YouTube link` field above the content editor, then click `Add YouTube Video`.

The editor will insert the responsive video embed into the post content for you.

## Local Database

Local development uses a file-based H2 database stored in `backend/data/`.

Posts, users, and comments will stay saved after the backend restarts. The `backend/data/` folder is ignored by Git because it contains local database files.

## Helpful URLs

- Frontend app: `http://localhost:5173`
- Backend API: `http://localhost:8080`
- Swagger API docs: `http://localhost:8080/swagger-ui.html`

## User Roles

The app has two user roles:

- `USER`: can read posts, comment, create posts, and view posts from all users
- `ADMIN`: can create, edit, and delete all posts

## Main API Endpoints

These are some of the backend routes.

### Public Routes

- `GET /api/posts/public` - get all published posts
- `GET /api/posts/public/slug/{slug}` - get one post by slug
- `GET /api/posts/public/search` - search posts
- `GET /api/comments/public/post/{postId}` - get comments for one post

### Login And Protected Routes

- `POST /api/auth/register` - create a new user account
- `POST /api/auth/login` - log in
- `POST /api/posts` - create a post as the logged-in user
- `PUT /api/posts/{id}` - update a post as admin
- `DELETE /api/posts/{id}` - delete a post as admin
- `GET /api/posts/my-posts` - get all posts as a logged-in user

For the complete API list, use Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Database Tables

The backend uses these main tables:

- `users`: stores account information
- `posts`: stores blog posts
- `comments`: stores comments

## Deployment

This project includes a `render.yaml` file for Render.

That file tells Render to create:

- a Docker web service for the Spring Boot backend
- a static site for the React frontend
- a PostgreSQL database for production data

Render Blueprints use `render.yaml` from the repository root. Render also lets services reference Postgres connection strings through `fromDatabase`, and static sites can use `routes` to rewrite browser routes back to `index.html`.

## Blog Theme Ideas

This project is designed for posts about music in movies, such as:

- film scores and soundtracks
- famous movie music scenes
- composer spotlights
- music analysis in cinema

## Author

Marius - Portfolio Project
