<!-- AlbumsList.vue -->
<template>
  <div class="mt-4 ml-32">
    <table class="w-full">
      <thead>
        <tr>
          <th class="p-2">#</th>
          <th class="p-2">专辑</th>
          <th class="p-2">歌手</th>
          <th class="p-2">播放次数</th>
          <th class="p-2">包含的歌曲数</th>
          <th class="p-2">发布时间</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(album, index) in albums" :key="album.albumId">
          <td class="p-2 text-center">{{ index + 1 }}</td>
          <td class="p-2 flex items-center">
            <img :src="album.coverImage" class="h-10 w-10 mr-2 rounded-full" alt="Album Cover" />
            <span>{{ album.title }}</span>
          </td>
          <td class="p-2">{{ album.artist }}</td>
          <td class="p-2 text-center">{{ album.playCount }}</td>
          <td class="p-2 text-center">{{ album.songCount }}</td>
          <td class="p-2 text-center">{{ formattedDate(album.createTime) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { format } from 'date-fns' // 引入 date-fns 的 format 函数

const route = useRoute() // 使用 useRoute 获取当前路由对象
const artistId = route.params.id // 获取路由参数中的歌手 ID

const albums = ref([]) // 存储专辑列表的响应式变量

// 定义获取专辑列表的方法
const fetchAlbums = async () => {
  try {
    const response = await axios.get(`/api/player/getPlayerAlbum/${artistId}`) // 使用 artistId 发送请求
    albums.value = response.data.data // 将返回的数据赋值给 albums
  } catch (error) {
    console.error('Error fetching albums:', error)
  }
}

// 在组件挂载后调用 fetchAlbums 方法
onMounted(() => {
  fetchAlbums()
})

// 定义格式化日期的方法
const formattedDate = (date) => {
  return format(new Date(date), 'yyyy-MM-dd') // 格式化为 '年-月-日'
}
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