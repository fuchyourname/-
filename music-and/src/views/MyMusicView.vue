<template>
  <div class="flex mb-32">
    <!-- Navigation Sidebar -->
    <div class="ml-32 h-screen w-1/5 flex flex-col gap-y-5 overflow-y-auto border-l border-r border-gray-200 bg-white px-4">
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
                    <RouterLink :to="subItem.href" :class="[isActive(subItem.href) ? 'bg-gray-50' : 'hover:bg-gray-50', 'block rounded-md py-2 pl-9 pr-2 text-sm/6 text-gray-700 flex items-center']">
                      <img :src="subItem.imageUrl" :alt="subItem.name" class="inline-block h-10 w-10 rounded mr-2" />
                      <span class="truncate" :title="subItem.name">{{ subItem.name }}</span>
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
  </div>
  <PlayListDialog 
    v-model:show="showCreatePlaylistModal" 
    :playlist="newPlaylist" 
    :playlistTypes="playlistTypes"
    @create="createPlaylist" 
    @close="showCreatePlaylistModal = false" 
  />
</template>

<script setup>
import { Disclosure, DisclosureButton, DisclosurePanel } from '@headlessui/vue'
import { ChevronRightIcon, FolderPlusIcon, HeartIcon, HomeIcon, UsersIcon } from '@heroicons/vue/24/outline'
import { ref, onMounted } from 'vue'
import { RouterLink, useRouter, useRoute } from 'vue-router'
import axios from 'axios'
import PlayListDialog from '../components/PlayListDialog.vue'

const showCreatePlaylistModal = ref(false)
const newPlaylist = ref({
  name: '',
  imageUrl: null,
  imageFile: null,
  type: 'public'
})
const router = useRouter()
const route = useRoute()

const playlistTypes = [
  { value: 'public', label: '公开' },
  { value: 'private', label: '私密' }
]

const navigation = ref([
  { name: '我的歌手', href: '/home/myMusic/playerlist', icon: HomeIcon, current: true },
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

const createPlaylist = async () => {
  if (newPlaylist.value.name && newPlaylist.value.imageFile) {
    try {
      const formDataToSend = new FormData()
      formDataToSend.append('name', newPlaylist.value.name)
      formDataToSend.append('type', newPlaylist.value.type)
      formDataToSend.append('image', newPlaylist.value.imageFile)

      const response = await axios.post('/api/playList/create', formDataToSend, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })

      showCreatePlaylistModal.value = false
      newPlaylist.value = {
        name: '',
        imageUrl: null,
        imageFile: null,
        type: 'public'
      }

      fetchCreatedPlaylists()
    } catch (error) {
      console.error('创建歌单失败:', error)
    }
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
        href: `/home/myMusic/songlistdetails/${playlist.playlistId}`,
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

/* 新增样式 */
.truncate {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 150px; /* 根据需要调整最大宽度 */
}

/* 确保封面图片和歌单标题在同一行 */
.flex-items-center {
  display: flex;
  align-items: center;
}
</style>