import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { postsAPI, categoriesAPI, tagsAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import RichTextEditor from '../components/RichTextEditor';
import './PostEditor.css';

const PostEditor = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isAuthor, loading: authLoading } = useAuth();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [tags, setTags] = useState([]);
  
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    excerpt: '',
    featuredImageUrl: '',
    status: 'DRAFT',
    categoryIds: [],
    tagIds: [],
  });

  useEffect(() => {
    const initialize = async () => {
      try {
        console.log('PostEditor: Initializing...', { authLoading, id });
        
        // Wait for auth to finish loading
        if (authLoading) {
          console.log('PostEditor: Waiting for auth...');
          return;
        }
        
        // Check if user is author
        const isUserAuthor = isAuthor();
        console.log('PostEditor: isAuthor check:', isUserAuthor);
        
        if (!isUserAuthor) {
          console.log('PostEditor: User is not author, redirecting...');
          navigate('/');
          return;
        }
        
        console.log('PostEditor: Loading data...');
        setLoading(true);
        setError(null);
        
        // Load categories and tags in parallel
        const [categoriesData, tagsData] = await Promise.all([
          categoriesAPI.getAll().catch(err => {
            console.error('Error loading categories:', err);
            return [];
          }),
          tagsAPI.getAll().catch(err => {
            console.error('Error loading tags:', err);
            return [];
          })
        ]);
        
        setCategories(categoriesData || []);
        setTags(tagsData || []);
        console.log('PostEditor: Categories and tags loaded', { 
          categories: categoriesData?.length || 0, 
          tags: tagsData?.length || 0 
        });
        
        // Load post if editing
        if (id) {
          console.log('PostEditor: Loading post with id:', id);
          const post = await postsAPI.getById(id);
          setFormData({
            title: post.title || '',
            content: post.content || '',
            excerpt: post.excerpt || '',
            featuredImageUrl: post.featuredImageUrl || '',
            status: post.status || 'DRAFT',
            categoryIds: post.categories?.map(c => c.id) || [],
            tagIds: post.tags?.map(t => t.id) || [],
          });
          console.log('PostEditor: Post loaded');
        }
        
        console.log('PostEditor: Initialization complete');
      } catch (err) {
        console.error('PostEditor: Error initializing editor:', err);
        setError('Failed to load editor. Please try again.');
      } finally {
        setLoading(false);
        console.log('PostEditor: Loading set to false');
      }
    };
    
    initialize();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id, authLoading]);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleContentChange = (content) => {
    setFormData({
      ...formData,
      content,
    });
  };

  const handleCategoryChange = (categoryId) => {
    setFormData({
      ...formData,
      categoryIds: formData.categoryIds.includes(categoryId)
        ? formData.categoryIds.filter(id => id !== categoryId)
        : [...formData.categoryIds, categoryId],
    });
  };

  const handleTagChange = (tagId) => {
    setFormData({
      ...formData,
      tagIds: formData.tagIds.includes(tagId)
        ? formData.tagIds.filter(id => id !== tagId)
        : [...formData.tagIds, tagId],
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);

    try {
      if (id) {
        await postsAPI.update(id, formData);
        alert('Post updated successfully!');
      } else {
        const newPost = await postsAPI.create(formData);
        alert('Post created successfully!');
        navigate(`/post-editor/${newPost.id}`);
        return;
      }
      navigate('/dashboard');
    } catch (err) {
      console.error('Error saving post:', err);
      setError('Failed to save post. Please try again.');
      alert('Failed to save post: ' + (err.response?.data?.message || err.message));
    } finally {
      setSaving(false);
    }
  };

  // Show loading if auth is loading or component is loading
  if (authLoading || loading) {
    console.log('PostEditor: Showing loading state', { authLoading, loading });
    return (
      <div className="post-editor">
        <div className="post-editor-loading">Loading editor...</div>
      </div>
    );
  }

  console.log('PostEditor: Rendering form', { 
    categories: categories.length, 
    tags: tags.length,
    formData: { title: formData.title, status: formData.status }
  });

  // Show error if there's one
  if (error && !loading) {
    return (
      <div className="post-editor">
        <div className="post-editor-error">
          <h2>Error</h2>
          <p>{error}</p>
          <button onClick={() => window.location.reload()} className="btn btn-primary">
            Reload Page
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="post-editor">
      <div className="post-editor-header">
        <h1>{id ? 'Edit Post' : 'Create New Post'}</h1>
      </div>

      {error && (
        <div className="error-message" style={{ margin: '1rem 0', padding: '1rem', background: '#fee', color: '#c33', borderRadius: '5px' }}>
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="post-editor-form">
        <div className="form-group">
          <label htmlFor="title">Title *</label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
            placeholder="Enter post title"
          />
        </div>

        <div className="form-group">
          <label htmlFor="excerpt">Excerpt</label>
          <textarea
            id="excerpt"
            name="excerpt"
            value={formData.excerpt}
            onChange={handleChange}
            rows="3"
            placeholder="Brief description of the post"
          />
        </div>

        <div className="form-group">
          <label htmlFor="featuredImageUrl">Featured Image URL</label>
          <input
            type="url"
            id="featuredImageUrl"
            name="featuredImageUrl"
            value={formData.featuredImageUrl}
            onChange={handleChange}
            placeholder="https://example.com/image.jpg"
          />
        </div>

        <div className="form-group">
          <label>Content *</label>
          <RichTextEditor
            value={formData.content}
            onChange={handleContentChange}
          />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Status</label>
            <select
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <option value="DRAFT">Draft</option>
              <option value="PUBLISHED">Published</option>
            </select>
          </div>
        </div>

        <div className="form-group">
          <label>Categories</label>
          <div className="checkbox-group">
            {categories.length === 0 ? (
              <p style={{ color: '#999', fontStyle: 'italic' }}>No categories available. Create categories first.</p>
            ) : (
              categories.map((category) => (
                <label key={category.id} className="checkbox-label">
                  <input
                    type="checkbox"
                    checked={formData.categoryIds.includes(category.id)}
                    onChange={() => handleCategoryChange(category.id)}
                  />
                  {category.name}
                </label>
              ))
            )}
          </div>
        </div>

        <div className="form-group">
          <label>Tags</label>
          <div className="checkbox-group">
            {tags.length === 0 ? (
              <p style={{ color: '#999', fontStyle: 'italic' }}>No tags available. Create tags first.</p>
            ) : (
              tags.map((tag) => (
                <label key={tag.id} className="checkbox-label">
                  <input
                    type="checkbox"
                    checked={formData.tagIds.includes(tag.id)}
                    onChange={() => handleTagChange(tag.id)}
                  />
                  {tag.name}
                </label>
              ))
            )}
          </div>
        </div>

        <div className="form-actions">
          <button
            type="button"
            onClick={() => navigate('/dashboard')}
            className="btn btn-secondary"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn btn-primary"
            disabled={saving}
          >
            {saving ? 'Saving...' : id ? 'Update Post' : 'Create Post'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default PostEditor;
