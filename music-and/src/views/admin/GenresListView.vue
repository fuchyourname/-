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
            <label for="category" class="sr-only">Category</label>
            <select id="category" name="category"
                    class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    v-model="selectedCategory">
              <option value="">全部</option>
              <option value="1">歌曲</option>
              <option value="2">歌手</option>
              <option value="3">歌单</option>
              <option value="4">专辑</option>
              <!-- Add more categories as needed -->
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
        <button type="button" @click="openAddGenreModal"
                class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          创建类型</button>
      </div>
    </div>
    <div class="mt-1 flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
            <tr>
              <th scope="col" class="py-3.5 pr-3 pl-4 text-left text-sm font-semibold text-gray-900 sm:pl-0">名称</th>
              <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">描述</th>
              <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">类型</th>
              <th scope="col" class="relative py-3.5 pr-4 pl-3 sm:pr-0">
                <span class="sr-only">编辑</span>
              </th>
            </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 bg-white">
            <tr v-for="genre in paginatedGenres" :key="genre.genreId" class="even:bg-gray-50">
              <td class="py-5 pr-3 pl-4 text-sm whitespace-nowrap sm:pl-0">
                <div class="flex items-center">
                  <div class="ml-4">
                    <div class="font-medium text-gray-900">{{ genre.name }}</div>
                  </div>
                </div>
              </td>
              <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <div>{{ genre.description }}</div>
              </td>
              <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <div>{{ genre.type }}</div>
              </td>
              <td class="relative py-5 pr-4 pl-3 text-right text-sm font-medium whitespace-nowrap sm:pr-0">
                <a href="#" @click="openEditGenreModal(genre)"
                   class="text-indigo-600 hover:text-indigo-900">编辑<span class="sr-only">,
                     {{ genre.name }}</span></a>
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
            <span class="font-medium">{{ Math.min(currentPage * pageSize, genres.length) }}</span>
            of
            <span class="font-medium">{{ genres.length }}</span>
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
    <!-- Genre Modal -->
    <GenreModal v-model="isModalOpen" :genre="editingGenre" @save="handleSaveGenre"/>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import axios from 'axios'
import GenreModal from '../../components/admin/GenresModal.vue'

const genres = ref([])
const searchQuery = ref('')
const selectedCategory = ref('')
const currentPage = ref(1)
const pageSize = ref(5)

const fetchGenres = async () => {
  try {
    const response = await axios.post('/api/index/getGenresPage', {
      type: selectedCategory.value,
      name: searchQuery.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    genres.value = response.data.data
  } catch (error) {
    console.error('Error fetching genres:', error)
  }
}

const totalPages = computed(() => Math.ceil(genres.value.length / pageSize.value))

const paginatedGenres = computed(() =>
genres.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value)
)

const isModalOpen = ref(false)
const editingGenre = ref({})

const performSearch = () => {
  currentPage.value = 1
  fetchGenres()
}

watch([searchQuery, selectedCategory], performSearch)
watch(currentPage, fetchGenres)

const openAddGenreModal = () => {
  editingGenre.value = {}
  isModalOpen.value = true
}

const openEditGenreModal = (genre) => {
  editingGenre.value = { ...genre }
  isModalOpen.value = true
}

const handleSaveGenre = (genre) => {
  if (genre.genreId) {
    // Edit existing genre
    const index = genres.value.findIndex(g => g.genreId === genre.genreId)
    if (index !== -1) {
      genres.value[index] = genre
    }
  } else {
    genres.value.push(genre)
  }
  isModalOpen.value = false
  fetchGenres() // Refresh the list after saving
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

onMounted(() => {
  fetchGenres()
})
</script>