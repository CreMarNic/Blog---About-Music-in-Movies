# 🔧 Backend Troubleshooting Guide

## ✅ Backend is Running

The backend is confirmed to be running on port 8080. All endpoints are accessible.

## 🌐 Access URLs

### Swagger UI (API Documentation)
```
http://localhost:8080/swagger-ui.html
```

### API Documentation (JSON)
```
http://localhost:8080/api-docs
```

### H2 Database Console
```
http://localhost:8080/h2-console
```
- JDBC URL: `jdbc:h2:mem:blogdb`
- Username: `sa`
- Password: (leave empty)

### API Endpoints
```
http://localhost:8080/api/posts/public
http://localhost:8080/api/categories/public
http://localhost:8080/api/tags/public
```

## 🐛 Common Issues

### Issue 1: Browser Shows "Cannot Connect" or "ERR_CONNECTION_REFUSED"

**Solutions:**
1. **Check if backend is running:**
   ```bash
   # In PowerShell
   Get-NetTCPConnection -LocalPort 8080
   ```

2. **Restart the backend:**
   ```bash
   cd blog-platform/backend
   mvn spring-boot:run
   ```

3. **Check for port conflicts:**
   - Another application might be using port 8080
   - Change port in `application.properties`:
     ```properties
     server.port=8081
     ```

### Issue 2: Browser Shows "This site can't be reached"

**Solutions:**
1. **Use `localhost` instead of `127.0.0.1`** (or vice versa)
2. **Clear browser cache:**
   - Press `Ctrl + Shift + Delete`
   - Clear cached images and files
3. **Try incognito/private mode**
4. **Disable browser extensions** that might block localhost

### Issue 3: CORS Errors in Browser Console

**Solutions:**
1. **Check frontend API URL:**
   - Make sure `VITE_API_URL` in `.env` is set to `http://localhost:8080`
2. **Backend CORS is already configured** - should work out of the box

### Issue 4: "404 Not Found" on Swagger UI

**Solutions:**
1. **Try the correct path:**
   - Use: `http://localhost:8080/swagger-ui.html`
   - Or: `http://localhost:8080/swagger-ui/index.html`
2. **Check Spring Boot logs** for any errors

### Issue 5: Backend Won't Start

**Solutions:**
1. **Check for compilation errors:**
   ```bash
   cd blog-platform/backend
   mvn clean compile
   ```

2. **Check Java version:**
   ```bash
   java -version
   # Should be Java 17 or higher
   ```

3. **Check Maven:**
   ```bash
   mvn -version
   ```

4. **Check logs** for specific error messages

## 🔄 Restart Backend

### Stop Current Backend
- Press `Ctrl + C` in the terminal where backend is running
- Or close the terminal window

### Start Backend
```bash
cd blog-platform/backend
mvn spring-boot:run
```

You should see:
```
Started BlogApplication in X.XXX seconds
```

## 📊 Verify Backend is Running

### Method 1: PowerShell
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api-docs" -UseBasicParsing
```

### Method 2: Browser
- Open: http://localhost:8080/swagger-ui.html
- Should see Swagger UI interface

### Method 3: Check Port
```powershell
Get-NetTCPConnection -LocalPort 8080
```

## 🔍 Check Backend Logs

Look for these messages in the console:
- ✅ `Started BlogApplication`
- ✅ `Default admin user created!`
- ✅ `Default author user created!`
- ❌ Any error messages in red

## 🌐 Network Issues

### Firewall
- Windows Firewall might block port 8080
- Add exception for Java or port 8080

### Antivirus
- Some antivirus software blocks localhost connections
- Add exception for your development folder

## 📝 Quick Test

Run this in PowerShell to test all endpoints:
```powershell
# Test API Docs
Invoke-WebRequest -Uri "http://localhost:8080/api-docs"

# Test Swagger UI
Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui.html"

# Test Posts API
Invoke-WebRequest -Uri "http://localhost:8080/api/posts/public"
```

All should return `200 OK` status.

## 🆘 Still Having Issues?

1. **Check backend console** for error messages
2. **Check browser console** (F12) for errors
3. **Try different browser** (Chrome, Firefox, Edge)
4. **Restart computer** (sometimes helps with port issues)
5. **Check if another app is using port 8080**
