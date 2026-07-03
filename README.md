# About Music in Movies - Blog Platform

This project is a full-stack blog website about music in movies.

It has two main parts:

- `backend`: a Spring Boot API written in Java
- `frontend`: a React website built with Vite

The backend stores users, posts, categories, tags, and comments. The frontend is what people see and use in the browser.

## What You Can Do With This App

- Read published blog posts
- Search posts
- Filter posts by category or tag
- Register and log in
- Leave comments
- Create, edit, and delete posts as an author or admin
- Write rich text blog posts with a text editor

## Tools Used

### Backend

- Java
- Spring Boot
- Spring Security
- JWT authentication
- Spring Data JPA
- H2 database for local development
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

## Helpful URLs

- Frontend app: `http://localhost:5173`
- Backend API: `http://localhost:8080`
- Swagger API docs: `http://localhost:8080/swagger-ui.html`

## User Roles

The app has three user roles:

- `USER`: can read posts and comment
- `AUTHOR`: can create and manage their own posts
- `ADMIN`: can access all features

## Main API Endpoints

These are some of the backend routes.

### Public Routes

- `GET /api/posts/public` - get all published posts
- `GET /api/posts/public/slug/{slug}` - get one post by slug
- `GET /api/posts/public/search` - search posts
- `GET /api/categories/public` - get all categories
- `GET /api/tags/public` - get all tags
- `GET /api/comments/public/post/{postId}` - get comments for one post

### Login And Protected Routes

- `POST /api/auth/register` - create a new user account
- `POST /api/auth/login` - log in
- `POST /api/posts` - create a post
- `PUT /api/posts/{id}` - update a post
- `DELETE /api/posts/{id}` - delete a post
- `GET /api/posts/my-posts` - get posts written by the logged-in user

For the complete API list, use Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Database Tables

The backend uses these main tables:

- `users`: stores account information
- `posts`: stores blog posts
- `categories`: stores post categories
- `tags`: stores post tags
- `comments`: stores comments

## Common Problems

### The frontend cannot connect to the backend

Check that:

- the backend is running
- the backend is running on `http://localhost:8080`
- `frontend/.env` contains `VITE_API_URL=http://localhost:8080`
- you restarted `npm run dev` after editing `.env`

### Port 8080 is already in use

Another app is already using the backend port. Stop that app, or change the backend port in:

```text
backend/src/main/resources/application.properties
```

### Port 5173 is already in use

Vite may choose another port automatically. Read the terminal output and open the URL it shows.

### npm install fails

Try deleting the installed dependencies and installing again:

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

On Windows PowerShell:

```powershell
cd frontend
Remove-Item -Recurse -Force node_modules
Remove-Item package-lock.json
npm install
```

## Deployment

This project includes a `render.yaml` file for Render.

That file tells Render to create:

- a Docker web service for the Spring Boot backend
- a static site for the React frontend
- a PostgreSQL database for production data

Render Blueprints use `render.yaml` from the repository root. Render also lets services reference Postgres connection strings through `fromDatabase`, and static sites can use `routes` to rewrite browser routes back to `index.html`.

### Manual Deploy On Render

Use this option if you want to create each Render service yourself instead of using the Blueprint.

#### 1. Push The Project To GitHub

Render deploys from a Git repository.

```bash
git add .
git commit -m "Prepare app for Render"
git push
```

#### 2. Create The PostgreSQL Database

1. Log in to Render.
2. Click **New**.
3. Choose **PostgreSQL**.
4. Use a name like:

```text
music-movies-blog-db
```

5. Choose the same region you will use for the backend.
6. Choose the free plan if available.
7. Click **Create Database**.
8. After it is created, copy the **Internal Database URL**.

The internal database URL will look similar to:

```text
postgresql://user:password@host:5432/database
```

#### 3. Create The Backend Web Service

1. Click **New**.
2. Choose **Web Service**.
3. Connect your GitHub repository.
4. Use these settings:

```text
Name: music-movies-blog-api
Runtime: Docker
Branch: main
Dockerfile Path: ./Dockerfile
Docker Context: .
Region: same region as your database
Plan: free, if available
```

