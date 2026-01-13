# 🎥 Step-by-Step: How to Post a Video on Your Blog

## Complete Guide - From Start to Finish

### Step 1: Open the Frontend
1. Open your web browser
2. Go to: **http://localhost:5173**
3. You should see the blog homepage

---

### Step 2: Login as Admin
1. Click **"Login"** in the top navigation bar
2. Or go directly to: **http://localhost:5173/login**
3. Enter your credentials:
   - **Username:** `admin`
   - **Password:** `admin123`
4. Click **"Sign In"**
5. You should now be logged in and see "Hello, admin" in the navbar

---

### Step 3: Go to Dashboard
1. After logging in, click **"Dashboard"** in the top navigation
2. Or go to: **http://localhost:5173/dashboard**
3. You'll see your dashboard with post management options

---

### Step 4: Create a New Post
1. Click the **"+ New Post"** button (top right)
2. Or go to: **http://localhost:5173/post-editor**
3. You'll see the post editor form

---

### Step 5: Fill in Post Details

**Title:**
- Enter a title, e.g., "The Best Movie Soundtracks of 2024"

**Excerpt (optional):**
- Write a short description, e.g., "Exploring the most memorable film scores this year"

**Featured Image URL (optional):**
- Add an image URL if you have one
- Example: `https://example.com/image.jpg`

**Status:**
- Choose **"Published"** to make it visible immediately
- Or **"Draft"** to save for later

---

### Step 6: Add Video to Content

This is the important part! Here's how to add a YouTube video:

#### Option A: Using YouTube's Embed Code (Easiest)

1. **Go to YouTube** (in another tab)
2. **Find the video** you want to embed
3. **Click the "Share" button** below the video
4. **Click "Embed"**
5. **Copy the entire iframe code** (it looks like this):
   ```html
   <iframe width="560" height="315" src="https://www.youtube.com/embed/VIDEO_ID" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
   ```

6. **Go back to your blog post editor**
7. **In the Content field** (the big text editor), paste the iframe code
8. The video will appear in your post!

#### Option B: Manual Method

If you know the YouTube video ID:

1. **Get the video ID** from the YouTube URL
   - Example: `https://www.youtube.com/watch?v=dQw4w9WgXcQ`
   - Video ID is: `dQw4w9WgXcQ`

2. **In the Content editor**, paste this code (replace VIDEO_ID):
   ```html
   <div class="video-wrapper">
     <iframe 
       width="560" 
       height="315" 
       src="https://www.youtube.com/embed/VIDEO_ID" 
       frameborder="0" 
       allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
       allowfullscreen>
     </iframe>
   </div>
   ```

3. Replace `VIDEO_ID` with your actual video ID

---

### Step 7: Add Text Around the Video

You can add text before and after the video:

```
Introduction paragraph about the video...

[PASTE VIDEO EMBED CODE HERE]

Analysis paragraph discussing the video content...
```

---

### Step 8: Add Categories and Tags (Optional)

- **Categories:** Check the boxes for relevant categories
- **Tags:** Check the boxes for relevant tags
- (You may need to create categories/tags first if none exist)

---

### Step 9: Save Your Post

1. Scroll down to the bottom
2. Click **"Create Post"** button
3. Your post is now saved!

---

### Step 10: View Your Post

1. After saving, you'll be redirected to the Dashboard
2. Click on your post title to view it
3. Or go to the homepage: **http://localhost:5173**
4. Your post with the video should be visible!

---

## 🎬 Example: Complete Post with Video

Here's what a complete post might look like in the editor:

**Title:**
```
Exploring John Williams' Iconic Star Wars Score
```

**Excerpt:**
```
A deep dive into the musical genius behind the Star Wars soundtrack.
```

**Content:**
```html
<p>John Williams' score for Star Wars revolutionized film music. In this video, we explore the iconic themes that defined a generation.</p>

<div class="video-wrapper">
  <iframe 
    width="560" 
    height="315" 
    src="https://www.youtube.com/embed/YOUR_VIDEO_ID" 
    frameborder="0" 
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
    allowfullscreen>
  </iframe>
</div>

<p>As we can see in the video above, the main theme combines classical orchestration with modern cinematic techniques...</p>
```

---

## ✅ Quick Checklist

- [ ] Frontend is running (http://localhost:5173)
- [ ] Backend is running (http://localhost:8080)
- [ ] Logged in as admin
- [ ] Created new post
- [ ] Added title and content
- [ ] Pasted video embed code
- [ ] Set status to "Published"
- [ ] Clicked "Create Post"
- [ ] Viewed post on homepage

---

## 🆘 Troubleshooting

**Video not showing?**
- Make sure you pasted the complete iframe code
- Check that the video ID is correct
- Try using YouTube's Share → Embed method

**Can't see "New Post" button?**
- Make sure you're logged in as admin or author
- Check the navbar shows "Hello, admin"

**Post not appearing?**
- Make sure status is set to "Published" (not "Draft")
- Refresh the homepage

---

**That's it! You now know how to post videos on your blog! 🎉**
