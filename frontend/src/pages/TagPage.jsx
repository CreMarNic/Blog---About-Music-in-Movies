import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { postsAPI, tagsAPI } from '../services/api';
import PostCard from '../components/PostCard';
import './TagPage.css';

const TagPage = () => {
  const { slug } = useParams();
  const [tag, setTag] = useState(null);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    loadTag();
    loadPosts();
  }, [slug, currentPage]);

  const loadTag = async () => {
    try {
      const data = await tagsAPI.getBySlug(slug);
      setTag(data);
    } catch (error) {
      console.error('Error loading tag:', error);
    }
  };

  const loadPosts = async () => {
    try {
      setLoading(true);
      const data = await postsAPI.getByTag(slug, currentPage, 10);
      setPosts(data.content || []);
      setTotalPages(data.totalPages || 0);
    } catch (error) {
      console.error('Error loading posts:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading && !tag) {
    return <div className="tag-page-loading">Loading...</div>;
  }

  if (!tag) {
    return <div className="tag-page-error">Tag not found</div>;
  }

  return (
    <div className="tag-page">
      <div className="tag-page-header">
        <h1>#{tag.name}</h1>
      </div>

      {loading ? (
        <div className="tag-page-loading">Loading posts...</div>
      ) : posts.length === 0 ? (
        <div className="tag-page-empty">No posts with this tag yet.</div>
      ) : (
        <>
          <div className="posts-grid">
            {posts.map((post) => (
              <PostCard key={post.id} post={post} />
            ))}
          </div>
          
          {totalPages > 1 && (
            <div className="pagination">
              <button
                onClick={() => setCurrentPage(p => Math.max(0, p - 1))}
                disabled={currentPage === 0}
              >
                Previous
              </button>
              <span>Page {currentPage + 1} of {totalPages}</span>
              <button
                onClick={() => setCurrentPage(p => Math.min(totalPages - 1, p + 1))}
                disabled={currentPage >= totalPages - 1}
              >
                Next
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default TagPage;
