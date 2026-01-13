import { useState } from 'react';
import './RichTextEditor.css';

const RichTextEditor = ({ value, onChange, placeholder = 'Write your post content here...' }) => {
  const [showHelp, setShowHelp] = useState(false);

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
      
      {showHelp && (
        <div className="editor-help">
          <h4>💡 HTML Tips:</h4>
          <ul>
            <li><strong>Bold:</strong> &lt;strong&gt;text&lt;/strong&gt;</li>
            <li><strong>Italic:</strong> &lt;em&gt;text&lt;/em&gt;</li>
            <li><strong>Paragraph:</strong> &lt;p&gt;text&lt;/p&gt;</li>
            <li><strong>Video:</strong> Paste YouTube/Vimeo embed code directly</li>
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
        💡 You can write HTML directly here, or paste video embed codes from YouTube/Vimeo!
      </p>
    </div>
  );
};

export default RichTextEditor;
