<template>
    <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
        <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-2xl">
            <button class="absolute top-4 right-4 text-gray-600 hover:text-gray-800" @click="closeModal">X</button>
            <form @submit.prevent="savePlaylist">
                <div class="mb-4">
                    <label for="name" class="block text-sm font-medium text-gray-700">歌单名</label>
                    <input type="text" id="name" v-model="formData.name"
                           class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                           required />
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-sm font-medium text-gray-700">歌单描述</label>
                    <textarea id="description" v-model="formData.description"
                              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                              required></textarea>
                </div>
                <div class="mb-4">
                    <label class="block text-sm font-medium text-gray-700">歌单封面</label>
                    <div v-if="!formData.imageUrl && !formData.imageFile" class="mt-2">
                        <input type="file" id="image" accept="image/*" @change="handleImageUpload" class="hidden"
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
                    <div v-else class="relative mt-2 ml-0">
                        <img :src="formData.imageUrl || formData.imagePreviewUrl" alt="Uploaded Image" class="w-16 h-16" />
                        <button type="button" @click="removeImage"
                                class="absolute top-0 ml-24 w-16 h-16 flex items-center justify-center text-white border border-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2">
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
import { ref, watch } from 'vue'
import axios from 'axios'

const props = defineProps({
    modelValue: Boolean,
    playlist: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
    playlistId: props.playlist.playlistId || '',
    name: props.playlist.name || '',
    description: props.playlist.description || '',
    imageFile: null,
    imageUrl: props.playlist.imageUrl || '', // 注意这里应该是 props.playlist.image 而不是 props.playlist.imageUrl
    imagePreviewUrl: '' // 新增字段用于存储新上传图片的预览地址
})

watch(
    () => props.modelValue,
    (newValue) => {
        show.value = newValue
        formData.value = {
            playlistId: props.playlist.playlistId || '',
            name: props.playlist.name || '',
            description: props.playlist.description || '',
            imageFile: null,
            imageUrl: props.playlist.imageUrl || '',
            imagePreviewUrl: ''
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

const savePlaylist = async () => {
    try {
        // 准备要发送的数据
        const dataToSend = {
            playlistId: formData.value.playlistId,
            name: formData.value.name,
            description: formData.value.description,
        }

        // 创建 FormData 对象
        const formDataToSend = new FormData()
        formDataToSend.append('playlistId', dataToSend.playlistId)
        formDataToSend.append('name', dataToSend.name)
        formDataToSend.append('description', dataToSend.description)
        formDataToSend.append('image', formData.value.imageFile)
     

        // 发送请求
        const response = await axios.post('/api/playList/update', formDataToSend, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })

        // 成功后关闭模态框
        closeModal()

        emit('save', response.data.data)
    } catch (error) {
        console.error('保存歌单失败:', error)
    }
}

const handleImageUpload = (event) => {
    const file = event.target.files[0]
    if (file) {
        formData.value.imageFile = file
        formData.value.imagePreviewUrl = URL.createObjectURL(file)
    }
}

const removeImage = () => {
    formData.value.imageFile = null
    formData.value.imagePreviewUrl = ''
    formData.value.imageUrl = '' // 清空现有图片URL
}
</script>

<style scoped>
/* 移除原有样式，因为 Tailwind CSS 已经提供了足够的样式 */
</style>