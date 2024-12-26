<!-- src/components/admin/AlbumModal.vue -->
<template>
    <div v-if="show" class="fixed inset-0 z-50 w-full h-full bg-black bg-opacity-50 flex items-center justify-center" @click.self="closeModal">
        <div class="relative z-50 flex flex-col max-h-[90%] mx-4 p-4 border border-gray-300 rounded-lg bg-white w-full max-w-md">
            <button class="absolute top-2 right-2 p-1.5 bg-transparent border-0 text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2" @click="closeModal">X</button>
            <form @submit.prevent="saveAlbum">
                <div class="mb-4">
                    <label for="title" class="block text-sm font-medium text-gray-700">标题</label>
                    <input type="text" id="title" v-model="formData.title"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required />
                </div>
                <div class="mb-4">
                    <label for="artist" class="block text-sm font-medium text-gray-700">艺术家</label>
                    <input type="text" id="artist" v-model="formData.artist"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        @input="searchArtists" required />
                    <!-- <ul v-if="searchResults.length" class="mt-2 border border-gray-300 rounded-md shadow-sm">
                        <li v-for="artist in searchResults" :key="artist.id" class="px-3 py-2 cursor-pointer hover:bg-gray-100"
                            @click="selectArtist(artist)">
                            {{ artist.name }}
                        </li>
                    </ul> -->
                </div>
                <div class="mb-4">
                    <label for="releaseDate" class="block text-sm font-medium text-gray-700">发布时间</label>
                    <input type="date" id="releaseDate" v-model="formData.createTime"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required />
                </div>
                <div class="mb-4">
                    <label for="genre" class="block text-sm font-medium text-gray-700">类别</label>
                    <select id="genre" v-model="formData.type"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required>
                        <option v-for="genre in genres" :key="genre.genreId" :value="genre.genreId">
                            {{ genre.name }}
                        </option>
                    </select>
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-sm font-medium text-gray-700">描述</label>
                    <textarea id="description" v-model="formData.description"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        rows="4"></textarea>
                </div>
                <div class="mb-4">
                    <label class="block text-sm font-medium text-gray-700">专辑封面</label>
                    <div v-if="!formData.imagePreview" class="mt-2">
                        <input type="file" id="coverImage" accept="image/*" @change="handleImageUpload" class="hidden"
                            ref="fileInput" />
                        <button type="button" @click="$refs.fileInput.click()"
                            class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
                            </svg>
                            上传
                        </button>
                    </div>
                    <div v-else class="relative mt-2">
                        <img :src="formData.imagePreview" alt="Uploaded Cover Image" class="w-16 h-16" />
                        <button type="button" @click="removeImage"
                            class="absolute top-0 ml-20 w-16 h-16 flex items-center justify-center text-white border border-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="gray" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M5 12h14" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="flex justify-end">
                    <button type="button"
                        class="mr-2 rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        @click="closeModal">
                        取消
                    </button>
                    <button type="submit"
                        class="rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                        保存
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
    album: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
    ...props.album,
    imageFile: null,
    imagePreview: props.album.coverImage || '',
    description: props.album.description || ''
})
const genres = ref([])
const searchResults = ref([])
const isEditing = ref(false) // 添加 isEditing 变量

watch(
    () => props.modelValue,
    (newValue) => {
        show.value = newValue
        formData.value = {
            ...props.album,
            imageFile: null,
            imagePreview: props.album.coverImage || '',
            description: props.album.description || ''
        }
        isEditing.value = !!props.album.albumId // 如果有 albumId 则为编辑状态
    }
)

watch(
    () => show.value,
    (newValue) => {
        emit('update:modelValue', newValue)
    }
)

watch(
    () => formData.value.artist,
    (newValue) => {
        if (newValue.length >= 3) { // 设置一个阈值，避免频繁请求
            searchArtists(newValue);
        } else {
            searchResults.value = [];
        }
    }
)

const closeModal = () => {
    show.value = false
}

const saveAlbum = async () => {
    try {
        const formDataToSend = new FormData()
        formDataToSend.append('albumId', formData.value.albumId)
        formDataToSend.append('title', formData.value.title)
        formDataToSend.append('releaseDate', formData.value.createTime)
        formDataToSend.append('type', formData.value.type)
        formDataToSend.append('description', formData.value.description)
        if (formData.value.imageFile) {
            formDataToSend.append('coverImage', formData.value.imageFile)
        }

        const response = await axios.post('/api/album/updateAlbum', formDataToSend, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        emit('save', response.data.data)
        closeModal()
    } catch (error) {
        console.error("Failed to save album:", error)
    }
}

const handleImageUpload = (event) => {
    const file = event.target.files[0]
    if (file) {
        formData.value.imageFile = file
        formData.value.imagePreview = URL.createObjectURL(file)
    }
}

const removeImage = () => {
    formData.value.imageFile = null
    formData.value.imagePreview = ''
}

const fetchGenres = async () => {
    try {
        const response = await axios.get('/api/album/getGenresList')
        genres.value = response.data.data
    } catch (error) {
        console.error("Failed to fetch genres:", error)
    }
}

const searchArtists = async (artistName) => {
    // if (isEditing.value) return; // 如果是编辑状态，不进行搜索
    if (!artistName || artistName.length < 3) {
        searchResults.value = [];
        return;
    }
    try {
        const response = await axios.get(`/api/player/getPlayerByName/${artistName}`);
        searchResults.value = response.data.data;
    } catch (error) {
        console.error("Failed to search artists:", error);
    }
}

const selectArtist = (artist) => {
    formData.value.artist = artist.name
    searchResults.value = []
}

onMounted(() => {
    fetchGenres()
})
</script>