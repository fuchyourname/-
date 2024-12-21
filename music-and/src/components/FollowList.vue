<template>
  <span class="ml-32 text-2xl">我的关注</span>
  <div class="mt-4 ml-32">
    <div class="hidden sm:block">
      <div class="border-b border-gray-200">
        <nav class="-mb-px flex space-x-8" aria-label="Tabs">
          <button v-for="tab in tabs" @click="selectTab(tab)"
            :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-200 hover:text-gray-700', 'flex whitespace-nowrap border-b-2 px-1 py-4 text-sm font-medium']">
            {{ tab.name }}
          </button>
        </nav>
      </div>
    </div>
  </div>
  <div class="grid grid-cols-4 gap-4 mt-8 ml-32">
    <div v-for="follow in followList" :key="follow.userId" class="flex items-center p-4 bg-white">
      <img :src="follow.picUrl" alt="头像" class="w-16 h-16 rounded-full mr-4" />
      <div class="flex-1">
        <span class="text-xl font-medium">{{ follow.userName }}</span>
        <p class="text-gray-600">{{ follow.description }}</p>
      </div>
      <button class="ml-auto flex px-4 py-2 rounded" @click="toggleChat(follow)">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
          stroke-width="1.5" stroke="currentColor" class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round"
            d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" />
        </svg>
        私信</button>
    </div>
  </div>
  <ChatDialog v-for="follow in chatOpenList" :key="follow.id" :user="follow" @close="closeChat(follow)"></ChatDialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'
import ChatDialog from './ChatDialog.vue';

const router = useRouter()
const chatOpenList = ref([])
const followList = ref([])

const tabs = [
  { name: '全部', href: '/home/my/playlist', count: '52', current: true },
  { name: '歌手', href: '/home/my/dynamicsList', count: '6', current: false },
  { name: '用户', href: '/home/my/dynamicsList', count: '6', current: false },
]

const fetchFollowList = async () => {
  try {
    const response = await axios.get('/api/user/getFollowUserList'); // 假设这是你的API地址
    followList.value = response.data.data; // 假设响应数据是一个数组
  } catch (error) {
    console.error("Failed to fetch follow list:", error);
  }
};

onMounted(() => {
  fetchFollowList();
});

const toggleChat = (follow) => {
  const index = chatOpenList.value.findIndex(f => f.id === follow.id);
  if (index === -1) {
    chatOpenList.value.push(follow);
  } else {
    chatOpenList.value.splice(index, 1);
  }
}

const closeChat = (follow) => {
  const index = chatOpenList.value.findIndex(f => f.id === follow.id);
  if (index !== -1) {
    chatOpenList.value.splice(index, 1);
  }
}
</script>