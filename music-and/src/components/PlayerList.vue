<!-- PlayerList.vue -->
<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">歌手列表</h1>
    <div class="border-b border-gray-300 mb-4"></div>
    <ul v-if="artists && artists.length > 0" class="space-y-4">
      <li 
        v-for="artist in artists" 
        :key="artist.artistId" 
        class="bg-gray-100 p-4 rounded-lg shadow-md flex items-center cursor-pointer hover:bg-gray-200 transition duration-300"
        @click="goToArtistDetail(artist.artistId)"
      >
        <img :src="artist.picUrl" :alt="artist.name" class="w-16 h-16 mr-4 rounded-full">
        <span class="text-lg font-medium">{{ artist.name }}</span>
      </li>
    </ul>
    <p v-else class="text-center text-gray-500">暂无歌手数据</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

// 定义一个响应式变量来存储歌手列表
const artists = ref([])

// 在组件挂载时发送 GET 请求
onMounted(async () => {
  axios.get('/api/player/getFollowSinger')
    .then(response => {
      artists.value = response.data.data
    })
    .catch(error => {
      console.error('Error:', error)
    })
})

const goToArtistDetail = (id) => {
  router.push(`/home/playerDetail/${id}/songslist/${id}`)
}
</script>

<style scoped>
/* 添加样式 */
</style>