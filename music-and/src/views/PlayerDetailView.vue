<!-- PlayerDetailView.vue -->
<template>
  <div class="flex flex-row ml-32 mt-8">
    <img :src="artistDetail.picUrl" class="h-48 w-48 flex-none rounded-full bg-white/10 object-cover"/>
    <div class="flex flex-col ml-4">
      <span class="font-bold text-2xl">{{ artistDetail.name }}</span>
      <div class="flex flex-row mt-2">
        <span class="font-mono">关注 {{ artistDetail.followCount }}</span>
        <span class="ml-4 font-mono">粉丝 {{ artistDetail.fansCount }}</span>
      </div>
      <span class="mt-2 text-xs text-gray-500">地区: {{ artistDetail.nationality }}</span>
      <div class="mt-2 text-xs text-gray-500">
        <span class="inline-block max-w-full truncate hover:whitespace-normal hover:overflow-visible hover:break-words" :title="artistDetail.bio">
          简介: {{ artistDetail.bio }}
        </span>
      </div>
      <button @click="toggleFollow" class="w-24 mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700 shadow-md hover:shadow-lg transition duration-300 ease-in-out">
        {{ isFollowing ? '取消关注' : '关注' }}
      </button>
    </div>
  </div>
  <div class="mt-4 ml-32">
    <div class="hidden sm:block">
      <div class="border-b border-gray-200">
        <nav class="-mb-px flex space-x-8" aria-label="Tabs">
          <RouterLink v-for="tab in tabs" :key="tab.name" :to="tab.href" @click="selectTab(tab)" :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-200 hover:text-gray-700', 'flex whitespace-nowrap border-b-2 px-1 py-4 text-sm font-medium']">
            {{ tab.name }}
            <span :class="[tab.current ? ' text-indigo-600' : ' text-gray-900', 'ml-3 hidden rounded-full px-2.5 py-0.5 text-xs font-medium md:inline-block']"></span>
          </RouterLink>
        </nav>
      </div>
    </div>
  </div>
  <RouterView></RouterView>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink, RouterView, useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute() // 使用 useRoute 获取当前路由对象

const tabs = [
  { name: '歌曲', href: `/home/playerDetail/${route.params.id}/songslist/${route.params.id}`, current: true },
  { name: '专辑', href: `/home/playerDetail/${route.params.id}/albumslist/${route.params.id}`, current: false },
]

const artistId = route.params.id // 获取路由参数中的歌手 ID
const artistDetail = ref({ bio: '', albums: [] })
const isFollowing = ref(false)

const selectTab = (tab) => {
  tabs.forEach(item => item.current = false)
  tab.current = true
}

const fetchArtistDetails = async () => {
  try {
    const response = await axios.get(`/api/player/getPlayerById/${artistId}`)
    artistDetail.value = response.data.data
    isFollowing.value = response.data.data.isFollow
  } catch (error) {
    console.error('Error fetching artist details:', error)
  }
}

const toggleFollow = async () => {
  try {
    const action = isFollowing.value ? 'removeFollowSinger' : 'addFollowSinger'
    await axios.post(`/api/user/${action}`, {}, { params: { followUserId: artistId } }) // 使用 artistId 发送请求
    isFollowing.value = !isFollowing.value
  } catch (error) {
    console.error('Error toggling follow:', error)
  }
}

onMounted(() => {
  fetchArtistDetails()
})
</script>

<style scoped>
/* Add any additional styles here */
.truncate {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
}
</style>