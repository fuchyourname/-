// src/stores/useMusicStore.js
import axios from 'axios';
import { defineStore } from 'pinia';

export const useMusicStore = defineStore('music', {
  state: () => ({
    currentMusicId: null,
    currentPlaylistId:null, // 新增字段
    currentPlaylist: null, // 新增字段
  }),
  actions: {
    setCurrentMusicId(id) {
      this.currentMusicId = id;
    },
    getCurrentMusicId() {
      return this.currentMusicId;
    },
    setCurrentPlaylistId(id) {
      this.currentPlaylistId = id; // 新增方法
    },
    getCurrentPlaylistId() {
      return this.currentPlaylistId; // 新增方法
    },
    async fetchPlaylistSongs() {
      try {
        const response = await axios.get('http://127.0.0.1:8080/playList/getPlaylistDetail', {
          params: {
            playlistId: this.currentPlaylistId,
          },
        });
        this.currentPlaylist = response.data.data;
      } catch (error) {
        console.error('Error fetching playlist songs:', error);
      }
    },
    async fetchSong(id) {
      try {
        const response = await axios.get('http://127.0.0.1:8080/music/getSongDetail', {
          params: {
            songId: id,
          },
        });
        return response.data.data;
      } catch (error) {
        console.error('Error fetching song URL:', error);
       return null;
      }
    }
  },
});