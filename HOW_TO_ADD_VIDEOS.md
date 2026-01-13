# 🎥 How to Add Videos to Your Blog Posts

## Quick Guide

The blog supports embedding videos from YouTube, Vimeo, and other platforms. Here's how:

## Method 1: Embed YouTube Videos (Recommended)

### Step-by-Step:

1. **Get the YouTube Video ID**
   - Go to your YouTube video
   - Copy the video ID from the URL (e.g., `dQw4w9WgXcQ` from `https://www.youtube.com/watch?v=dQw4w9WgXcQ`)

2. **Create/Edit Your Post**
   - Go to Dashboard → New Post (or Edit existing post)
   - Write your content in the rich text editor

3. **Add Video Embed Code**
   - In the editor, switch to HTML/Source view (if available) or paste directly
   - Paste this code where you want the video:

```html
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
```

**Replace `YOUR_VIDEO_ID` with your actual video ID**

### Example:
For the video `https://www.youtube.com/watch?v=dQw4w9WgXcQ`, use:
```html
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
```

## Method 2: Use YouTube Share Button

1. Go to your YouTube video
2. Click **Share** → **Embed**
3. Copy the iframe code
4. Paste it in your blog post editor

## Method 3: Embed Vimeo Videos

1. Go to your Vimeo video
2. Click **Share** → **Embed**
3. Copy the iframe code
4. Wrap it in a video-wrapper div:

```html
<div class="video-wrapper">
  <iframe 
    src="https://player.vimeo.com/video/YOUR_VIDEO_ID" 
    width="640" 
    height="360" 
    frameborder="0" 
    allow="autoplay; fullscreen; picture-in-picture" 
    allowfullscreen>
  </iframe>
</div>
```

## Method 4: Simple Video Link

You can also just paste the video URL as text, and it will appear as a clickable link:

```
Watch this amazing soundtrack analysis: https://www.youtube.com/watch?v=VIDEO_ID
```

## 📝 Complete Example Post

Here's how a post with a video might look:

```html
<h2>Exploring John Williams' Star Wars Score</h2>

<p>John Williams' music for Star Wars revolutionized film scoring. In this post, we'll analyze the iconic themes...</p>

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

<p>As we can see in the video above, the main theme combines...</p>
```

## 🎨 Tips

1. **Placement**: Add videos after an introductory paragraph
2. **Context**: Always provide context about the video in your text
3. **Featured Image**: Use a screenshot from the video as the featured image
4. **Responsive**: The `video-wrapper` class makes videos responsive on mobile

## ⚠️ Important Notes

- **Direct Video Upload**: Currently not supported (would require file storage and video hosting)
- **Embedding**: Works with any platform that provides iframe embed codes
- **HTML Support**: The rich text editor supports HTML, so embed codes work directly

## 🚀 Supported Platforms

- ✅ YouTube
- ✅ Vimeo  
- ✅ Any platform with iframe embed codes
- ✅ Direct video URLs (as clickable links)

---

**Need Help?** If you're having trouble, make sure you're using the correct video ID and that the iframe code is properly formatted!