5. Add these environment variables:

```text
DATABASE_URL=your_internal_database_url
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_FORMAT_SQL=false
SPRING_H2_CONSOLE_ENABLED=false
JWT_SECRET=use-a-long-random-secret-value
```

For `DATABASE_URL`, paste the internal database URL from the PostgreSQL service.

For `JWT_SECRET`, use a long random value. Do not use the default local development secret.

6. Click **Create Web Service**.
7. Wait for the backend to deploy.
8. Open the backend URL Render gives you.

It should look like:

```text
https://music-movies-blog-api.onrender.com
```

You can also check Swagger:

```text
https://music-movies-blog-api.onrender.com/swagger-ui.html
```

#### 4. Create The Frontend Static Site

1. Click **New**.
2. Choose **Static Site**.
3. Connect the same GitHub repository.
4. Use these settings:

```text
Name: music-movies-blog-frontend
Branch: main
Build Command: cd frontend && npm install && npm run build
Publish Directory: frontend/dist
```

5. Add this environment variable:

```text
VITE_API_URL=https://your-backend-url.onrender.com
```

Replace `https://your-backend-url.onrender.com` with your real backend URL from step 3.

6. Add a rewrite rule for React Router:

```text
Source: /*
Destination: /index.html
Action: Rewrite
```

7. Click **Create Static Site**.
8. Wait for the frontend to deploy.
9. Open the frontend URL Render gives you.

It should look like:

```text
https://music-movies-blog-frontend.onrender.com
```

#### 5. If The Frontend Cannot Reach The Backend

Check these first:

- The backend deploy finished successfully.
- `VITE_API_URL` in the frontend service is the real backend URL.
- The backend URL starts with `https://`.
- You redeployed the frontend after changing `VITE_API_URL`.
- The backend environment variable `DATABASE_URL` uses the internal PostgreSQL URL.

### Blueprint Deploy On Render

1. Push this project to GitHub.
2. Log in to Render.
3. Click **New**.
4. Choose **Blueprint**.
5. Connect your GitHub repository.
6. Render should find `render.yaml` automatically.
7. Review the services:
   - `music-movies-blog-api`
   - `music-movies-blog-frontend`
   - `music-movies-blog-db`
8. Click **Apply** or **Create Blueprint**.
9. Wait for the database, backend, and frontend to finish deploying.

After deployment, Render will give you public URLs like:

```text
https://music-movies-blog-api.onrender.com
https://music-movies-blog-frontend.onrender.com
```

Open the frontend URL in your browser.

### Important Render Setting

The frontend is built with this environment variable:

```text
VITE_API_URL=https://music-movies-blog-api.onrender.com
```

If Render gives your backend a different URL, update `VITE_API_URL` in the frontend service settings:

1. Open `music-movies-blog-frontend` in Render.
2. Go to **Environment**.
3. Change `VITE_API_URL` to your real backend URL.
4. Save and redeploy the frontend.

### Render Environment Variables

The Blueprint sets these backend variables automatically:

- `DATABASE_URL`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`
- `SPRING_JPA_HIBERNATE_DDL_AUTO`
- `SPRING_JPA_SHOW_SQL`
- `SPRING_JPA_FORMAT_SQL`
- `SPRING_H2_CONSOLE_ENABLED`
- `JWT_SECRET`

`JWT_SECRET` is generated by Render, so you do not need to commit a real secret to GitHub.

### Backend On Railway

1. Push the project to GitHub.
2. Connect the GitHub repository to Railway.
3. Set the required environment variables.
4. Deploy the backend.

Important backend environment variables:

- `jwt.secret`
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

### Frontend On Netlify Or Vercel

Build the frontend:

```bash
cd frontend
npm run build
```

Deploy the generated `dist` folder.

Set this frontend environment variable in your hosting provider:

```text
VITE_API_URL=https://your-backend-url.com
```

Replace `https://your-backend-url.com` with the real deployed backend URL.

## Blog Theme Ideas

This project is designed for posts about music in movies, such as:

- film scores and soundtracks
- famous movie music scenes
- composer spotlights
- music analysis in cinema

## Author

Marius - Portfolio Project
