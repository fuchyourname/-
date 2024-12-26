<!-- src/components/admin/GenreModal.vue -->
<template>
    <div v-if="show" class="fixed inset-0 z-50 w-full h-full bg-black bg-opacity-50 flex items-center justify-center" @click.self="closeModal">
        <div class="relative z-50 flex flex-col max-h-[90%] mx-4 p-4 border border-gray-300 rounded-lg bg-white w-full max-w-md">
            <button class="absolute top-2 right-2 p-1.5 bg-transparent border-0 text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2" @click="closeModal">X</button>
            <form @submit.prevent="saveGenre">
                <div class="mb-4">
                    <label for="name" class="block text-sm font-medium text-gray-700">类别名称</label>
                    <input type="text" id="name" v-model="formData.name"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required />
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-sm font-medium text-gray-700">类别描述</label>
                    <textarea id="description" v-model="formData.description"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        rows="4"></textarea>
                </div>
                <div class="mb-4">
                    <label for="category" class="block text-sm font-medium text-gray-700">分类</label>
                    <select id="category" v-model="formData.type"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm">
                        <option value="1">歌曲</option>
                        <option value="2">歌手</option>
                        <option value="3">歌单</option>
                        <option value="4">专辑</option>
                        <option value="5">用户</option>
                    </select>
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
    genre: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
    ...props.genre,
    name: props.genre.name || '',
    description: props.genre.description || '',
    type: props.genre.type || '歌曲'
})

watch(
    () => props.modelValue,
    (newValue) => {
        show.value = newValue
        formData.value = {
            ...props.genre,
            name: props.genre.name || '',
            description: props.genre.description || '',
            type: props.genre.type || '歌曲'
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

const saveGenre = async () => {
    const formDataToSend = new FormData()
    if(formData.value.genreId !== undefined) {
        formDataToSend.append('genreId', formData.value.genreId)
    }
    formDataToSend.append('name', formData.value.name)
    formDataToSend.append('description', formData.value.description)
    formDataToSend.append('type', formData.value.type)
    try {
        const response = await axios.post('/api/index/addGenres', formDataToSend)
        emit('save', response.data.data)
        closeModal()
    } catch (error) {
        console.error("Failed to save genre:", error)
    }
}
</script>