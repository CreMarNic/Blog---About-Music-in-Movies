# 🎵 About Music in Movies - Blog Platform

A full-stack blog platform with CMS capabilities, focused on music in movies. Built with Spring Boot (backend) and React (frontend).

## 🎯 Project Overview

This is a complete blog platform that allows users to:
- Read blog posts about music in movies
- Create and manage posts (Authors/Admins)
- Comment on posts
- Search and filter content
- Manage categories and tags

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3.2.0** - Java framework
- **Spring Security** - Authentication & Authorization
- **JWT** - Token-based authentication
- **Spring Data JPA** - Database access
- **H2/PostgreSQL** - Database
- **Swagger/OpenAPI** - API documentation

### Frontend
- **React 19** - UI library
- **React Router** - Routing
- **React Quill** - Rich text editor
- **Axios** - HTTP client
- **Vite** - Build tool

## 📁 Project Structure

```
blog-platform/
├── backend/          # Spring Boot API
│   ├── src/
│   │   └── main/
│   │       ├── java/com/marius/blog/
│   │       │   ├── model/        # Entities
│   │       │   ├── repository/  # Data access
│   │       │   ├── service/      # Business logic
│   │       │   ├── controller/  # REST endpoints
│   │       │   ├── security/     # JWT & Security
│   │       │   └── dto/          # Data transfer objects
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
└── frontend/         # React application
    ├── src/
    │   ├── components/
    │   ├── pages/
    │   ├── services/
    │   ├── context/
    │   └── config/
    └── package.json
```

## 🚀 Quick Start

### Backend

1. **Navigate to backend:**
   ```bash
   cd backend
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access Swagger UI:**
   - http://localhost:8080/swagger-ui.html

### Frontend

1. **Navigate to frontend:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Configure API URL:**
   - Copy `env.example` to `.env`
   - Set `VITE_API_URL=http://localhost:8080`

4. **Run development server:**
   ```bash
   npm run dev
   ```

5. **Access the application:**
   - http://localhost:5173

## ✨ Features

### Public Features
- ✅ Browse published blog posts
- ✅ View individual posts
- ✅ Search posts
- ✅ Filter by categories and tags
- ✅ Leave comments on posts
- ✅ Responsive design

### CMS Features (Author/Admin)
- ✅ Create and edit blog posts
- ✅ Rich text editor (WYSIWYG)
- ✅ Manage post status (Draft/Published)
- ✅ Upload featured images
- ✅ Assign categories and tags
- ✅ View post statistics
- ✅ Delete posts

### Authentication
- ✅ User registration
- ✅ User login
- ✅ JWT token authentication
- ✅ Role-based access control (USER, AUTHOR, ADMIN)

## 🔐 User Roles

- **USER**: Can view posts and comment
- **AUTHOR**: Can create and manage posts
- **ADMIN**: Full access to all features

## 📡 API Endpoints

### Public Endpoints
- `GET /api/posts/public` - Get all published posts
- `GET /api/posts/public/slug/{slug}` - Get post by slug
- `GET /api/posts/public/search` - Search posts
- `GET /api/categories/public` - Get all categories
- `GET /api/tags/public` - Get all tags
- `GET /api/comments/public/post/{postId}` - Get comments for a post

### Protected Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/posts` - Create post (AUTHOR/ADMIN)
- `PUT /api/posts/{id}` - Update post (AUTHOR/ADMIN)
- `DELETE /api/posts/{id}` - Delete post (AUTHOR/ADMIN)
- `GET /api/posts/my-posts` - Get user's posts

See Swagger UI for complete API documentation.

## 🗄️ Database Schema

- **Users**: id, username, email, password, role, bio, avatar_url
- **Posts**: id, title, slug, content, excerpt, status, author_id, views_count
- **Categories**: id, name, slug, description
- **Tags**: id, name, slug
- **Comments**: id, post_id, user_id, content, parent_id, status

## 🚀 Deployment

### Backend (Railway)
1. Push code to GitHub
2. Connect repository to Railway
3. Set environment variables (JWT_SECRET, DATABASE_URL)
4. Deploy

### Frontend (Netlify/Vercel)
1. Build: `npm run build`
2. Deploy `dist` folder
3. Set `VITE_API_URL` environment variable

## 📝 Environment Variables

### Backend
- `jwt.secret` - JWT secret key
- `spring.datasource.url` - Database URL
- `spring.datasource.username` - Database username
- `spring.datasource.password` - Database password

### Frontend
- `VITE_API_URL` - Backend API URL

## 🎨 Theme

The blog is themed around "Music in Movies" - perfect for writing about:
- Film scores and soundtracks
- Iconic movie music moments
- Composer spotlights
- Music analysis in cinema

## 📄 License

This project is part of a portfolio project.

## 👤 Author

Marius - Portfolio Project

---

**Status**: ✅ Fully functional and ready for deployment!
