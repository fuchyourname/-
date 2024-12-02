<template>
  <div class="flex h-screen">
    <!-- Navigation Sidebar -->
    <div class="ml-48 h-screen w-1/5 flex flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white px-4"> <!-- 缩小左侧导航栏的内边距 -->
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
                <li v-for="subItem in item.children" :key="subItem.name">
                  <RouterLink :to="subItem.href" :class="[isActive(subItem.href) ? 'bg-gray-50' : 'hover:bg-gray-50', 'block rounded-md py-2 pl-9 pr-2 text-sm/6 text-gray-700']">
                    {{ subItem.name }}
                  </RouterLink>
                </li>
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
    <div v-if="showCreatePlaylistModal" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div class="bg-white p-8 rounded-lg">
        <h2 class="text-lg font-semibold mb-4">创建新歌单</h2>
        <input v-model="newPlaylistName" type="text" placeholder="歌单名称" class="border border-gray-300 p-2 rounded w-full mb-4" />
        <button @click="createPlaylist" class="bg-blue-500 text-white px-4 py-2 rounded">创建</button>
        <button @click="showCreatePlaylistModal = false" class="ml-2 bg-gray-300 text-gray-700 px-4 py-2 rounded">取消</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Disclosure, DisclosureButton, DisclosurePanel } from '@headlessui/vue'
import { ChevronRightIcon, FolderPlusIcon, HeartIcon, HomeIcon, UsersIcon } from '@heroicons/vue/24/outline'
import { ref, onMounted, computed } from 'vue'
import { RouterLink, useRouter, useRoute } from 'vue-router'

const showCreatePlaylistModal = ref(false)
const newPlaylistName = ref('')
const router = useRouter()
const route = useRoute()

const navigation = [
  { name: '我的歌手', href:'/home/myMusic/songlistdetails/1', icon: HomeIcon, current: true },
  {
    name: '创建的歌单',
    icon: FolderPlusIcon,
    current: false,
    children: [
      { name: 'Engineering', href: '/home/myMusic/songlistdetails/1' },
      { name: 'Human Resources', href: '/home/myMusic/songlistdetails/1008' },
      { name: 'Customer Success', href: '/home/myMusic/songlistdetails/1010' },
    ],
  },
  {
    name: '收藏的歌单',
    icon: HeartIcon,
    current: false,
    children: [
      { name: 'GraphQL API', href: '/home/myMusic/songlistdetails/1' },
      { name: 'iOS App', href: '/home/myMusic/songlistdetails/1' },
      { name: 'Android App', href: '/home/myMusic/songlistdetails/1' },
      { name: 'New Customer Portal', href: '/home/myMusic/songlistdetails/1' },
    ],
  }
]

const isActive = (path) => {
  return route.path === path
}

const isChildActive = (children) => {
  // 确保 children 存在且是一个数组
  return Array.isArray(children) && children.some(child => isActive(child.href))
}

const createPlaylist = () => {
  if (newPlaylistName.value) {
    // 这里可以添加创建歌单的逻辑
    console.log('Creating playlist:', newPlaylistName.value)
    showCreatePlaylistModal.value = false
    newPlaylistName.value = ''
  }
}

onMounted(() => {
  // 默认导航到“创建的歌单”的第一项
  const firstPlaylistItem = navigation.find(item => item.name === '创建的歌单')?.children[0];
  if (firstPlaylistItem) {
    router.push(firstPlaylistItem.href);
  }
})
</script>