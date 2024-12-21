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
                    clip-rule="evenodd" />
                </svg>
              </div>
              <input v-model="searchName" id="search" name="search"
                class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder="Search" />
            </div>
          </div>

          <!-- Dropdown -->
          <div>
            <label for="role" class="sr-only">Role</label>
            <select v-model="selectedGenreId" id="role" name="role"
              class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option value="">All Genres</option>
              <option v-for="genre in genres" :key="genre.genreId" :value="genre.genreId">{{ genre.name }}</option>
            </select>
          </div>

          <!-- Search Button -->
          <button type="button" @click="fetchData"
            class="inline-flex items-center px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
            <svg class="-ml-1 mr-2 h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
              fill="currentColor" aria-hidden="true">
              <path fill-rule="evenodd"
                d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                clip-rule="evenodd" />
            </svg>
            Search
          </button>
        </div>
      </div>
      <div class="mt-4 sm:mt-0 sm:ml-16 sm:flex-none">
        <button type="button" @click="openAddMusicModal"
          class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          添加音乐</button>
      </div>
    </div>
    <div class="mt-1 flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
              <tr>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">标题</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">艺术家</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">分类</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">时长</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">专辑</th>
                <th scope="col" class="relative py-3.5 pr-4 pl-3 sm:pr-0">
                  <span class="sr-only">编辑</span>
                </th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 bg-white">
              <tr v-for="music in paginatedMusics" :key="music.songId" class="even:bg-gray-50">
                <td class="px-3 py-5 text-sm whitespace-nowrap">
                  <div class="flex items-center">
                    <img :src="music.coverPath" :alt="music.title" class="w-10 h-10 mr-3">
                    <div class="font-medium text-gray-900">{{ music.title }}</div>
                  </div>
                </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">{{ music.artist }}</td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">{{ music.genre }}</td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">{{ music.duration }}</td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">{{ music.album }}</td>
                <td class="relative py-5 pr-4 pl-3 text-right text-sm font-medium whitespace-nowrap sm:pr-0">
                  <a href="#" @click.prevent="openEditMusicModal(music)"
                    class="text-indigo-600 hover:text-indigo-900">编辑<span class="sr-only">, {{ music.title
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
            <span class="font-medium">{{ Math.min(currentPage * pageSize, musics.length) }}</span>
            of
            <span class="font-medium">{{ musics.length }}</span>
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
    <!-- Music Modal -->
    <MusicModal v-model="isModalOpen" :music="editingMusic" @save="handleSaveMusic" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import MusicModal from '../../components/admin/MusicModal.vue'

const musics = ref([])
const searchName = ref('')
const genres = ref([])
const selectedGenreId = ref('')
const currentPage = ref(1)
const pageSize = ref(5)

const totalPages = computed(() => Math.ceil(musics.value.length / pageSize.value))

const paginatedMusics = computed(() =>
  musics.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value)
)

const isModalOpen = ref(false)
const editingMusic = ref({})

const fetchData = async () => {
  try {
    const response = await axios.post('/api/music/getSongPage', {
      name: searchName.value,
      type: selectedGenreId.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    musics.value = response.data.data
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

const fetchGenres = async () => {
  try {
    const response = await axios.get('/api/music/getGenresList')
    genres.value = response.data.data
  } catch (error) {
    console.error('Error fetching genres:', error)
  }
}

const openAddMusicModal = () => {
  editingMusic.value = {}
  isModalOpen.value = true
}

const openEditMusicModal = (music) => {
  editingMusic.value = { ...music }
  isModalOpen.value = true
}

const handleSaveMusic = (music) => {
  if (music.id) {
    const index = musics.value.findIndex((m) => m.id === music.id)
    if (index !== -1) {
      // Edit existing music
      musics.value[index] = music
    } else {
      // Add new music
      musics.value.push(music)
    }
  }
  isModalOpen.value = false
  fetchData() // Refresh data after saving
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    fetchData()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    fetchData()
  }
}

onMounted(() => {
  fetchData()
  fetchGenres()
})
</script>

<style scoped>
/* 添加样式 */
</style>