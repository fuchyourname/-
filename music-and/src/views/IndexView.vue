<template>
  <div class="bg-white">
    <main class="mx-auto max-w-2xl px-4 py-12 sm:px-6 lg:max-w-7xl lg:px-8" aria-labelledby="order-history-heading">
      <div class="max-w-xl">
        <h1 id="order-history-heading" class="text-xl font-bold tracking-tight text-gray-900">歌单推荐</h1>
      </div>

      <div
        class="mt-3 grid grid-cols-5 gap-x-4 gap-y-8 w-full">
        <div v-for="playlist in playlists" :key="playlist.playlistId" class="group relative playlist-item"
          @click="handlePlaylistClick(playlist.playlistId)">
          <div class="h-56 w-56 overflow-hidden rounded-md bg-gray-200 group-hover:opacity-75 relative">
            <img :src="playlist.imageUrl" class="h-56 w-56 object-cover object-center" />
            <div class="absolute bottom-2 right-2 bg-black bg-opacity-50 text-white text-xs px-2 py-1 rounded">
              {{ playlist.playCount }}
            </div>
          </div>
          <h3 class="mt-4 text-sm text-gray-500">
            <span class="absolute inset-0" />
            {{ playlist.name }}
          </h3>
        </div>
      </div>
      <div class="max-w-xl mt-4 flex justify-between items-center">
        <div class="flex-1">
          <h1 id="order-history-heading" class="text-xl font-bold tracking-tight text-gray-900">榜单推荐</h1>
        </div>
      </div>
      <ListView/>
      <div class="max-w-xl mt-4">
        <h1 id="order-history-heading" class="text-xl font-bold tracking-tight text-gray-900">新歌首发</h1>
      </div>
      <NewMusic></NewMusic>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios';
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ListView from '../components/home/ListView.vue'
import NewMusic from '../components/home/NewMusic.vue';

const playlists = ref([])
const router = useRouter()

onMounted(() => {
  axios.get('/api/playList/getRecommendPlayList')
    .then(response => {
      playlists.value = response.data.data
    })
    .catch(error => {
      console.error('Error fetching playlists:', error)
    })
})

const handlePlaylistClick = (playlistId) => {
  router.push(`/home/songlistdetails/${playlistId}`)
}

const open = ref(false)
</script>

<style scoped>
.playlist-item {
  transition: transform 0.3s ease;
}

.playlist-item:hover {
  transform: translateY(-10px);
}
</style>