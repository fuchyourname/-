<template>
  <div class="bg-gray-100 min-h-screen">
    <div class="container mx-auto px-4 py-8">
        <div v-for="playlist in playlists" :key="playlist.playlistId" class="mb-8">
          <a class="block" @click="handlePlaylistClick(playlist)">
            <div class="relative rounded-lg overflow-hidden shadow-lg cursor-pointer">
              <img :src="playlist.imageUrl" alt="Playlist Image" class="w-full h-64 object-cover" />
              <div class="absolute inset-0 bg-black bg-opacity-50"></div>
              <div class="absolute inset-0 flex flex-col justify-center items-center text-white p-4">
                <h2 class="text-2xl font-bold mb-4">{{ playlist.name }}</h2>
                <ul class="space-y-2">
                  <li v-for="(song, index) in playlist.topSongs" :key="song.songId" class="text-lg">
                    {{ index + 1 }}. {{ song.title }} - {{ song.artist }}
                  </li>
                </ul>
              </div>
            </div>
        </a>
        </div>
      </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const playlists = ref([])
const loading = ref(true)
const error = ref(false)

const handlePlaylistClick = (playlist) => {
//   router.push({ name: 'PlaylistDetail', params: { playlistId: playlist.playlistId }})
}

const fetchPlaylists = async () => {
  try {
    const response = await axios.get('/api/index/getRankList')
    playlists.value = response.data.data.map(playlist => ({
      ...playlist,
      topSongs: playlist.songs.slice(0, 3) // 获取前三首歌曲
    }))
  } catch (err) {
    error.value = true
    console.error("Error fetching playlists:", err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchPlaylists()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style>