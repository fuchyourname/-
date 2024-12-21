<!-- SongsList.vue -->
<template>
  <div class="mt-4 ml-32">
    <table class="w-full">
      <thead>
        <tr>
          <th class="p-2">#</th>
          <th class="p-2">歌曲</th>
          <th class="p-2">歌手</th>
          <th class="p-2">专辑</th>
          <th class="p-2">时长</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(song, index) in songs" :key="song.songId">
          <td class="p-2 text-center">{{ index + 1 }}</td>
          <td class="p-2 flex items-center">
            <img :src="song.coverPath" class="h-10 w-10 mr-2 rounded-full" alt="Song Cover" />
            <span>{{ song.title }}</span>
          </td>
          <td class="p-2">{{ song.artist }}</td>
          <td class="p-2">{{ song.album }}</td>
          <td class="p-2 text-center">{{ song.duration }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute() // 使用 useRoute 获取当前路由对象
const artistId = route.params.id // 获取路由参数中的歌手 ID

const songs = ref([]) // 存储歌曲列表的响应式变量

// 定义获取歌曲列表的方法
const fetchSongs = async () => {
  try {
    const response = await axios.get(`/api/player/getPlayerSong/${artistId}`) // 使用 artistId 发送请求
    songs.value = response.data.data // 将返回的数据赋值给 songs
  } catch (error) {
    console.error('Error fetching songs:', error)
  }
}

// 在组件挂载后调用 fetchSongs 方法
onMounted(() => {
  fetchSongs()
})
</script>

<style scoped>
/* Add any additional styles here */
table {
  width: 100%;
}

th, td {
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f1f1f1;
}

img {
  object-fit: cover;
}
</style>