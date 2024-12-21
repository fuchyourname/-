// src/store/user.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
  }),
  // persist: true,
  actions: {
    async login(user) {
      try {
        const response = await axios.post('/api/index/login', user);
        if (response.data.success) {
          if (response.data.data.isAdmin) {
            localStorage.setItem('role', true); // 存储到本地存储
          }
          this.setUser(response.data.data);
          localStorage.setItem('user', JSON.stringify(response.data.data)); // 存储到本地存储
          return true;
        } else {
          throw new Error('Login failed, please check your credentials.');
        }
      } catch (error) {
        console.error("There was an error!", error);
        throw new Error('An error occurred while trying to log in.');
      }
    },
    async logout() {
      localStorage.removeItem('user'); // 清除本地存储
      localStorage.removeItem('role');
      return axios.get('/api/index/logout');
    },
    async initialize() {
      return new Promise((resolve, reject) => {
        try {
          const storedUser = localStorage.getItem('user');
          if (storedUser) {
            this.user = JSON.parse(storedUser); // 从本地存储恢复
          }
          resolve();
        } catch (error) {
          reject(error);
        }
      });
    },
    setUser(user) {
      this.user = user;
    }
  }
});