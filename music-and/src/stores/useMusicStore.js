// src/stores/useMusicStore.js
import axios from 'axios';
import { set } from 'date-fns';
import { defineStore } from 'pinia';

export const useMusicStore = defineStore('music', {
  state: () => ({
    currentMusic: {
      "songId": 0,
      "title": "",
      "artist": "",
      "album": null,
      "genre": null,
      "duration": null,
      "coverPath": "",
      "filePath": "",
      "lyricPath": "",
      "like": null
    },
    isPaused: true,
    currentPlaylist: [], // 新增字段
    currentPlaylistId: null,
  }),
  actions: {
    setIsPaused(isPaused) {
      this.isPaused = isPaused;
    },
    getIsPaused() {
      return this.isPaused;
    },
    setCurrentPlaylistId(id) {
      this.currentPlaylistId = id;
    },
    getCurrentPlaylistId() {
      return this.currentPlaylistId;
    }, 
    setCurrentMusic(music) {
      this.currentMusic = music;
    },
    getCurrentMusic() {
      return this.currentMusic;
    },
    setCurrentPlaylist(playlist) {
      this.currentPlaylist = playlist; // 新增方法
    },
    // 上一首
    async playPrevious() {
      const currentIndex = this.currentPlaylist.findIndex(song => song.songId === this.currentMusic.songId);
      if (currentIndex > 0) {
        const previousSong = this.currentPlaylist[currentIndex - 1];
        this.setCurrentMusic(previousSong);
      }
    },
    // 下一首
    async playNext() {
      const currentIndex = this.currentPlaylist.findIndex(song => song.songId === this.currentMusic.songId);
      if (currentIndex < this.currentPlaylist.length - 1) {
        const nextSong = this.currentPlaylist[currentIndex + 1];
        this.setCurrentMusic(nextSong);
      }
    },
    async fetchPlaylistSongs() {
      try {
        const response = await axios.get('/api/playList/getPlaylistDetail', {
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
        const response = await axios.get('/api/music/getSongDetail', {
          params: {
            songId: id,
          },
        });
        return response.data.data;
        // 增加播放次数
      } catch (error) {
        console.error('Error fetching song URL:', error);
        return null;
      } finally {
        this.addPlayCount();
      }
    },
    // 给歌单增加播放次数
    async addPlayCount() {
      try {
        const response = await axios.get('/api/playList/addPlayCount', {
          params: {
            // 将Id转为整数.
            playlistId: parseInt(this.currentPlaylistId),

          songId: this.currentMusic.songId,
          },
        });
        return response.data.data;
      } catch (error) {
        console.error('Error fetching song URL:', error);
        return null;
      }
    },
  },
});