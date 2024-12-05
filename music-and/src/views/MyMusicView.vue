<template>
  <div class="flex h-screen">
    <!-- Navigation Sidebar -->
    <div class="ml-48 h-screen w-1/5 flex flex-col gap-y-5 overflow-y-auto border-l border-r border-gray-200 bg-white px-4">
      <nav class="flex flex-1 flex-col">
        <ul role="list" class="-mx-2 space-y-1">
          <li v-for="item in navigation" :key="item.name">
            <RouterLink v-if="!item.children" :to="item.href" :class="[isActive(item.href) ? 'bg-gray-50' : 'hover:bg-gray-50', 'group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700']">
              <component :is="item.icon" class="size-6 shrink-0 text-gray-400" aria-hidden="true" />
              {{ item.name }}
            </RouterLink>
            <Disclosure as="div" v-else v-slot="{ open }">
              <DisclosureButton :class="[isActive(item.href) || isChildActive(item.children) ? 'bg-gray-50' : 'hover:bg-gray-50', 'flex w-full items-center gap-x-3 rounded-md p-2 text-left text-sm/6 font-semibold text-gray-700']">
                <component :is="item.icon" class="size-6 shrink-0 text-gray-400" aria-hidden="true" />
                {{ item.name }}
                <ChevronRightIcon :class="[open ? 'rotate-90 text-gray-500' : 'text-gray-400', 'size-5 shrink-0']" aria-hidden="true" />
                <button v-if="item.name === '创建的歌单'" @click.stop="showCreatePlaylistModal = true" class="ml-2 bg-gray-100 text-gray-700 px-2 py-1 rounded">新建</button>
              </DisclosureButton>
              <DisclosurePanel as="ul" class="mt-1 px-2">
                <template v-if="item.children.length > 0">
                  <li v-for="subItem in item.children" :key="subItem.name">
                    <RouterLink :to="subItem.href" :class="[isActive(subItem.href) ? 'bg-gray-50' : 'hover:bg-gray-50', 'block rounded-md py-2 pl-9 pr-2 text-sm/6 text-gray-700']">
                      <img :src="subItem.imageUrl" :alt="subItem.name" class="inline-block h-10 w-10 rounded mr-2" />
                      {{ subItem.name }}
                    </RouterLink>
                  </li>
                </template>
                <template v-else>
                  <li class="text-gray-400 text-sm/6 p-2 text-center">暂无数据</li>
                </template>
              </DisclosurePanel>
            </Disclosure>
          </li>
        </ul>
      </nav>
    </div>
    <!-- Main Content Area -->
    <div class="w-4/5">
      <router-view></router-view>
    </div>
    <!-- Create Playlist Modal -->
    <div v-if="showCreatePlaylistModal" class="z-50 fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div class="bg-white p-8 rounded-lg w-full max-w-lg">
        <h2 class="text-lg font-semibold mb-4">创建新歌单</h2>
        <form @submit.prevent="createPlaylist">
          <div class="mb-4">
            <label for="playlistName" class="block text-gray-700 text-sm font-bold mb-2">歌单名</label>
            <input v-model="newPlaylist.name" type="text" id="playlistName" placeholder="歌单名称" class="border border-gray-300 p-2 rounded w-full" required />
          </div>
          <div class="mb-4">
            <label for="playlistCover" class="block text-gray-700 text-sm font-bold mb-2">歌单封面</label>
            <input type="file" id="playlistCover" @change="handleFileUpload" accept="image/*" class="hidden" ref="fileInput" />
            <button @click.prevent="$refs.fileInput.click()" class="flex items-center bg-gray-100 text-gray-700 px-4 py-2 rounded">
              <FolderPlusIcon class="size-5 mr-2" aria-hidden="true" />
              选择文件
            </button>
          </div>
          <div class="mb-4">
            <label for="playlistType" class="block text-gray-700 text-sm font-bold mb-2">歌单类型</label>
            <select v-model="newPlaylist.type" id="playlistType" class="border border-gray-300 p-2 rounded w-full" required>
              <option v-for="type in playlistTypes" :key="type.value" :value="type.value">{{ type.label }}</option>
            </select>
          </div>
          <div class="flex items-center justify-between">
            <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">创建</button>
            <button @click="showCreatePlaylistModal = false" class="ml-2 bg-gray-300 text-gray-700 px-4 py-2 rounded">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Disclosure, DisclosureButton, DisclosurePanel } from '@headlessui/vue'
import { ChevronRightIcon, FolderPlusIcon, HeartIcon, HomeIcon, UsersIcon } from '@heroicons/vue/24/outline'
import { ref, onMounted, computed } from 'vue'
import { RouterLink, useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const showCreatePlaylistModal = ref(false)
const newPlaylist = ref({
  name: '',
  imageUrl: null,
  type: 'public'
})
const router = useRouter()
const route = useRoute()

const playlistTypes = [
  { value: 'public', label: '公开' },
  { value: 'private', label: '私密' }
]

const navigation = ref([
  { name: '我的歌手', href: '/home/myMusic/songlistdetails/1', icon: HomeIcon, current: true },
  {
    name: '创建的歌单',
    icon: FolderPlusIcon,
    current: false,
    children: []
  },
  {
    name: '收藏的歌单',
    icon: HeartIcon,
    current: false,
    children: []
  }
])

const isActive = (path) => {
  return route.path === path
}

const isChildActive = (children) => {
  // 确保 children 存在且是一个数组
  return Array.isArray(children) && children.some(child => isActive(child.href))
}

const handleFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      newPlaylist.value.imageUrl = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const createPlaylist = () => {
  if (newPlaylist.value.name && newPlaylist.value.imageUrl) {
    // 这里可以添加创建歌单的逻辑
    console.log('Creating playlist:', newPlaylist.value)
    showCreatePlaylistModal.value = false
    newPlaylist.value = {
      name: '',
      imageUrl: null,
      type: 'public'
    }
    fetchCreatedPlaylists()
  }
}

const fetchCreatedPlaylists = async () => {
  try {
    const response = await axios.get(`/api/playList/getUserPlaylists`)
    const playlists = response.data.data
    const createdNavItem = navigation.value.find(item => item.name === '创建的歌单')
    if (createdNavItem) {
      createdNavItem.children = playlists.map(playlist => ({
        name: playlist.name,
        href: `/home/myMusic/songlistdetails/${playlist.playlistId}`,
        imageUrl: playlist.imageUrl
      }))
    }
    // 默认导航到“创建的歌单”的第一项
    if (createdNavItem.children.length > 0) {
      router.push(createdNavItem.children[0].href)
    }
  } catch (error) {
    console.error('Error fetching created playlists:', error)
  }
}

const fetchFavoritePlaylists = async () => {
  try {
    const response = await axios.get(`/api/playList/getUserFavorites`)
    const playlists = response.data.data
    const favoriteNavItem = navigation.value.find(item => item.name === '收藏的歌单')
    if (favoriteNavItem) {
      favoriteNavItem.children = playlists.map(playlist => ({
        name: playlist.name,
        href: `/home/myMusic/songlistdetails/${playlist.id}`,
        imageUrl: playlist.imageUrl
      }))
    }
  } catch (error) {
    console.error('Error fetching favorite playlists:', error)
  }
}

onMounted(() => {
  fetchCreatedPlaylists()
  fetchFavoritePlaylists()
})
</script>