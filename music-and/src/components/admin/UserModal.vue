<template>
    <div v-if="show" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
        <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-2xl">
            <button class="absolute top-4 right-4 text-gray-600 hover:text-gray-800" @click="closeModal">X</button>
            <form @submit.prevent="saveUser">
                <div class="mb-4">
                    <label for="name" class="block text-sm font-medium text-gray-700">昵称</label>
                    <input type="text" id="name" v-model="formData.userName"
                           class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                           required />
                </div>
                <div class="mb-4">
                    <label for="email" class="block text-sm font-medium text-gray-700">邮箱</label>
                    <input type="email" id="email" v-model="formData.email"
                           class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                           required />
                    <p v-if="emailError" class="text-red-500 text-xs italic">{{ emailError }}</p>
                </div>
                <div class="mb-4">
                    <label for="address" class="block text-sm font-medium text-gray-700">地址</label>
                    <select id="address" v-model="formData.address"
                            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            required>
                        <option disabled value="">请选择地址</option>
                        <option v-for="(address, index) in addressOptions" :key="index" :value="address">{{ address }}</option>
                    </select>
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-sm font-medium text-gray-700">描述</label>
                    <textarea id="description" v-model="formData.description"
                              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                              rows="4" required></textarea>
                </div>
                <div class="mb-4">
                    <label for="role" class="block text-sm font-medium text-gray-700">角色</label>
                    <select id="role" v-model="formData.type"
                            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            required>
                        <option disabled value="">请选择角色</option>
                        <option value="1">管理员</option>
                        <option value="0">普通用户</option>
                    </select>
                </div>
                <div class="mb-4">
                    <label class="block text-sm font-medium text-gray-700">头像</label>
                    <div v-if="!formData.imagePreview" class="mt-2">
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
import { ref, watch, computed } from 'vue'
import axios from 'axios'

const props = defineProps({
    modelValue: Boolean,
    user: Object,
})

const emit = defineEmits(['update:modelValue', 'save'])

const show = ref(props.modelValue)
const formData = ref({
    userName: props.user?.userName || '',
    email: props.user?.email || '',
    address: props.user?.nationality || '',
    description: props.user?.description || '',
    type: props.user?.type !== undefined ? String(props.user.type) : '',
    imageFile: null,
    imagePreview: props.user?.image || ''
})

const addressOptions = [
    '北京',
    '上海',
    '广州',
    '深圳',
    '成都',
    '杭州',
    '南京',
    '武汉',
    '西安',
    '重庆',
    '苏州',
    '天津',
    '青岛',
    '宁波',
    '无锡',
    '长沙',
    '大连',
    '厦门',
    '沈阳',
    '昆明'
]

const emailError = ref('')

const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return re.test(email)
}

watch(
    () => props.modelValue,
    (newValue) => {
        show.value = newValue
        formData.value = {
            userName: props.user?.userName || '',
            email: props.user?.email || '',
            address: props.user?.nationality || '',
            description: props.user?.description || '',
            type: props.user?.type !== undefined ? String(props.user.type) : '',
            imageFile: null,
            imagePreview: props.user?.picUrl || ''
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

const saveUser = async () => {
    if (!validateEmail(formData.value.email)) {
        emailError.value = '请输入有效的邮箱地址'
        return
    }

    try {
        const formDataToSend = new FormData()
        formDataToSend.append('userId', props.user?.userId || '')
        formDataToSend.append('userName', formData.value.userName)
        formDataToSend.append('email', formData.value.email)
        formDataToSend.append('nationality', formData.value.address)
        formDataToSend.append('description', formData.value.description)
        formDataToSend.append('type', formData.value.type)
        if (formData.value.imageFile) {
            formDataToSend.append('pic', formData.value.imageFile)
        }

        const response = await axios.post('/api/user/updateUser', formDataToSend, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })

        console.log('User saved successfully:', response.data.data)
        emit('save', response.data.data)
        closeModal()
    } catch (error) {
        console.error('Error saving user:', error)
        emailError.value = '保存用户时出错，请稍后再试'
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
/* 移除原有样式，因为 Tailwind CSS 已经提供了足够的样式 */
</style>