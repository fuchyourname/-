<template>
    <div class="flex flex-row ml-32 mt-8">
      <img :src="artist.avatar" class="h-48 w-48 flex-none rounded-full bg-white/10 object-cover"/>
      <div class="flex flex-col ml-4">
        <span class="font-mono text-2xl">{{ artist.username }}</span>
        <div class="flex flex-row mt-2">
          <RouterLink to="/home/follow" class="font-mono">关注 {{ artist.followCount }}</RouterLink>
          <RouterLink to="/home/fans" class="ml-4 font-mono">粉丝 {{ artist.fansCount }}</RouterLink>
        </div>
        <span class="mt-2 text-xs text-gray-500">地区: {{ artist.nationality }}</span>
        <button @click="toggleFollow" class="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700">
          {{ isFollowing ? '取消关注' : '关注' }}
        </button>
      </div>
    </div>
    <div class="mt-4 ml-32">
      <div class="hidden sm:block">
        <div class="border-b border-gray-200">
          <nav class="-mb-px flex space-x-8" aria-label="Tabs">
            <RouterLink v-for="tab in tabs" :to="tab.href" @click="selectTab(tab)" :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-200 hover:text-gray-700', 'flex whitespace-nowrap border-b-2 px-1 py-4 text-sm font-medium']">
              {{ tab.name }}
              <span :class="[tab.current ? ' text-indigo-600' : ' text-gray-900', 'ml-3 hidden rounded-full px-2.5 py-0.5 text-xs font-medium md:inline-block']"></span>
            </RouterLink>
          </nav>
        </div>
      </div>
    </div>
    <div class="mt-4 ml-32">
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-2xl font-bold mb-4">个人信息</h2>
        <p class="text-gray-700">{{ artistDetail.bio }}</p>
      </div>
      <div class="mt-8">
        <h2 class="text-2xl font-bold mb-4">专辑列表</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          <div v-for="album in artistDetail.albums" :key="album.id" class="bg-white p-6 rounded-lg shadow-md">
            <img :src="album.coverImage" class="h-48 w-full object-cover rounded-t-lg"/>
            <div class="p-4">
              <h3 class="text-xl font-bold">{{ album.title }}</h3>
              <p class="text-gray-700">{{ album.releaseDate }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <RouterView></RouterView>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue'
  import { RouterLink, RouterView } from 'vue-router'
  import { useUserStore } from '../stores/user'
  import axios from 'axios'
  
  const userStore = useUserStore()
  
  const tabs = [
    { name: '歌单', href: '/home/my/playlist', current: true },
    // { name: '动态', href: '/home/my/dynamicsList', current: false },
  ]
  
  const artist = userStore.artist // 假设 userStore 中有 artist 对象
  const artistDetail = ref({ bio: '', albums: [] })
  const isFollowing = ref(false)
  
  const selectTab = (tab) => {
    tabs.forEach(item => item.current = false)
    tab.current = true
  }
  
  const fetchArtistDetails = async () => {
    try {
      const response = await axios.get(`/api/artists/${artist.id}`)
      artistDetail.value = response.data
      isFollowing.value = response.data.isFollowing
    } catch (error) {
      console.error('Error fetching artist details:', error)
    }
  }
  
  const toggleFollow = async () => {
    try {
      const action = isFollowing.value ? 'unfollow' : 'follow'
      await axios.post(`/api/artists/${artist.id}/${action}`)
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
  </style>