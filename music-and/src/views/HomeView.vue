<template>
  <header class="bg-white">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1">
        <a href="#" class="-m-1.5 p-1.5">
          <img class="h-8 w-auto" src="https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=600" alt="" />
        </a>
      </div>
      <PopoverGroup class="hidden lg:flex lg:gap-x-12">
        <Popover class="relative">
          <PopoverButton class="flex items-center gap-x-1 text-sm font-semibold leading-6 text-gray-900">
            音乐馆
            <ChevronDownIcon class="h-5 w-5 flex-none text-gray-400" aria-hidden="true" />
          </PopoverButton>

          <transition enter-active-class="transition ease-out duration-200" enter-from-class="opacity-0 translate-y-1"
            enter-to-class="opacity-100 translate-y-0" leave-active-class="transition ease-in duration-150"
            leave-from-class="opacity-100 translate-y-0" leave-to-class="opacity-0 translate-y-1">
            <PopoverPanel
              class="absolute -left-8 top-full z-10 mt-3 w-screen max-w-md overflow-hidden rounded-3xl bg-white shadow-lg ring-1 ring-gray-900/5">
              <div class="p-4">
                <div v-for="item in products" :key="item.name"
                  class="group relative flex items-center gap-x-6 rounded-lg p-4 text-sm leading-6 hover:bg-gray-50">
                  <div
                    class="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white">
                    <component :is="item.icon" class="h-6 w-6 text-gray-600 group-hover:text-indigo-600"
                      aria-hidden="true" />
                  </div>
                  <div class="flex-auto">
                    <RouterLink :to="item.href" class="block font-semibold text-gray-900">
                      {{ item.name }}
                      <span class="absolute inset-0" />
                    </RouterLink>
                    <p class="mt-1 text-gray-600">{{ item.description }}</p>
                  </div>
                </div>
              </div>
              <div class="grid grid-cols-2 divide-x divide-gray-900/5 bg-gray-50">
                <a v-for="item in callsToAction" :key="item.name" :href="item.href"
                  class="flex items-center justify-center gap-x-2.5 p-3 text-sm font-semibold leading-6 text-gray-900 hover:bg-gray-100">
                  <component :is="item.icon" class="h-5 w-5 flex-none text-gray-400" aria-hidden="true" />
                  {{ item.name }}
                </a>
              </div>
            </PopoverPanel>
          </transition>
        </Popover>

        <RouterLink to="/home/myMusic" class="text-sm font-semibold leading-6 text-gray-900">我的音乐</RouterLink>
        <RouterLink to="/home/community" class="text-sm font-semibold leading-6 text-gray-900">关注</RouterLink>
        <RouterLink to="/home/upload" class="text-sm font-semibold leading-6 text-gray-900">云音乐</RouterLink>
      </PopoverGroup>
      <div class="hidden lg:flex lg:flex-1 lg:justify-end">

        <a @click="toggleSearchDialog" class="ml-2 p-2 text-gray-400 hover:text-gray-500 mr-4">
      <MagnifyingGlassIcon class="size-6" aria-hidden="true" />
    </a>

        <button type="button" class="relative rounded-full bg-white p-1 text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
            <!-- <span class="absolute -inset-1.5" /> -->
            <BellIcon class="size-6" aria-hidden="true" />
          </button>

        <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton class="relative flex rounded-full bg-white text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                <span class="absolute -inset-1.5" />
                <span class="sr-only">Open user menu</span>
                <img class="size-10 rounded-full" :src="user.avatar" alt="" />
              </MenuButton>
            </div>
            <transition enter-active-class="transition ease-out duration-200" enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100" leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
              <MenuItems class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black/5 focus:outline-none">
                <MenuItem v-slot="{ active }">
                  <RouterLink to="/home/my/playlist" :class="[active ? 'bg-gray-100 outline-none' : '', 'block px-4 py-2 text-sm text-gray-700']">我的</RouterLink>
                </MenuItem>
                <MenuItem v-slot="{ active }">
                   <RouterLink to="/home/settings" :class="[active ? 'bg-gray-100 outline-none' : '', 'block px-4 py-2 text-sm text-gray-700']">设置</RouterLink>
                </MenuItem>
                <MenuItem v-slot="{ active }">
                  <a @click = "logout" :class="[active ? 'bg-gray-100 outline-none' : '', 'block px-4 py-2 text-sm text-gray-700']">登出</a>
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
      </div>
    </nav>
  </header>

  <RouterView />
  <div class="fixed bottom-0 left-0 right-0 h-[70px] bg-gray-200">
    <MusicPlayer />
  </div>
  <SearchDialog v-if="searchVisible" @close="closeSearchDialog" />
</template>

<script setup>
import { onMounted, ref } from 'vue'
import {
  Popover,
  PopoverButton,
  PopoverGroup,
  PopoverPanel,
  Menu, MenuButton, MenuItem, MenuItems
} from '@headlessui/vue'
import {
  ArrowPathIcon,
  ChartPieIcon,
  CursorArrowRaysIcon,
  FingerPrintIcon,
  SquaresPlusIcon,
} from '@heroicons/vue/24/outline'
import { BellIcon,MagnifyingGlassIcon} from '@heroicons/vue/24/outline'
import { ChevronDownIcon, PhoneIcon, PlayCircleIcon} from '@heroicons/vue/20/solid'
import { RouterLink, RouterView } from 'vue-router';
import { useRouter } from 'vue-router';
import MusicPlayer from '../components/MusicPlayer.vue';
import SearchDialog from '../components/SearchDialog.vue'
import { useUserStore } from '../stores/user';
import { computed } from 'vue';

const open = ref(true)
const searchVisible = ref(false)

const userStore = useUserStore();

// 计算属性来获取 user 信息
const user = computed(() => userStore.user);

const logout = () => {
  userStore.logout();
  router.push('/login');
}

const toggleSearchDialog = () => {
  searchVisible.value = !searchVisible.value;
}

const closeSearchDialog = () => {
  searchVisible.value = false;
}

const products = [
  { name: '首页', description: 'Get a better understanding of your traffic', href: '/home/index', icon: ChartPieIcon },
  { name: '歌手', description: 'Speak directly to your customers', href: '/home/player', icon: CursorArrowRaysIcon },
  { name: '分类歌单', description: 'Your customers’ data will be safe and secure', href: '/home/category', icon: FingerPrintIcon },
  { name: '排行榜', description: 'Connect with third-party tools', href: '#', icon: SquaresPlusIcon },
  { name: '专辑', description: 'Build strategic funnels that will convert', href: '#', icon: ArrowPathIcon },
]
const callsToAction = [
  { name: 'Watch demo', href: '#', icon: PlayCircleIcon },
  { name: 'Contact sales', href: '#', icon: PhoneIcon },
]
const router = useRouter();
</script>