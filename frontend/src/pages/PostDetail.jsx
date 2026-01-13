import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { format } from 'date-fns';
import { postsAPI, commentsAPI } from '../services/api';
import CommentSection from '../components/CommentSection';
import './PostDetail.css';

const PostDetail = () => {
  const { slug } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadPost();
  }, [slug]);

  const loadPost = async () => {
    try {
      setLoading(true);
      const data = await postsAPI.getBySlug(slug);
      setPost(data);
    } catch (error) {
      setError('Post not found');
      console.error('Error loading post:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="post-detail-loading">Loading post...</div>;
  }

  if (error || !post) {
    return <div className="post-detail-error">{error || 'Post not found'}</div>;
  }

  return (
    <div className="post-detail">
      <article className="post-detail-content">
        {post.featuredImageUrl && (
          <div className="post-detail-image">
            <img src={post.featuredImageUrl} alt={post.title} />
          </div>
        )}
        
        <div className="post-detail-header">
          <div className="post-detail-meta">
            <span className="post-detail-date">
              {post.publishedAt ? format(new Date(post.publishedAt), 'MMMM d, yyyy') : 'Draft'}
            </span>
            {post.categories && post.categories.length > 0 && (
              <div className="post-detail-categories">
                {post.categories.map((cat) => (
                  <Link key={cat.id} to={`/category/${cat.slug}`} className="category-tag">
                    {cat.name}
                  </Link>
                ))}
              </div>
            )}
          </div>
          <h1 className="post-detail-title">{post.title}</h1>
          {post.excerpt && <p className="post-detail-excerpt">{post.excerpt}</p>}
          <div className="post-detail-author">
            By <strong>{post.author?.username || 'Unknown'}</strong>
            {post.viewsCount > 0 && <span className="views-count"> • {post.viewsCount} views</span>}
          </div>
        </div>

        <div 
          className="post-detail-body"
          dangerouslySetInnerHTML={{ __html: post.content || '' }}
        />

        {post.tags && post.tags.length > 0 && (
          <div className="post-detail-tags">
            <strong>Tags: </strong>
            {post.tags.map((tag) => (
              <Link key={tag.id} to={`/tag/${tag.slug}`} className="tag-link">
                #{tag.name}
              </Link>
            ))}
          </div>
        )}
      </article>

      <CommentSection postId={post.id} />
    </div>
  );
};

export default PostDetail;
