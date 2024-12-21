<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white p-8 rounded-lg shadow-lg w-1/2">
      <h2 class="mb-4 text-xl font-bold">添加到歌单</h2>
      <input 
        v-model="searchQuery" 
        type="text" 
        placeholder="搜索歌单..." 
        class="border border-gray-300 p-2 w-full mb-4 rounded"
      />
      <ul>
        <li 
          v-for="playlist in filteredPlaylists" 
          :key="playlist.id" 
          class="cursor-pointer p-2 hover:bg-gray-100 rounded"
          @click="selectPlaylist(playlist.playlistId)"
        >
          {{ playlist.name }}
        </li>
      </ul>
      <button 
        class="mt-4 bg-red-500 text-white p-2 rounded hover:bg-red-600"
        @click="$emit('close-modal')"
      >
        取消
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps({
  song: Object
});

const emit = defineEmits(['close-modal', 'add-to-playlist']);

const searchQuery = ref('');
const playlists = ref([]);

const fetchPlaylists = async () => {
  try {
    const response = await axios.get('/api/playList/getUserPlaylists');
    playlists.value = response.data.data;
  } catch (error) {
    console.error("Error fetching user playlists:", error);
  }
};

const filteredPlaylists = computed(() => {
  return playlists.value.filter(playlist =>
    playlist.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const selectPlaylist = (playlistId) => {
  emit('add-to-playlist', playlistId);
};

onMounted(() => {
  fetchPlaylists();
});
</script>

<style scoped>
/* 样式可以根据需要进一步调整 */
</style>