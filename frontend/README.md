# 🎵 About Music in Movies - Frontend

A modern React frontend for the "About Music in Movies" blog platform with CMS capabilities.

## 🚀 Features

- **Public Blog View**
  - Browse published posts
  - View individual posts with full content
  - Search functionality
  - Filter by categories and tags
  - Responsive design

- **CMS Dashboard** (Author/Admin only)
  - Create and edit blog posts
  - Rich text editor (React Quill)
  - Manage post status (Draft/Published)
  - View post statistics
  - Delete posts

- **Authentication**
  - User registration and login
  - JWT token-based authentication
  - Role-based access control

- **Comments System**
  - Add comments to posts
  - Nested comment replies
  - Comment moderation

## 🛠️ Tech Stack

- **React 19** - UI library
- **React Router** - Routing
- **Axios** - HTTP client
- **React Quill** - Rich text editor
- **date-fns** - Date formatting
- **Vite** - Build tool

## 📋 Prerequisites

- Node.js 18+ and npm
- Backend API running (see backend README)

## 🔧 Setup

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Configure API URL:**
   - Copy `env.example` to `.env`
   - Update `VITE_API_URL` with your backend URL
   - Default: `http://localhost:8080`

3. **Run development server:**
   ```bash
   npm run dev
   ```

4. **Build for production:**
   ```bash
   npm run build
   ```

## 📁 Project Structure

```
src/
├── components/      # Reusable components
│   ├── Navbar.jsx
│   ├── PostCard.jsx
│   ├── RichTextEditor.jsx
│   ├── CommentSection.jsx
│   └── ProtectedRoute.jsx
├── pages/          # Page components
│   ├── Home.jsx
│   ├── PostDetail.jsx
│   ├── PostEditor.jsx
│   ├── Dashboard.jsx
│   ├── Login.jsx
│   ├── Register.jsx
│   ├── CategoryPage.jsx
│   └── TagPage.jsx
├── services/        # API services
│   └── api.js
├── context/         # React Context
│   └── AuthContext.jsx
├── config/          # Configuration
│   └── api.js
└── App.jsx          # Main app component
```

## 🎨 Features in Detail

### Public Blog
- **Home Page**: Lists all published posts with pagination
- **Post Detail**: Full post view with comments
- **Category/Tag Pages**: Filtered post listings
- **Search**: Search posts by title or content

### CMS Dashboard
- **Post Management**: Create, edit, delete posts
- **Rich Text Editor**: WYSIWYG editor for post content
- **Statistics**: View post counts and views
- **Status Management**: Toggle between Draft and Published

### Authentication
- **Registration**: Create new user accounts
- **Login**: Authenticate with username/email and password
- **Role-Based Access**: Different permissions for USER, AUTHOR, ADMIN

## 🔐 User Roles

- **USER**: Can view posts and leave comments
- **AUTHOR**: Can create and manage posts
- **ADMIN**: Full access to all features

## 🌐 API Integration

The frontend connects to the Spring Boot backend API. Make sure the backend is running and accessible at the URL specified in `.env`.

## 📱 Responsive Design

The frontend is fully responsive and works on:
- Desktop
- Tablet
- Mobile

## 🚀 Deployment

### Netlify
1. Build the project: `npm run build`
2. Deploy the `dist` folder to Netlify
3. Set environment variable `VITE_API_URL` in Netlify dashboard

### Vercel
1. Connect your GitHub repository
2. Set build command: `npm run build`
3. Set output directory: `dist`
4. Add environment variable `VITE_API_URL`

### Railway
1. Build the project: `npm run build`
2. Deploy using Railway CLI or dashboard
3. Set environment variable `VITE_API_URL`

## 📝 Environment Variables

- `VITE_API_URL`: Backend API URL (default: `http://localhost:8080`)

## 🐛 Troubleshooting

- **CORS errors**: Make sure backend allows requests from frontend origin
- **API connection failed**: Check `VITE_API_URL` in `.env`
- **Authentication issues**: Verify JWT token is being stored in localStorage

## 📄 License

This project is part of a portfolio project.
