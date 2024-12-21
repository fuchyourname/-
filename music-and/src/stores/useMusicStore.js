// src/stores/useMusicStore.js
import axios from 'axios';
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
    currentPlaylist: null, // 新增字段
  }),
  actions: {
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
      } catch (error) {
        console.error('Error fetching song URL:', error);
        return null;
      }
    }
  },
});