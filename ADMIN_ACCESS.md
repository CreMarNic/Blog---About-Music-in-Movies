# 🔐 Admin Access Guide

## Default Admin Account

When the backend starts, it automatically creates default users:

### Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ADMIN`
- **Email**: `admin@example.com`

### Author User
- **Username**: `author`
- **Password**: `author123`
- **Role**: `AUTHOR`
- **Email**: `author@example.com`

## How to Login as Admin

1. **Start the backend** (if not running):
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Start the frontend** (if not running):
   ```bash
   cd frontend
   npm run dev
   ```

3. **Go to the login page**:
   - Open http://localhost:5173/login

4. **Login with admin credentials**:
   - Username: `admin`
   - Password: `admin123`

5. **You'll now have admin access**:
   - Can create and edit posts
   - Can manage categories and tags
   - Full access to dashboard

## Alternative: Change Existing User to Admin

If you want to promote an existing user to admin:

### Option 1: Using H2 Console

1. **Access H2 Console**:
   - Go to http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:blogdb`
   - Username: `sa`
   - Password: (leave empty)

2. **Run SQL to update user role**:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
   ```

3. **Logout and login again** to refresh your role.

### Option 2: Register and Update via Database

1. Register a new user normally (they'll be USER by default)
2. Use H2 Console to change their role:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
   ```

## User Roles Explained

- **USER**: Can view posts and leave comments
- **AUTHOR**: Can create and manage their own posts
- **ADMIN**: Full access - can create posts, manage categories, tags, and moderate comments

## Security Note

⚠️ **Important**: The default admin password (`admin123`) is for **development only**!

For production:
1. Change the admin password immediately
2. Use a strong, unique password
3. Consider removing the auto-creation of default users in production
4. Use environment variables for sensitive credentials

## Production Setup

For production, you should:
1. Remove or disable the `DataInitializer` class
2. Create admin users manually through a secure process
3. Use strong passwords
4. Enable proper database persistence (PostgreSQL instead of H2)
