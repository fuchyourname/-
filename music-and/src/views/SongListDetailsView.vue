<template>
  <div class="bg-white">
    <div class="ml-32 max-w-2xl px-4 py-4 sm:px-6 sm:py-4">
      <!-- 图片和文字在同一行 -->
      <div class="flex items-start mb-8">
        <img class="inline-block h-40 w-40 rounded-md mr-4" :src="playlist.imageUrl" alt="" />
        <div>
          <h3 class="text-lg font-medium text-gray-900">{{ playlist.name }}</h3>
          <p class="mt-1 text-sm text-gray-600">{{ playlist.description }}</p>
        </div>
      </div>

      <!-- 按钮和路由视图位于图片下方 -->
      <div class="w-full">
        <span class="isolate inline-flex rounded-md shadow-sm w-full mb-4">
          <div class="flex flex-1 space-x-2">
            <button type="button" @click="handleSong"
              :class="['relative inline-flex items-center rounded-full bg-white px-4 py-2 text-sm font-semibold', selectedButton === 'song' ? 'text-green-500' : 'text-gray-900']">
              歌曲
            </button>
            <button type="button" @click="handleComment"
              :class="['relative inline-flex items-center rounded-full bg-white px-4 py-2 text-sm font-semibold', selectedButton === 'comment' ? 'text-green-500' : 'text-gray-900']">
              评论
            </button>
          </div>
        </span>
        <RouterView />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios';

export default {
  setup() {
    const route = useRoute()
    const router = useRouter()
    const playlist = ref({})
    const selectedButton = ref('song')

    onMounted(() => {
      console.log(route.params.id)
      const playlistId = route.params.id
      axios.get('http://127.0.0.1:8080/playList/getPlaylistDetail', {
        params: {
          playlistId: playlistId
        }
      })
        .then(response => {
          playlist.value = response.data.data
        })
        .catch(error => {
          console.error('Error fetching playlist details:', error)
        })
    })

    const handleSong = () => {
      selectedButton.value = 'song'
      router.push({ name: 'songListDetailsSongs', params: { id: route.params.id } })
    }

    const handleComment = () => {
      selectedButton.value = 'comment'
      router.push({ name: 'songListDetailsComments', params: { id: route.params.id } })
    }

    return {
      playlist,
      selectedButton,
      handleSong,
      handleComment
    }
  }
}
</script>