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
              <input id="search" v-model="search" name="search"
                class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder="Search" />
            </div>
          </div>

          <!-- Genre Dropdown -->
          <div>
            <label for="genre" class="sr-only">Genre</label>
            <select id="genre" v-model="genre" name="genre"
              class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option value="">全部</option>
              <option v-for="genreOption in genreOptions" :key="genreOption.genreId" :value="genreOption.name">{{ genreOption.name }}</option>
            </select>
          </div>

          <!-- Gender Dropdown -->
          <div>
            <label for="gender" class="sr-only">Gender</label>
            <select id="gender" v-model="gender" name="gender"
              class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md">
              <option v-for="genderOption in genderOptions" :key="genderOption.value" :value="genderOption.value">{{ genderOption.label }}</option>
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
        <button type="button" @click="openAddArtistModal"
          class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          添加歌手</button>
      </div>
    </div>
    <div class="mt-1 flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
              <tr>
                <th scope="col" class="py-3.5 pr-3 pl-4 text-left text-sm font-semibold text-gray-900 sm:pl-0">名称</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">专辑数</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">国籍</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">类型</th>
                <th scope="col" class="relative py-3.5 pr-4 pl-3 sm:pr-0">
                  <span class="sr-only">编辑</span>
                </th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 bg-white">
              <tr v-for="artist in paginatedArtists" :key="artist.artistId" class="even:bg-gray-50">
                <td class="py-5 pr-3 pl-4 text-sm whitespace-nowrap sm:pl-0">
                  <div class="flex items-center">
                    <div class="size-11 shrink-0">
                      <img class="size-11" :src="artist.picUrl" alt="" />
                    </div>
                    <div class="ml-4">
                      <div class="font-medium text-gray-900">{{ artist.name }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                  <div class="text-gray-900">{{ artist.albums }}</div>
                </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                  <div class="text-gray-900">{{ artist.nationality }}</div>
                </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                  <div class="text-gray-900">{{ artist.gender }}</div>
                </td>
                <td class="relative py-5 pr-4 pl-3 text-right text-sm font-medium whitespace-nowrap sm:pr-0">
                  <a href="#" @click.prevent="openEditArtistModal(artist)"
                    class="text-indigo-600 hover:text-indigo-900">编辑<span class="sr-only">, {{ artist.name
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
            <span class="font-medium">{{ Math.min(currentPage * pageSize, (artists.length || 0)) }}</span>
            of
            <span class="font-medium">{{ artists.length || 0 }}</span>
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
    <!-- Artist Modal -->
    <PlayerModal v-model="isModalOpen" :artist="editingArtist" @save="handleSaveArtist" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import axios from 'axios'
import PlayerModal from '../../components/admin/PlayerModal.vue'

const artists = ref([])
const genreOptions = ref([])
const currentPage = ref(1)
const pageSize = ref(5)

const totalPages = computed(() => {
  const total = artists.value ? artists.value.length : 0
  return Math.ceil(total / pageSize.value)
})

const isModalOpen = ref(false)
const editingArtist = ref({})

const search = ref('')
const genre = ref('')
const gender = ref('')

const genderOptions = [
  { label: '全部', value: '' },
  { label: '男', value: '1' },
  { label: '女', value: '2' },
  { label: '组合', value: '3' }
]

const paginatedArtists = computed(() => {
  if (!artists.value) return []
  return artists.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value)
})

const fetchData = () => {
  const pageInfo = {
    name: search.value,
    nationality: genre.value,
    sex: gender.value,
    pageNum: currentPage.value,
    pageSize: pageSize.value
  }

  axios.post('/api/player/getPlayerPage', pageInfo)
    .then(response => {
      artists.value = response.data.data || []
      console.log(artists.value)
    })
    .catch(error => {
      console.error('Error fetching artists:', error)
      alert('Failed to fetch artists. Please try again later.')
    })
}

const fetchGenres = () => {
  axios.get('/api/player/getPlayerType')
    .then(response => {
      genreOptions.value = response.data.data || []
      console.log(genreOptions.value)
    })
    .catch(error => {
      console.error('Error fetching genres:', error)
      alert('Failed to fetch genres. Please try again later.')
    })
}


onMounted(() => {
  fetchData()
  fetchGenres()
})

watch([search, genre, gender], () => {
  currentPage.value = 1
  fetchData()
})

watch(currentPage, () => {
  fetchData()
})

const openAddArtistModal = () => {
  editingArtist.value = {}
  isModalOpen.value = true
}

const openEditArtistModal = (artist) => {
  editingArtist.value = { ...artist }
  isModalOpen.value = true
}

const handleSaveArtist = (artist) => {
  if (artist.artistId) {
    const index = artists.value.findIndex((a) => a.artistId === artist.artistId)
    if (index !== -1) {
      // Edit existing artist
      artists.value[index] = artist
    }
  } else {
    // Add new artist
    artist.id = artists.value.length + 1
    artists.value.push(artist)
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
</script>