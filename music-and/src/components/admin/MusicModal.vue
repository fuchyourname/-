<template>
  <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 modal-overlay"
    @click.self="closeModal">
    <div class="bg-white p-6 rounded-lg shadow-lg modal-content w-full max-w-2xl">
      <button class="absolute top-2 right-2 text-gray-600 hover:text-gray-800 modal-close"
        @click="closeModal">X</button>
      <form @submit.prevent="saveMusic">
        <div class="mb-4">
          <label for="title" class="block text-sm font-medium text-gray-700">Title</label>
          <input type="text" id="title" v-model="formData.title"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required />
        </div>
        <div class="mb-4">
          <label for="artist" class="block text-sm font-medium text-gray-700">Artist</label>
          <input type="text" id="artist" v-model="searchArtistQuery" @input="fetchArtists"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required />
          <ul v-if="artists.length && searchArtistQuery"
            class="absolute mt-1 w-full bg-white border border-gray-300 rounded-b-md shadow-lg max-h-40 overflow-y-auto">
            <li v-for="artist in artists" :key="artist.id" @click="selectArtist(artist)"
              class="cursor-pointer p-2 hover:bg-gray-100">
              {{ artist.name }}
            </li>
          </ul>
        </div>
        <div class="mb-4">
          <label for="album" class="block text-sm font-medium text-gray-700">Album</label>
          <input type="text" id="album" v-model="searchAlbumQuery" @input="fetchAlbums"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required />
          <ul v-if="albums.length && searchAlbumQuery"
            class="absolute mt-1 w-full bg-white border border-gray-300 rounded-b-md shadow-lg max-h-40 overflow-y-auto">
            <li v-for="album in albums" :key="album.id" @click="selectAlbum(album)"
              class="cursor-pointer p-2 hover:bg-gray-100">
              {{ album.title }}
            </li>
          </ul>
        </div>
   <div class="mb-4">
  <label for="genre" class="block text-sm font-medium text-gray-700">Genre</label>
  <select id="genre" v-model="formData.genre"
    class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
    required>
    <option v-for="genre in genres" :key="genre.genreId" :value="genre.genreId">
      {{ genre.name }}
    </option>
  </select>
</div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Cover Image</label>
          <div class="mt-2">
            <input type="file" id="cover-image" accept="image/*" @change="handleImageUpload" class="hidden"
              ref="coverImageInput" />
            <div v-if="!formData.coverImageFile" class="flex items-center space-x-2">
              <button type="button" @click="$refs.coverImageInput.click()"
                class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                  stroke="currentColor" class="w-5 h-5 mr-2">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
                </svg>
                Upload Cover Image
              </button>
            </div>
            <div v-else class="flex items-center space-x-2">
              <span>{{ formData.coverImageFile ? formData.coverImageFile.name : '' }}</span>
              <button type="button" @click="removeImage"
                class="inline-flex items-center px-3 py-2 border border-red-500 shadow-sm text-sm leading-4 font-medium rounded-md text-red-500 bg-white hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                Remove
              </button>
            </div>
          </div>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Music File</label>
          <div class="mt-2">
            <input type="file" id="music-file" accept="audio/*" @change="handleMusicFileUpload" class="hidden"
              ref="musicFileInput" />
            <div v-if="!formData.musicFile" class="flex items-center space-x-2">
              <button type="button" @click="$refs.musicFileInput.click()"
                class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                  stroke="currentColor" class="w-5 h-5 mr-2">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
                </svg>
                Upload Music File
              </button>
            </div>
            <div v-else class="flex items-center space-x-2">
              <span>{{ formData.musicFile ? formData.musicFile.name : '' }}</span>
              <button type="button" @click="formData.musicFile = null"
                class="inline-flex items-center px-3 py-2 border border-red-500 shadow-sm text-sm leading-4 font-medium rounded-md text-red-500 bg-white hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                Remove
              </button>
            </div>
          </div>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Lyrics File</label>
          <div class="mt-2">
            <input type="file" id="lyrics-file" accept=".txt,.lrc" @change="handleLyricsFileUpload" class="hidden"
              ref="lyricsFileInput" />
            <div v-if="!formData.lyricsFile" class="flex items-center space-x-2">
              <button type="button" @click="$refs.lyricsFileInput.click()"
                class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                  stroke="currentColor" class="w-5 h-5 mr-2">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
                </svg>
                Upload Lyrics File
              </button>
            </div>
            <div v-else class="flex items-center space-x-2">
              <span>{{ formData.lyricsFile ? formData.lyricsFile.name : '' }}</span>
              <button type="button" @click="formData.lyricsFile = null"
                class="inline-flex items-center px-3 py-2 border border-red-500 shadow-sm text-sm leading-4 font-medium rounded-md text-red-500 bg-white hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                Remove
              </button>
            </div>
          </div>
        </div>
        <div class="flex justify-end space-x-2">
          <button type="button"
            class="rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
            @click="closeModal">
            Cancel
          </button>
          <button type="submit"
            class="rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
            Save
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import axios from 'axios'


