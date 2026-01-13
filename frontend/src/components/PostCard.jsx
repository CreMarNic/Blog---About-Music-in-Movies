import { Link } from 'react-router-dom';
import { format } from 'date-fns';
import './PostCard.css';

const PostCard = ({ post }) => {
  return (
    <article className="post-card">
      {post.featuredImageUrl && (
        <div className="post-card-image">
          <img src={post.featuredImageUrl} alt={post.title} />
        </div>
      )}
      <div className="post-card-content">
        <div className="post-card-meta">
          <span className="post-card-date">
            {post.publishedAt ? format(new Date(post.publishedAt), 'MMM d, yyyy') : 'Draft'}
          </span>
          {post.categories && post.categories.length > 0 && (
            <span className="post-card-category">
              {post.categories[0].name}
            </span>
          )}
        </div>
        <h2 className="post-card-title">
          <Link to={`/post/${post.slug}`}>{post.title}</Link>
        </h2>
        {post.excerpt && (
          <p className="post-card-excerpt">{post.excerpt}</p>
        )}
        <div className="post-card-footer">
          <span className="post-card-author">By {post.author?.username || 'Unknown'}</span>
          {post.viewsCount > 0 && (
            <span className="post-card-views">{post.viewsCount} views</span>
          )}
        </div>
      </div>
    </article>
  );
};

export default PostCard;
