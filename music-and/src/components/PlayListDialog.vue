<template>
  <!-- Create Playlist Modal -->
  <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white p-8 rounded-lg w-full max-w-lg relative">
      <button @click="closeModal" class="absolute top-3 right-3 text-gray-500 hover:text-gray-700">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
      <h2 class="text-lg font-semibold mb-4">创建新歌单</h2>
      <form @submit.prevent="handleSubmit">
        <div class="mb-4">
          <label for="playlistName" class="block text-gray-700 text-sm font-bold mb-2">歌单名</label>
          <input v-model="playlist.name" type="text" id="playlistName" placeholder="歌单名称" class="border border-gray-300 p-2 rounded w-full focus:ring-blue-500 focus:border-blue-500" required />
        </div>
        <div class="mb-4">
          <label for="playlistDescription" class="block text-gray-700 text-sm font-bold mb-2">歌单描述</label>
          <textarea v-model="playlist.description" id="playlistDescription" rows="4" placeholder="歌单描述" class="border border-gray-300 p-2 rounded w-full focus:ring-blue-500 focus:border-blue-500"></textarea>
        </div>
        <div class="mb-4">
          <label for="playlistCover" class="block text-gray-700 text-sm font-bold mb-2">歌单封面</label>
          <div v-if="!playlist.imageUrl">
            <input type="file" id="playlistCover" @change="handleFileUpload" accept="image/*" class="hidden" ref="fileInput" />
            <button @click.prevent="$refs.fileInput.click()" class="flex items-center bg-gray-100 text-gray-700 px-4 py-2 rounded">
              <FolderPlusIcon class="h-5 w-5 mr-2" aria-hidden="true" />
              选择文件
            </button>
          </div>
          <div v-if="playlist.imageUrl" class="flex items-center">
            <img :src="playlist.imageUrl" alt="Playlist Cover" class="max-h-20 mr-2" />
            <button @click="removeImage" class="text-red-500 hover:text-red-700">删除</button>
          </div>
        </div>
        <div class="mb-4">
          <label for="playlistType" class="block text-gray-700 text-sm font-bold mb-2">歌单类型</label>
          <select v-model="playlist.type" id="playlistType" class="border border-gray-300 p-2 rounded w-full focus:ring-blue-500 focus:border-blue-500" required>
            <option v-for="type in playlistTypes" :key="type.value" :value="type.value">{{ type.label }}</option>
          </select>
        </div>
        <div class="flex items-center justify-end">
          <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded mr-2">保存</button>
          <button @click="closeModal" class="bg-gray-300 text-gray-700 px-4 py-2 rounded">取消</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { FolderPlusIcon } from '@heroicons/vue/24/outline'
import axios from 'axios'

const props = defineProps({
  show: Boolean,
  playlist: Object,
  playlistTypes: Array
})

const emit = defineEmits(['create', 'close'])

const fileInput = ref(null)

const handleFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    props.playlist.imageFile = file
    const reader = new FileReader()
    reader.onload = (e) => {
      props.playlist.imageUrl = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const removeImage = () => {
  props.playlist.imageUrl = null
  props.playlist.imageFile = null
}

const handleSubmit = async () => {
  if (props.playlist.name && props.playlist.imageFile) {
    try {
      const formDataToSend = new FormData()
      formDataToSend.append('name', props.playlist.name)
      formDataToSend.append('description', props.playlist.description)
      formDataToSend.append('type', props.playlist.type === 'public' ? 1 : 0)
      formDataToSend.append('image', props.playlist.imageFile)

      const response = await axios.post('/api/playList/addPlayList', formDataToSend, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })

      if (response.status === 200) {
        emit('create')
        closeModal()
      }
    } catch (error) {
      console.error('创建歌单失败:', error)
    }
  }
}

const closeModal = () => {
  emit('close')
}
</script>