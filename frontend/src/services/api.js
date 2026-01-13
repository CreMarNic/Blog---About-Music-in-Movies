import axios from 'axios';
import { API_BASE_URL, API_ENDPOINTS } from '../config/api';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Handle 401 errors (unauthorized)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth API
export const authAPI = {
  register: async (userData) => {
    const response = await api.post(API_ENDPOINTS.REGISTER, userData);
    return response.data;
  },
  
  login: async (credentials) => {
    const response = await api.post(API_ENDPOINTS.LOGIN, credentials);
    return response.data;
  },
};

// Posts API
export const postsAPI = {
  getAll: async (page = 0, size = 10, sortBy = 'publishedAt', sortDir = 'DESC') => {
    const response = await api.get(API_ENDPOINTS.POSTS_PUBLIC, {
      params: { page, size, sortBy, sortDir }
    });
    return response.data;
  },
  
  getBySlug: async (slug) => {
    const response = await api.get(API_ENDPOINTS.POST_BY_SLUG(slug));
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(API_ENDPOINTS.POST_BY_ID(id));
    return response.data;
  },
  
  search: async (query, page = 0, size = 10) => {
    const response = await api.get(API_ENDPOINTS.POSTS_SEARCH, {
      params: { q: query, page, size }
    });
    return response.data;
  },
  
  getByCategory: async (categorySlug, page = 0, size = 10) => {
    const response = await api.get(API_ENDPOINTS.POSTS_BY_CATEGORY(categorySlug), {
      params: { page, size }
    });
    return response.data;
  },
  
  getByTag: async (tagSlug, page = 0, size = 10) => {
    const response = await api.get(API_ENDPOINTS.POSTS_BY_TAG(tagSlug), {
      params: { page, size }
    });
    return response.data;
  },
  
  getMyPosts: async (page = 0, size = 10) => {
    const response = await api.get(API_ENDPOINTS.MY_POSTS, {
      params: { page, size }
    });
    return response.data;
  },
  
  create: async (postData) => {
    const response = await api.post(API_ENDPOINTS.POSTS, postData);
    return response.data;
  },
  
  update: async (id, postData) => {
    const response = await api.put(API_ENDPOINTS.POST_BY_ID(id), postData);
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(API_ENDPOINTS.POST_BY_ID(id));
  },
};

// Categories API
export const categoriesAPI = {
  getAll: async () => {
    const response = await api.get(API_ENDPOINTS.CATEGORIES_PUBLIC);
    return response.data;
  },
  
  getBySlug: async (slug) => {
    const response = await api.get(API_ENDPOINTS.CATEGORY_BY_SLUG(slug));
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(API_ENDPOINTS.CATEGORY_BY_ID(id));
    return response.data;
  },
  
  create: async (categoryData) => {
    const response = await api.post(API_ENDPOINTS.CATEGORIES, categoryData);
    return response.data;
  },
  
  update: async (id, categoryData) => {
    const response = await api.put(API_ENDPOINTS.CATEGORY_BY_ID(id), categoryData);
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(API_ENDPOINTS.CATEGORY_BY_ID(id));
  },
};

// Tags API
export const tagsAPI = {
  getAll: async () => {
    const response = await api.get(API_ENDPOINTS.TAGS_PUBLIC);
    return response.data;
  },
  
  getBySlug: async (slug) => {
    const response = await api.get(API_ENDPOINTS.TAG_BY_SLUG(slug));
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(API_ENDPOINTS.TAG_BY_ID(id));
    return response.data;
  },
  
  create: async (tagData) => {
    const response = await api.post(API_ENDPOINTS.TAGS, tagData);
    return response.data;
  },
  
  update: async (id, tagData) => {
    const response = await api.put(API_ENDPOINTS.TAG_BY_ID(id), tagData);
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(API_ENDPOINTS.TAG_BY_ID(id));
  },
};

// Comments API
export const commentsAPI = {
  getByPost: async (postId) => {
    const response = await api.get(API_ENDPOINTS.COMMENTS_BY_POST(postId));
    return response.data;
  },
  
  create: async (postId, commentData) => {
    const response = await api.post(`${API_BASE_URL}/api/comments/post/${postId}`, commentData);
    return response.data;
  },
  
  updateStatus: async (id, status) => {
    const response = await api.put(`${API_ENDPOINTS.COMMENT_BY_ID(id)}/status`, null, {
      params: { status }
    });
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(API_ENDPOINTS.COMMENT_BY_ID(id));
  },
};

export default api;
