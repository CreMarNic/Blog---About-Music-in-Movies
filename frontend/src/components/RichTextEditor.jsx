import { useState } from 'react';
import './RichTextEditor.css';

const RichTextEditor = ({ value, onChange, placeholder = 'Write your post content here...' }) => {
  const [showHelp, setShowHelp] = useState(false);
  const [videoUrl, setVideoUrl] = useState('');
  const [videoError, setVideoError] = useState('');

  const getYouTubeVideoId = (url) => {
    const trimmedUrl = url.trim();

    if (!trimmedUrl) {
      return null;
    }

    const patterns = [
      /(?:youtube\.com\/watch\?v=)([a-zA-Z0-9_-]{11})/,
      /(?:youtube\.com\/embed\/)([a-zA-Z0-9_-]{11})/,
      /(?:youtu\.be\/)([a-zA-Z0-9_-]{11})/,
      /(?:youtube\.com\/shorts\/)([a-zA-Z0-9_-]{11})/,
    ];

    for (const pattern of patterns) {
      const match = trimmedUrl.match(pattern);
      if (match) {
        return match[1];
      }
    }

    return null;
  };

  const handleAddVideo = () => {
    const videoId = getYouTubeVideoId(videoUrl);

    if (!videoId) {
      setVideoError('Paste a valid YouTube video link.');
      return;
    }

    const embedCode = `<div class="video-wrapper">
  <iframe
    src="https://www.youtube.com/embed/${videoId}"
    title="YouTube video"
    frameborder="0"
    allowfullscreen>
  </iframe>
</div>`;

    const nextContent = value?.trim()
      ? `${value}\n\n${embedCode}`
      : embedCode;

    onChange(nextContent);
    setVideoUrl('');
    setVideoError('');
  };

  return (
    <div className="rich-text-editor">
      <div className="editor-toolbar">
        <button
          type="button"
          onClick={() => setShowHelp(!showHelp)}
          className="help-button"
        >
          {showHelp ? '▼' : '▶'} HTML Help
        </button>
      </div>

      <div className="video-insert">
        <input
          type="url"
          value={videoUrl}
          onChange={(e) => {
            setVideoUrl(e.target.value);
            setVideoError('');
          }}
          placeholder="Paste YouTube link"
          className="video-url-input"
        />
        <button
          type="button"
          onClick={handleAddVideo}
          className="add-video-button"
        >
          Add YouTube Video
        </button>
      </div>

      {videoError && <p className="video-error">{videoError}</p>}
      
      {showHelp && (
        <div className="editor-help">
          <h4>HTML Tips:</h4>
          <ul>
            <li><strong>Bold:</strong> &lt;strong&gt;text&lt;/strong&gt;</li>
            <li><strong>Italic:</strong> &lt;em&gt;text&lt;/em&gt;</li>
            <li><strong>Paragraph:</strong> &lt;p&gt;text&lt;/p&gt;</li>
            <li><strong>Video:</strong> Paste a YouTube link above and click Add YouTube Video</li>
            <li><strong>Link:</strong> &lt;a href="url"&gt;text&lt;/a&gt;</li>
            <li><strong>Image:</strong> &lt;img src="url" alt="description" /&gt;</li>
          </ul>
          <p><strong>Example Video Embed:</strong></p>
          <pre>{`<div class="video-wrapper">
  <iframe src="https://www.youtube.com/embed/VIDEO_ID" 
    width="560" height="315" 
    frameborder="0" allowfullscreen>
  </iframe>
</div>`}</pre>
        </div>
      )}
      
      <textarea
        value={value || ''}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        className="editor-textarea"
      />
      <p className="editor-tip">
        You can write HTML directly here or use the YouTube field above to insert a video.
      </p>
    </div>
  );
};

export default RichTextEditor;
