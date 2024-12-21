<template>
  <div class="px-4 sm:px-6 lg:px-8">
    <div class="sm:flex sm:items-center">
      <div class="sm:flex-auto">
        <div class="flex space-x-4">
          <!-- Search Input -->
          <div class="flex-1">
            <label for="search" class="sr-only">Search</label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg class="h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                     fill="currentColor" aria-hidden="true">
                  <path fill-rule="evenodd"
                        d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                        clip-rule="evenodd"/>
                </svg>
              </div>
              <input id="search" name="search"
                     class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                     placeholder="Search" v-model="searchQuery"/>
            </div>
          </div>

          <!-- Dropdown -->
          <div>
            <label for="type" class="sr-only">Type</label>
            <select id="type" name="type"
                    class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    v-model="filterType">
              <option value="">全部</option>
              <option v-for="type in playlistTypes" :value="type.genreId" :key="type.genreId">{{ type.name }}</option>
              <!-- Add more types as needed -->
            </select>
          </div>

          <!-- Search Button -->
          <button type="button"
                  class="inline-flex items-center px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  @click="performSearch">
            <svg class="-ml-1 mr-2 h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                 fill="currentColor" aria-hidden="true">
              <path fill-rule="evenodd"
                    d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                    clip-rule="evenodd"/>
            </svg>
            Search
          </button>
        </div>
      </div>
      <div class="mt-4 sm:mt-0 sm:ml-16 sm:flex-none">
        <button type="button" @click="openAddPlaylistModal"
                class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          创建歌单</button>
      </div>
    </div>
    <div class="mt-1 flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
            <tr>
              <th scope="col" class="py-3.5 pr-3 pl-4 text-left text-sm font-semibold text-gray-900 sm:pl-0">名称</th>
              <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">创建人</th>
              <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">类型</th>
              <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">歌曲数</th>
              <th scope="col" class="relative py-3.5 pr-4 pl-3 sm:pr-0">
                <span class="sr-only">编辑</span>
              </th>
            </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 bg-white">
            <tr v-for="playlist in paginatedPlaylists" :key="playlist.playlistId" class="even:bg-gray-50">
              <td class="py-5 pr-3 pl-4 text-sm whitespace-nowrap sm:pl-0">
                <div class="flex items-center">
                  <img :src="playlist.imageUrl" alt="Playlist Image" class="h-10 w-10 flex-shrink-0"/>
                  <div class="ml-4">
                    <div class="font-medium text-gray-900">{{ playlist.name }}</div>
                  </div>
                </div>
              </td>
              <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <div>{{ playlist.userName }}</div>
              </td>
              <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <div>{{ playlist.type }}</div>
              </td>
              <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <div>{{ playlist.songCount }}</div>
              </td>
              <td class="relative py-5 pr-4 pl-3 text-right text-sm font-medium whitespace-nowrap sm:pr-0">
                <a href="#" @click.prevent="openEditPlaylistModal(playlist)"
                   class="text-indigo-600 hover:text-indigo-900">编辑<span class="sr-only">, {{ playlist.name
                    }}</span></a>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="mt-1">
      <nav class="flex items-center justify-between border-t border-gray-200 px-4 py-3 sm:px-6" aria-label="Pagination">
        <div class="hidden sm:block">
          <p class="text-sm text-gray-700">
            Showing
            <span class="font-medium">{{ (currentPage - 1) * pageSize + 1 }}</span>
            to
            <span class="font-medium">{{ Math.min(currentPage * pageSize, filteredPlaylists.length) }}</span>
            of
            <span class="font-medium">{{ filteredPlaylists.length }}</span>
            results
          </p>
        </div>
        <div class="flex flex-1 justify-between sm:justify-end">
          <button @click="prevPage" :disabled="currentPage === 1"
                  class="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-3 py-2 text-sm font-semibold text-gray-900 hover:bg-gray-50">
            上一页
          </button>
          <button @click="nextPage" :disabled="currentPage === totalPages"
                  class="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-3 py-2 text-sm font-semibold text-gray-900 hover:bg-gray-50">
            下一页
          </button>
        </div>
      </nav>
    </div>
    <!-- Playlist Modal -->
    <PlaylistModal v-model="isModalOpen" :playlist="editingPlaylist" @save="handleSavePlaylist"/>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import PlaylistModal from '../../components/admin/PlayListModal.vue'
import axios from 'axios'

const playlists = ref([])
const playlistTypes = ref([])

const searchQuery = ref('')
const filterType = ref('')

const filteredPlaylists = computed(() => {
  return playlists.value.filter(playlist =>
    playlist.name.toLowerCase().includes(searchQuery.value.toLowerCase()) &&
    (filterType.value === '' || playlist.type === filterType.value)
  )
})

const currentPage = ref(1)
const pageSize = 5

const totalPages = computed(() => Math.ceil(filteredPlaylists.value.length / pageSize))

const paginatedPlaylists = computed(() =>
  filteredPlaylists.value.slice((currentPage.value - 1) * pageSize, currentPage.value * pageSize)
)

const isModalOpen = ref(false)
const editingPlaylist = ref({})

const performSearch = () => {
  currentPage.value = 1
}

watch([searchQuery, filterType], performSearch)

const openAddPlaylistModal = () => {
  editingPlaylist.value = {}
  isModalOpen.value = true
}

const openEditPlaylistModal = (playlist) => {
  editingPlaylist.value = { ...playlist }
  isModalOpen.value = true
}

const handleSavePlaylist = (playlist) => {
  if (playlist.playlistId) {
    // Edit existing playlist
    const index = playlists.value.findIndex(p => p.playlistId === playlist.playlistId)
    if (index !== -1) {
      playlists.value[index] = playlist
    }
  } else {
    // Add new playlist
    playlist.id = playlists.value.length + 1
    playlists.value.push(playlist)
  }
  isModalOpen.value = false
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const fetchPlaylists = async () => {
  try {
    const pageInfo = {
      name: searchQuery.value,
      type: filterType.value,
      pageNum: currentPage.value,
      pageSize: pageSize,
    }
    const response = await axios.post('/api/playList/getPlayListPage', pageInfo)
    playlists.value = response.data.data
  } catch (error) {
    console.error('Error fetching playlists:', error)
  }
}

const fetchPlaylistTypes = async () => {
  try {
    const response = await axios.get('/api/playList/getGenresList')
    playlistTypes.value = response.data.data
  } catch (error) {
    console.error('Error fetching playlist types:', error)
  }
}

onMounted(() => {
  fetchPlaylists()
  fetchPlaylistTypes()
})
</script>

<style scoped>
/* Add any additional styles here */
</style>