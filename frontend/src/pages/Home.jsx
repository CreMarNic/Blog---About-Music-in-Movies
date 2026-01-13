import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { postsAPI, categoriesAPI } from '../services/api';
import PostCard from '../components/PostCard';
import './Home.css';

const Home = () => {
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    loadPosts();
    loadCategories();
  }, [currentPage]);

  const loadPosts = async () => {
    try {
      setLoading(true);
      const data = await postsAPI.getAll(currentPage, 10);
      setPosts(data.content || []);
      setTotalPages(data.totalPages || 0);
    } catch (error) {
      console.error('Error loading posts:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadCategories = async () => {
    try {
      const data = await categoriesAPI.getAll();
      setCategories(data);
    } catch (error) {
      console.error('Error loading categories:', error);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      loadPosts();
      return;
    }
    
    try {
      setLoading(true);
      const data = await postsAPI.search(searchQuery, 0, 10);
      setPosts(data.content || []);
      setTotalPages(data.totalPages || 0);
      setCurrentPage(0);
    } catch (error) {
      console.error('Error searching posts:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="home">
      <div className="home-header">
        <h1>🎵 About Music in Movies</h1>
        <p className="subtitle">Exploring the soundtracks and scores that make movies unforgettable</p>
      </div>

      <div className="home-content">
        <div className="home-main">
          <form onSubmit={handleSearch} className="search-form">
            <input
              type="text"
              placeholder="Search posts..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="search-input"
            />
            <button type="submit" className="search-button">Search</button>
          </form>

          {loading ? (
            <div className="loading">Loading posts...</div>
          ) : posts.length === 0 ? (
            <div className="no-posts">No posts found. Be the first to write about music in movies!</div>
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

        <aside className="home-sidebar">
          <div className="sidebar-section">
            <h3>Categories</h3>
            {categories.length === 0 ? (
              <p className="empty-state">No categories yet</p>
            ) : (
              <ul className="category-list">
                {categories.map((category) => (
                  <li key={category.id}>
                    <Link to={`/category/${category.slug}`}>{category.name}</Link>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </aside>
      </div>
    </div>
  );
};

export default Home;
