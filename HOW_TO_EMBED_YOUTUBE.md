# 🎥 How to Embed a YouTube Video in Your Blog Post

## Simple Step-by-Step Guide

### Method 1: Using YouTube's Embed Button (Easiest!)

1. **Go to YouTube**
   - Open YouTube in your browser
   - Find the video you want to embed

2. **Get the Embed Code**
   - Click the **"Share"** button below the video
   - Click **"Embed"** in the popup
   - **Copy the entire iframe code** (it will look like this):
     ```html
     <iframe width="560" height="315" src="https://www.youtube.com/embed/VIDEO_ID" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
     ```

3. **Paste in Your Blog Post**
   - Go to your blog post editor
   - In the **Content** field, paste the iframe code
   - That's it! The video will appear in your post

---

### Method 2: Manual Method (If you know the video ID)

1. **Get the Video ID from YouTube URL**
   - Example URL: `https://www.youtube.com/watch?v=dQw4w9WgXcQ`
   - Video ID is: `dQw4w9WgXcQ` (the part after `v=`)

2. **Paste this code in your Content field** (replace `VIDEO_ID`):
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

3. **Replace `VIDEO_ID`** with your actual video ID

---

## 📝 Complete Example

Here's what a complete post with a video might look like:

**Title:**
```
Exploring John Williams' Iconic Star Wars Score
```

**Content:**
```html
<p>John Williams' score for Star Wars revolutionized film music. In this video, we explore the iconic themes that defined a generation.</p>

<div class="video-wrapper">
  <iframe 
    width="560" 
    height="315" 
    src="https://www.youtube.com/embed/dQw4w9WgXcQ" 
    frameborder="0" 
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
    allowfullscreen>
  </iframe>
</div>

<p>As we can see in the video above, the main theme combines classical orchestration with modern cinematic techniques...</p>
```

---

## ✅ Quick Checklist

- [ ] Found your YouTube video
- [ ] Clicked Share → Embed
- [ ] Copied the iframe code
- [ ] Pasted it in the Content field of your post
- [ ] Set status to "Published"
- [ ] Clicked "Create Post"

---

## 💡 Tips

1. **Placement**: Add some text before and after the video for context
2. **Featured Image**: Use a screenshot from the video as the featured image
3. **Responsive**: The `video-wrapper` class makes videos work on mobile too
4. **Multiple Videos**: You can add multiple videos in one post!

---

## 🎬 That's It!

Your YouTube video will now be embedded and playable directly in your blog post!
