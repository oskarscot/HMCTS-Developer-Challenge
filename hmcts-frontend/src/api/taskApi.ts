import axios from 'axios';
import { Task, TaskCreate, TaskUpdate, TaskStatus } from '../types/task';

const API_URL = 'http://localhost:8080/api';

const taskApi = {
  // Get all tasks
  getAllTasks: async (): Promise<Task[]> => {
    try {
      const response = await axios.get(`${API_URL}/tasks`);
      return response.data;
    } catch (error) {
      console.error('Error fetching tasks:', error);
      throw error;
    }
  },

  // Get a single task by ID
  getTaskById: async (id: number): Promise<Task> => {
    try {
      const response = await axios.get(`${API_URL}/tasks/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching task ${id}:`, error);
      throw error;
    }
  },

  // Create a new task
  createTask: async (task: TaskCreate): Promise<Task> => {
    try {
      const response = await axios.post(`${API_URL}/tasks`, task);
      return response.data;
    } catch (error) {
      console.error('Error creating task:', error);
      throw error;
    }
  },

  // Update a task
  updateTask: async (id: number, task: TaskUpdate): Promise<Task> => {
    try {
      const response = await axios.put(`${API_URL}/tasks/${id}`, task);
      return response.data;
    } catch (error) {
      console.error(`Error updating task ${id}:`, error);
      throw error;
    }
  },

  // Update just the task status
  updateTaskStatus: async (id: number, status: TaskStatus): Promise<Task> => {
    try {
      const response = await axios.patch(`${API_URL}/tasks/${id}/status?status=${status}`);
      return response.data;
    } catch (error) {
      console.error(`Error updating task ${id} status:`, error);
      throw error;
    }
  },

  // Delete a task
  deleteTask: async (id: number): Promise<void> => {
    try {
      await axios.delete(`${API_URL}/tasks/${id}`);
    } catch (error) {
      console.error(`Error deleting task ${id}:`, error);
      throw error;
    }
  }
};

export default taskApi;