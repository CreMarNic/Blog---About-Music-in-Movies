import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { postsAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { format } from 'date-fns';
import './Dashboard.css';

const Dashboard = () => {
  const { isAuthor } = useAuth();
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    total: 0,
    published: 0,
    drafts: 0,
  });

  useEffect(() => {
    if (!isAuthor()) {
      return;
    }
    loadPosts();
  }, []);

  const loadPosts = async () => {
    try {
      setLoading(true);
      const data = await postsAPI.getMyPosts(0, 100);
      setPosts(data.content || []);
      
      const published = (data.content || []).filter(p => p.status === 'PUBLISHED').length;
      const drafts = (data.content || []).filter(p => p.status === 'DRAFT').length;
      
      setStats({
        total: data.totalElements || 0,
        published,
        drafts,
      });
    } catch (error) {
      console.error('Error loading posts:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this post?')) {
      return;
    }

    try {
      await postsAPI.delete(id);
      loadPosts();
    } catch (error) {
      console.error('Error deleting post:', error);
      alert('Failed to delete post');
    }
  };

  if (!isAuthor()) {
    return (
      <div className="dashboard-error">
        <h2>Access Denied</h2>
        <p>You need to be an Author or Admin to access the dashboard.</p>
      </div>
    );
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Dashboard</h1>
        <Link to="/post-editor" className="btn btn-primary">
          + New Post
        </Link>
      </div>

      <div className="dashboard-stats">
        <div className="stat-card">
          <h3>Total Posts</h3>
          <p className="stat-number">{stats.total}</p>
        </div>
        <div className="stat-card">
          <h3>Published</h3>
          <p className="stat-number">{stats.published}</p>
        </div>
        <div className="stat-card">
          <h3>Drafts</h3>
          <p className="stat-number">{stats.drafts}</p>
        </div>
      </div>

      {loading ? (
        <div className="dashboard-loading">Loading posts...</div>
      ) : posts.length === 0 ? (
        <div className="dashboard-empty">
          <p>You haven't created any posts yet.</p>
          <Link to="/post-editor" className="btn btn-primary">
            Create Your First Post
          </Link>
        </div>
      ) : (
        <div className="dashboard-posts">
          <h2>Your Posts</h2>
          <table className="posts-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Status</th>
                <th>Published</th>
                <th>Views</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {posts.map((post) => (
                <tr key={post.id}>
                  <td>
                    <Link to={`/post/${post.slug}`}>{post.title}</Link>
                  </td>
                  <td>
                    <span className={`status-badge status-${post.status.toLowerCase()}`}>
                      {post.status}
                    </span>
                  </td>
                  <td>
                    {post.publishedAt
                      ? format(new Date(post.publishedAt), 'MMM d, yyyy')
                      : '-'}
                  </td>
                  <td>{post.viewsCount || 0}</td>
                  <td>
                    <div className="action-buttons">
                      <Link
                        to={`/post-editor/${post.id}`}
                        className="btn btn-small btn-secondary"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDelete(post.id)}
                        className="btn btn-small btn-danger"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
