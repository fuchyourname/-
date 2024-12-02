<template>
  <div class="ml-32">
    <span>“{{ searchQuery }}”的相关搜索如下</span>
    <div class="grid grid-cols-1 sm:hidden">
      <!-- Use an "onChange" listener to redirect the user to the selected tab URL. -->
      <select aria-label="Select a tab" class="col-start-1 row-start-1 w-full appearance-none rounded-md bg-white py-2 pl-3 pr-8 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600" @change="handleTabChange">
        <option v-for="tab in tabs" :key="tab.name" :value="tab.href" :selected="tab.current">{{ tab.name }}</option>
      </select>
      <ChevronDownIcon class="pointer-events-none col-start-1 row-start-1 mr-2 size-5 self-center justify-self-end fill-gray-500" aria-hidden="true" />
    </div>
    <div class="hidden sm:block">
      <div class="border-b border-gray-200">
        <nav class="-mb-px flex space-x-8" aria-label="Tabs">
          <a v-for="tab in tabs" :key="tab.name" :href="tab.href" :class="[tab.current ? 'border-indigo-500 text-indigo-600' : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700', 'group inline-flex items-center border-b-2 px-1 py-4 text-sm font-medium']" :aria-current="tab.current ? 'page' : undefined" @click.prevent="handleTabClick(tab)">
            <component :is="tab.icon" :class="[tab.current ? 'text-indigo-500' : 'text-gray-400 group-hover:text-gray-500', '-ml-0.5 mr-2 size-5']" aria-hidden="true" />
            <span>{{ tab.name }}</span>
          </a>
        </nav>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ChevronDownIcon } from '@heroicons/vue/16/solid'
import { BuildingOfficeIcon, CreditCardIcon, UserIcon, UsersIcon, SpeakerWaveIcon, FolderIcon } from '@heroicons/vue/20/solid'

const route = useRoute()
const router = useRouter()

const searchQuery = ref(route.params.query)

const tabs = ref([
  { name: '综合', href: `#`, icon: CreditCardIcon, current: false },
  { name: '单曲', href: `#`, icon: SpeakerWaveIcon, current: false }, // 替换 MusicNoteIcon 为 SpeakerWaveIcon
  { name: '歌单', href: `#`, icon: FolderIcon, current: false },
  { name: '歌手', href: `#`, icon: UsersIcon, current: true },
  { name: '专辑', href: `#`, icon: BuildingOfficeIcon, current: false },
  { name: '用户', href: `#`, icon: UserIcon, current: false },
])

// 初始化 tabs 的 href 和 current 状态
onMounted(() => {
  updateTabsHref()
})

// 监听路由参数变化
watch(
  () => route.params.query,
  (newQuery) => {
    searchQuery.value = newQuery
    updateTabsHref()
  }
)

// 更新 tabs 的 href 属性
function updateTabsHref() {
  tabs.value.forEach((tab) => {
    tab.href = `/search-results/${searchQuery.value}?tab=${encodeURIComponent(tab.name.toLowerCase())}`
  })
}

// 处理 Tab 切换
function handleTabClick(tab) {
  tabs.value.forEach((t) => (t.current = t.name === tab.name))
  router.push(tab.href)
}

// 处理下拉菜单 Tab 切换
function handleTabChange(event) {
  const selectedHref = event.target.value
  const selectedTab = tabs.value.find((tab) => tab.href === selectedHref)
  if (selectedTab) {
    tabs.value.forEach((t) => (t.current = t.name === selectedTab.name))
    router.push(selectedHref)
  }
}
</script>

<style scoped>
/* 添加样式 */
</style>