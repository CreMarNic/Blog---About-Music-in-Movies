import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { commentsAPI } from '../services/api';
import { format } from 'date-fns';
import './CommentSection.css';

const CommentSection = ({ postId }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(true);
  const { isAuthenticated, user } = useAuth();

  useEffect(() => {
    loadComments();
  }, [postId]);

  const loadComments = async () => {
    try {
      const data = await commentsAPI.getByPost(postId);
      setComments(data || []);
    } catch (error) {
      console.error('Error loading comments:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    try {
      await commentsAPI.create(postId, { content: newComment });
      setNewComment('');
      loadComments();
    } catch (error) {
      console.error('Error creating comment:', error);
      alert('Failed to post comment. Please try again.');
    }
  };

  const renderComment = (comment) => (
    <div key={comment.id} className="comment">
      <div className="comment-header">
        <strong>{comment.author?.username || comment.authorName || 'Anonymous'}</strong>
        <span className="comment-date">
          {format(new Date(comment.createdAt), 'MMM d, yyyy')}
        </span>
      </div>
      <div className="comment-content">{comment.content}</div>
      {comment.replies && comment.replies.length > 0 && (
        <div className="comment-replies">
          {comment.replies.map(renderComment)}
        </div>
      )}
    </div>
  );

  return (
    <div className="comment-section">
      <h2>Comments</h2>
      
      {isAuthenticated ? (
        <form onSubmit={handleSubmit} className="comment-form">
          <textarea
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Write a comment..."
            rows="4"
            className="comment-input"
          />
          <button type="submit" className="comment-submit">Post Comment</button>
        </form>
      ) : (
        <p className="comment-login-prompt">
          <a href="/login">Sign in</a> to leave a comment
        </p>
      )}

      {loading ? (
        <div className="comment-loading">Loading comments...</div>
      ) : comments.length === 0 ? (
        <p className="comment-empty">No comments yet. Be the first to comment!</p>
      ) : (
        <div className="comments-list">
          {comments.map(renderComment)}
        </div>
      )}
    </div>
  );
};

export default CommentSection;
