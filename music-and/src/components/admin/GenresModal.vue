<template>
    <div v-if="show" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <button class="modal-close" @click="closeModal">X</button>
            <form @submit.prevent="savePlaylist">
                <div class="mb-4">
                    <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
                    <input type="text" id="name" v-model="formData.name"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required />
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
                    <textarea id="description" v-model="formData.description"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        required></textarea>
                </div>
                <div class="mb-4">
                    <label class="block text-sm font-medium text-gray-700">Image</label>
                    <div v-if="!formData.imagePreview" class="mt-2">
                        <input type="file" id="image" accept="image/*" @change="handleImageUpload" class="hidden"
                            ref="fileInput" />
                        <button type="button" @click="$refs.fileInput.click()"
                            class="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="size-6">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
                            </svg>
                            Upload Image
                        </button>
                    </div>
                    <div v-else class="relative mt-2 ml-0">
                        <img :src="formData.imagePreview" alt="Uploaded Image" class="w-16 h-16" />
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
import { ref, watch } from 'vue'

const props = defineProps({
    modelValue: Boolean,
    playlist: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
    ...props.playlist,
    imageFile: null,
    imagePreview: props.playlist.image || ''
})

watch(
    () => props.modelValue,
    (newValue) => {
        show.value = newValue
        formData.value = {
            ...props.playlist,
            imageFile: null,
            imagePreview: props.playlist.image || ''
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

const savePlaylist = () => {
    emit('save', { ...formData.value })
    closeModal()
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