const props = defineProps({
  modelValue: Boolean,
  music: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
  title: props.music.title || '',
  artistId: props.music.artistId || null,
  albumId: props.music.albumId || null,
  genre: props.music.genre || '',
  coverImageFile: null,
  coverImagePreview: props.music.coverPath || '',
  musicFile: null,
  lyricsFile: null,
})

const searchArtistQuery = ref('')
const searchAlbumQuery = ref('')
const artists = ref([])
const albums = ref([])

const genres = ref([])

const fetchGenres = async () => {
  try {
    const response = await axios.get('/api/music/getGenresList')
    genres.value = response.data.data
  } catch (error) {
    console.error("Failed to fetch genres:", error)
  }
}

onMounted(() => {
  fetchGenres()
})

watch(
  () => props.modelValue,
  (newValue) => {
    show.value = newValue
    formData.value = {
      title: props.music.title || '',
      artistId: props.music.artistId || null,
      albumId: props.music.albumId || null,
      genre: props.music.genre || '',
      coverImageFile: null,
      coverImagePreview: props.music.coverPath || '',
      musicFile: null,
      lyricsFile: null,
    }
    searchArtistQuery.value = ''
    searchAlbumQuery.value = ''
    artists.value = []
    albums.value = []
  }
)

watch(
  () => show.value,
  (newValue) => {
    emit('update:modelValue', newValue)
  }
)

const closeModal = () => {
  show.value = false
}

const saveMusic = async () => {
  try {
    const formDataToSend = new FormData()
    formDataToSend.append('title', formData.value.title)
    formDataToSend.append('artistId', formData.value.artistId)
    formDataToSend.append('albumId', formData.value.albumId)
    formDataToSend.append('genreId', formData.value.genre)
    if (formData.value.coverImageFile) {
      formDataToSend.append('musicPic', formData.value.coverImageFile)
    }
    if (formData.value.musicFile) {
      formDataToSend.append('musicFile', formData.value.musicFile)
    }
    if (formData.value.lyricsFile) {
      formDataToSend.append('lyricFile', formData.value.lyricsFile)
    }

    const response = await axios.post('/api/music/upload', formDataToSend, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    emit('save', response.data.data)
    closeModal()
  } catch (error) {
    console.error("Failed to save music:", error)
  }
}

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    formData.value.coverImageFile = file
    // formData.value.coverImagePreview = URL.createObjectURL(file)
  }
}

const handleMusicFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    formData.value.musicFile = file
  }
}

const handleLyricsFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    formData.value.lyricsFile = file
  }
}

const removeImage = () => {
  formData.value.coverImageFile = null
  formData.value.coverImagePreview = ''
}

const fetchArtists = async () => {
  if (!searchArtistQuery.value) {
    artists.value = []
    return
  }
  try {
    const response = await axios.get(`/api/player/getPlayerByName/${encodeURIComponent(searchArtistQuery.value)}`)
    artists.value = response.data.data
  } catch (error) {
    console.error("Failed to fetch artists:", error)
  }
}

const fetchAlbums = async () => {
  if (!searchAlbumQuery.value) {
    albums.value = []
    return
  }
  try {
    const response = await axios.get(`/api/album/getAlbumsByName/${encodeURIComponent(searchAlbumQuery.value)}`)
    albums.value = response.data.data
  } catch (error) {
    console.error("Failed to fetch albums:", error)
  }
}

const selectArtist = (artist) => {
  formData.value.artistId = artist.artistId
  searchArtistQuery.value = artist.name
  artists.value = []
}

const selectAlbum = (album) => {
  formData.value.albumId = album.albumId
  searchAlbumQuery.value = album.title
  albums.value = []
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  position: relative;
  display: flex;
  flex-direction: column;
  max-height: 90%;
  margin: 0 1rem;
  padding: 1rem;
  border: 1px solid #e2e8f0;
  border-radius: 0.25rem;
  background: #fff;
  width: 90%;
  max-width: 500px;
}

.modal-close {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  padding: 0.25rem 0.5rem;
  background: transparent;
  border: 0;
  font-size: 1rem;
  cursor: pointer;
}
</style>