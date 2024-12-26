<template>
  <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 modal-overlay" @click.self="closeModal">
    <div class="bg-white p-6 rounded-lg shadow-lg modal-content w-full max-w-2xl">
      <button class="absolute top-2 right-2 text-gray-600 hover:text-gray-800 modal-close" @click="closeModal">X</button>
      <form @submit.prevent="saveArtist">
        <div class="mb-4">
          <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
          <input type="text" id="name" v-model="formData.name"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required />
        </div>
        <div class="mb-4">
          <label for="nationality" class="block text-sm font-medium text-gray-700">Nationality</label>
          <select id="nationality" v-model="formData.nationality"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required>
            <option v-for="nationality in nationalities" :key="nationality.value" :value="nationality.value">
              {{ nationality.label }}
            </option>
          </select>
        </div>
        <div class="mb-4">
          <label for="type" class="block text-sm font-medium text-gray-700">Type</label>
          <select id="type" v-model.number="formData.type"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            required>
            <option value="1">男</option>
            <option value="2">女</option>
            <option value="3">组合</option>
          </select>
        </div>
        <div class="mb-4">
          <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
          <textarea id="description" v-model="formData.description"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm resize-y min-h-[100px]"
            rows="4"></textarea>
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Image</label>
          <div v-if="formData.imagePreview" class="relative mt-2 ml-0">
            <img :src="formData.imagePreview" alt="Uploaded Image" class="w-16 h-16 object-cover" />
            <button type="button" @click="removeImage"
              class="absolute top-0 ml-20 w-16 h-16 flex items-center justify-center text-white border border-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                stroke="gray" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M5 12h14" />
              </svg>
            </button>
          </div>
          <div v-else class="mt-2">
            <input type="file" id="image" accept="image/*" @change="handleImageUpload" class="hidden"
              ref="fileInput" />
            <button type="button" @click="$refs.fileInput.click()"
              class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                stroke="currentColor" class="size-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
              </svg>
              Upload Image
            </button>
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
  artist: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
  name: props.artist.name || '',
  nationality: props.artist.nationality || '',
  type: Number(props.artist.type) || 1,
  description: props.artist.description || '', // 添加描述字段
  imageFile: null,
  imagePreview: props.artist.picUrl || ''
})
const nationalities = ref([]) // 存储国籍选项

// 获取国籍选项
const fetchNationalities = async () => {
  try {
    const response = await axios.get('/api/index/getGenresList/2') // 假设后端接口路径为 /api/nationalities
    nationalities.value = response.data.data.map(nation => ({
      value: nation.name, // 假设返回的数据结构中有 code 和 label 字段
      label: nation.name
    }))
  } catch (error) {
    console.error("Failed to fetch nationalities:", error)
  }
}

onMounted(() => {
  fetchNationalities()
})

watch(
  () => props.modelValue,
  (newValue) => {
    show.value = newValue
    formData.value = {
      name: props.artist.name || '',
      nationality: props.artist.nationality || '',
      type: props.artist.gender == '男' ? 1 : props.artist.gender == '女' ? 2 : 3,
      description: props.artist.bio || '', // 更新描述字段
      imageFile: null,
      imagePreview: props.artist.picUrl || ''
    }
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

const saveArtist = async () => {
  try {
    const formDataToSend = new FormData()
    if (props.artist.artistId) {
      formDataToSend.append('artistId', props.artist.artistId)
    }
    formDataToSend.append('name', formData.value.name)
    formDataToSend.append('nationality', formData.value.nationality)
    formDataToSend.append('sex', formData.value.type.toString())
    formDataToSend.append('bio', formData.value.description) // 添加描述字段
    if (formData.value.imageFile) {
      formDataToSend.append('pic', formData.value.imageFile)
    }

    const response = await axios.post('/api/player/updatePlayer', formDataToSend, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    emit('save', response.data.data)
    closeModal()
  } catch (error) {
    console.error("Failed to save artist:", error)
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
</script>

<style scoped>
/* 移除了原有的 .modal-overlay 样式 */
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