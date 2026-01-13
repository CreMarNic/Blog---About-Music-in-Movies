import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { postsAPI, categoriesAPI } from '../services/api';
import PostCard from '../components/PostCard';
import './CategoryPage.css';

const CategoryPage = () => {
  const { slug } = useParams();
  const [category, setCategory] = useState(null);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    loadCategory();
    loadPosts();
  }, [slug, currentPage]);

  const loadCategory = async () => {
    try {
      const data = await categoriesAPI.getBySlug(slug);
      setCategory(data);
    } catch (error) {
      console.error('Error loading category:', error);
    }
  };

  const loadPosts = async () => {
    try {
      setLoading(true);
      const data = await postsAPI.getByCategory(slug, currentPage, 10);
      setPosts(data.content || []);
      setTotalPages(data.totalPages || 0);
    } catch (error) {
      console.error('Error loading posts:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading && !category) {
    return <div className="category-page-loading">Loading...</div>;
  }

  if (!category) {
    return <div className="category-page-error">Category not found</div>;
  }

  return (
    <div className="category-page">
      <div className="category-page-header">
        <h1>{category.name}</h1>
        {category.description && <p className="category-description">{category.description}</p>}
      </div>

      {loading ? (
        <div className="category-page-loading">Loading posts...</div>
      ) : posts.length === 0 ? (
        <div className="category-page-empty">No posts in this category yet.</div>
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

export default CategoryPage;
