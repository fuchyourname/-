<template>
  <div class="flex flex-row ml-32">
    <img :src="person.imageUrl" class="h-48 w-48 flex-none  bg-white/10"/>
    <div class="flex flex-col ml-4">
      <span class="font-mono text-2xl">{{ person.name }}</span>
      <div class="flex flex-row mt-2">
        <RouterLink to="/home/follow" class="font-mono">关注 {{ person.likes }}</RouterLink>
        <RouterLink to="/home/fans"class="ml-4 font-mono">粉丝 {{ person.counts }}</RouterLink>
      </div>
      <span class="mt-2 text-xs text-gray-500">地区: {{ person.country }}</span>
    </div>
  </div>
  <div class="mt-4 ml-32">
    <div class="hidden sm:block">
      <div class="border-b border-gray-200">
        <nav class="-mb-px flex space-x-8" aria-label="Tabs">
          <RouterLink v-for="tab in tabs":to="tab.href" @click = "selectTab(tab)" :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-200 hover:text-gray-700', 'flex whitespace-nowrap border-b-2 px-1 py-4 text-sm font-medium']">
            {{ tab.name }}
            <span v-if="tab.count" :class="[tab.current ? 'bg-indigo-100 text-indigo-600' : 'bg-gray-100 text-gray-900', 'ml-3 hidden rounded-full px-2.5 py-0.5 text-xs font-medium md:inline-block']">{{ tab.count }}</span>
          </RouterLink>
        </nav>
      </div>
    </div>
  </div>
  <RouterView></RouterView>
</template>

<script setup>
import { onMounted } from 'vue';
import { RouterLink, RouterView,useRouter } from 'vue-router';

const router = useRouter()

const tabs = [
  { name: '歌单', href: '/home/my/playlist', count: '52', current: false },
  { name: '动态', href: '/home/my/dynamicsList', count: '6', current: false },
]
const person = {
  name: 'And',
  imageUrl: 'http://192.168.154.1:9000/music/cover/1/1005_1732423111003.jpg',
  country: 'United States',
  counts: 12,
  likes: 17
}
const selectTab = (tab) => {
  tabs.forEach(item => item.current = false)
  tab.current = true
}
</script>