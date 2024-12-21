<!-- SerachResView.vue -->
<template>
  <div class="ml-32">
    <span>“{{ searchQuery }}”的相关搜索如下</span>
    <div class="grid grid-cols-1 sm:hidden">
      <select aria-label="Select a tab" class="col-start-1 row-start-1 w-full appearance-none rounded-md bg-white py-2 pl-3 pr-8 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600" @change="handleTabChange">
        <option v-for="tab in tabs" :key="tab.name" :value="tab.name" :selected="tab.current">{{ tab.name }}</option>
      </select>
      <ChevronDownIcon class="pointer-events-none col-start-1 row-start-1 mr-2 size-5 self-center justify-self-end fill-gray-500" aria-hidden="true" />
    </div>
    <div class="hidden sm:block">
      <div class="border-b border-gray-200">
        <nav class="-mb-px flex space-x-8" aria-label="Tabs">
          <router-link v-for="tab in tabs" :key="tab.name" :to="{ name: tab.searchType, params: { keyword: searchQuery } }" :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700', 'group inline-flex items-center border-b-2 px-1 py-4 text-sm font-medium']" :aria-current="tab.current ? 'page' : undefined" @click.prevent="handleTabClick(tab)">
            <component :is="tab.icon" :class="[tab.current ? 'text-indigo-500' : 'text-gray-400 group-hover:text-gray-500', '-ml-0.5 mr-2 size-5']" aria-hidden="true" />
            <span>{{ tab.name }}</span>
          </router-link>
        </nav>
      </div>
    </div>
  </div>
  <router-view :searchQuery="searchQuery" :currentTab="currentTab" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ChevronDownIcon } from '@heroicons/vue/16/solid'
import { BuildingOfficeIcon, CreditCardIcon, UserIcon, UsersIcon, SpeakerWaveIcon, FolderIcon } from '@heroicons/vue/20/solid'

const route = useRoute()
const router = useRouter()

// 从路由中提取查询参数
const searchQuery = ref(route.params.keyword || '')

const currentTab = ref('单曲')

const tabs = ref([
  { name: '单曲', href: '', icon: SpeakerWaveIcon, current: true, searchType: 'songItems' },
  { name: '歌单', href: '', icon: FolderIcon, current: false, searchType: 'playListItems' },
  { name: '歌手', href: '', icon: UsersIcon, current: false, searchType: 'playerItems' },
  { name: '专辑', href: '', icon: BuildingOfficeIcon, current: false, searchType: 'albumItems' },
  { name: '用户', href: '', icon: UserIcon, current: false, searchType: 'userItems' },
])

onMounted(() => {
  updateTabsHref()
  handleTabClick(tabs.value[0])
})

watch(
  () => route.params.keyword,
  (newKeyword) => {
    searchQuery.value = newKeyword
    updateTabsHref()
    handleTabClick(tabs.value[0])
  }
)

watch(
  () => route.params.tab,
  (newTab) => {
    const selectedTab = tabs.value.find(tab => tab.searchType === newTab)
    if (selectedTab) {
      currentTab.value = selectedTab.name
    }
  }
)

function updateTabsHref() {
  tabs.value.forEach((tab) => {
    tab.href = `/home/serach/${encodeURIComponent(searchQuery.value)}/${tab.searchType}`
    tab.current = tab.searchType === route.params.tab
  })
}

function handleTabClick(tab) {
  currentTab.value = tab.name
  router.push({ name: tab.searchType, params: { keyword: searchQuery.value } })
}

function handleTabChange(event) {
  const selectedName = event.target.value
  const selectedTab = tabs.value.find((tab) => tab.name === selectedName)
  if (selectedTab) {
    currentTab.value = selectedTab.name
    router.push({ name: selectedTab.searchType, params: { keyword: searchQuery.value } })
  }
}
</script>

<style scoped>
/* 添加样式 */
</style>