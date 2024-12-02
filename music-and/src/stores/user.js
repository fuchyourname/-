// src/store/user.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
  }),
  persist: true,
  actions: {
    async login(user) {
      try {
        const response = await axios.post('http://localhost:8080/index/login', user);
        if (response.data.success) {
          this.setUser(response.data.data);
          console.log('Login successful!');
          return true;
        } else {
          throw new Error('Login failed, please check your credentials.');
        }
      } catch (error) {
        console.error("There was an error!", error);
        throw new Error('An error occurred while trying to log in.');
      }
    },
    setUser(user) {
      this.user = user;
    }
  }
});