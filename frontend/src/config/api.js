// API Configuration
export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  // Auth
  REGISTER: `${API_BASE_URL}/api/auth/register`,
  LOGIN: `${API_BASE_URL}/api/auth/login`,
  
  // Posts
  POSTS: `${API_BASE_URL}/api/posts`,
  POSTS_PUBLIC: `${API_BASE_URL}/api/posts/public`,
  POST_BY_SLUG: (slug) => `${API_BASE_URL}/api/posts/public/slug/${slug}`,
  POST_BY_ID: (id) => `${API_BASE_URL}/api/posts/${id}`,
  POSTS_SEARCH: `${API_BASE_URL}/api/posts/public/search`,
  POSTS_BY_CATEGORY: (slug) => `${API_BASE_URL}/api/posts/public/category/${slug}`,
  POSTS_BY_TAG: (slug) => `${API_BASE_URL}/api/posts/public/tag/${slug}`,
  MY_POSTS: `${API_BASE_URL}/api/posts/my-posts`,
  
  // Categories
  CATEGORIES: `${API_BASE_URL}/api/categories`,
  CATEGORIES_PUBLIC: `${API_BASE_URL}/api/categories/public`,
  CATEGORY_BY_ID: (id) => `${API_BASE_URL}/api/categories/${id}`,
  CATEGORY_BY_SLUG: (slug) => `${API_BASE_URL}/api/categories/public/slug/${slug}`,
  
  // Tags
  TAGS: `${API_BASE_URL}/api/tags`,
  TAGS_PUBLIC: `${API_BASE_URL}/api/tags/public`,
  TAG_BY_ID: (id) => `${API_BASE_URL}/api/tags/${id}`,
  TAG_BY_SLUG: (slug) => `${API_BASE_URL}/api/tags/public/slug/${slug}`,
  
  // Comments
  COMMENTS: `${API_BASE_URL}/api/comments`,
  COMMENTS_BY_POST: (postId) => `${API_BASE_URL}/api/comments/public/post/${postId}`,
  COMMENT_BY_ID: (id) => `${API_BASE_URL}/api/comments/${id}`,
};
