<template>
  <div class="ml-32 mt-4">
    <div>
      <span class="font-sans">我创建的歌单</span>
      <div v-if="createdPlaylists.length > 0" class="mt-4 grid grid-cols-1 gap-x-4 gap-y-8 sm:grid-cols-2 sm:gap-y-8 lg:grid-cols-3 lg:gap-x-1 xl:grid-cols-6 w-full">
        <div v-for="playlist in createdPlaylists" :key="playlist.playlistId" class="group relative" @click="handlePlaylistClick(playlist.playlistId)">
          <div class="h-48 w-48 overflow-hidden rounded-md bg-gray-200 group-hover:opacity-75">
            <img :src="playlist.imageUrl" class="h-48 w-48 object-cover object-center" />
          </div>
          <h3 class="mt-4 text-sm text-gray-500">
            <span class="absolute inset-0" />
            {{ playlist.name }}
          </h3>
        </div>
      </div>
      <div v-else class="mt-4 text-center">
        <span class="font-sans text-gray-500">您还没有创建任何歌单</span>
      </div>
    </div>

    <div class="mt-7 mb-24">
      <span class="font-sans">我收藏的歌单</span>
      <div v-if="collectedPlaylists.length > 0" class="mt-4 grid grid-cols-1 gap-x-4 gap-y-8 sm:grid-cols-2 sm:gap-y-8 lg:grid-cols-3 lg:gap-x-1 xl:grid-cols-6 w-full">
        <div v-for="playlist in collectedPlaylists" :key="playlist.playlistId" class="group relative" @click="handlePlaylistClick(playlist.playlistId)">
          <div class="h-48 w-48 overflow-hidden rounded-md bg-gray-200 group-hover:opacity-75">
            <img :src="playlist.imageUrl" class="h-48 w-48 object-cover object-center" />
          </div>
          <h3 class="mt-4 text-sm text-gray-500">
            <span class="absolute inset-0" />
            {{ playlist.name }}
          </h3>
        </div>
      </div>
      <div v-else class="mt-4 text-center">
        <span class="font-sans text-gray-500">您还没有收藏任何歌单</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

const createdPlaylists = ref([])
const collectedPlaylists = ref([])
const router = useRouter()

onMounted(() => {
  // 获取用户创建的歌单
  axios.get('/api/playList/getUserPlaylists')
    .then(response => {
      if (Array.isArray(response.data.data)) {
        createdPlaylists.value = response.data.data
      } else {
        createdPlaylists.value = []
        console.warn('Expected an array for user created playlists, but got:', response.data.data)
      }
      console.log('User Created Playlists:', createdPlaylists.value)
    })
    .catch(error => {
      console.error('Error fetching user created playlists:', error)
      createdPlaylists.value = []
    })

  // 获取用户收藏的歌单
  axios.get('/api/playList/getUserFavorites')
    .then(response => {
      if (Array.isArray(response.data.data)) {
        collectedPlaylists.value = response.data.data
      } else {
        collectedPlaylists.value = []
        console.warn('Expected an array for user collected playlists, but got:', response.data.data)
      }
      console.log('User Collected Playlists:', collectedPlaylists.value)
    })
    .catch(error => {
      console.error('Error fetching user collected playlists:', error)
      collectedPlaylists.value = []
    })
})

const handlePlaylistClick = (playlistId) => {
  router.push(`/home/songlistdetails/${playlistId}`)
}
</script>

<style scoped>
/* 你可以在这里添加样式 */
</style>