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
              <input id="search" name="search"
                class="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white placeholder-gray-500 focus:outline-none focus:placeholder-gray-400 focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder="Search" v-model="searchQuery" />
            </div>
          </div>

          <!-- Dropdown -->
          <div>
            <label for="role" class="sr-only">Role</label>
            <select id="role" name="role"
              class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
              v-model="selectedType">
              <option value="">全部</option>
              <option value="1">管理员</option>
              <option value="0">用户</option>
              <!-- Add more roles as needed -->
            </select>
          </div>

          <!-- Search Button -->
          <button type="button" @click="fetchUsers"
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
        <button type="button" @click="openAddUserModal"
          class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          创建用户</button>
      </div>
    </div>
    <div class="mt-1 flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
              <tr>
                <th scope="col" class="py-3.5 pr-3 pl-4 text-left text-sm font-semibold text-gray-900 sm:pl-0">名称</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">地区</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">状态</th>
                <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">角色</th>
                <th scope="col" class="relative py-3.5 pr-4 pl-3 sm:pr-0">
                  <span class="sr-only">编辑</span>
                </th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 bg-white">
              <tr v-for="person in paginatedPeople" :key="person.userId" class="even:bg-gray-50">
                <td class="py-5 pr-3 pl-4 text-sm whitespace-nowrap sm:pl-0">
                  <div class="flex items-center">
                    <div class="size-11 shrink-0">
                      <img class="size-11" :src="person.picUrl" alt="" />
                    </div>
                    <div class="ml-4">
                      <div class="font-medium text-gray-900">{{ person.userName }}</div>
                      <div class="mt-1 text-gray-500">{{ person.email }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                  <div class="text-gray-900">{{ person.nationality }}</div>
                  <div class="mt-1 text-gray-500">{{ person.description }}</div>
                </td>
                <!-- <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                  <span
                    class="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-green-700 ring-1 ring-green-600/20 ring-inset">{{ person.status == 1 ? 'Active' : '' }}</span>
                </td> -->
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">
                <span
                  :class="[person.status === 1 ? 'bg-green-50 text-green-700 ring-green-600/20' : 'bg-red-50 text-red-700 ring-red-600/20', 'inline-flex items-center rounded-md px-2 py-1 text-xs font-medium ring-1 ring-inset']">
                  {{ person.status == 1 ? 'active' : 'inactive' }}
                </span>
              </td>
                <td class="px-3 py-5 text-sm whitespace-nowrap text-gray-500">{{ person.type == 1 ? "administer" : 'user' }}</td>
                <td class="relative py-5 pr-4 pl-3 text-right text-sm font-medium whitespace-nowrap sm:pr-0">
                  <a href="#" @click.prevent="openEditUserModal(person)"
                    class="text-indigo-600 hover:text-indigo-900">编辑<span class="sr-only">, {{ person.name
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
            <span class="font-medium">{{ Math.min(currentPage * pageSize, people.length) }}</span>
            of
            <span class="font-medium">{{ people.length }}</span>
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
    <!-- User Modal -->
    <UserModal v-model="isModalOpen" :user="editingUser" @save="handleSaveUser" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios' // 导入 Axios
import UserModal from '../../components/admin/UserModal.vue'

const people = ref([])
const currentPage = ref(1)
const pageSize = 5
const searchQuery = ref('') // 搜索输入框的内容
const selectedType = ref('') // 下拉菜单的选择

const totalPages = computed(() => Math.ceil(people.value.length / pageSize))
const paginatedPeople = computed(() =>
  people.value.slice((currentPage.value - 1) * pageSize, currentPage.value * pageSize)
)

const isModalOpen = ref(false)
const editingUser = ref({})

const openAddUserModal = () => {
  editingUser.value = {}
  isModalOpen.value = true
}

const openEditUserModal = (user) => {
  editingUser.value = { ...user }
  isModalOpen.value = true
}

const handleSaveUser = (user) => {
  if (user.userId) {
    const index = people.value.findIndex((p) => p.userId === user.userId)
    if (index !== -1) {
      // Edit existing user
      people.value[index] = user
    } else {
      // Add new user
      people.value.push(user)
    }
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

// 定义 PageInfo 对象
const pageInfo = computed(() => ({
  name: searchQuery.value,
  type: selectedType.value,
  pageNum: currentPage.value,
  pageSize: pageSize
}))

// 页面加载时发送 POST 请求
onMounted(() => {
  fetchUsers()
})

// 定义搜索方法
const fetchUsers = async () => {
  try {
    const response = await axios.post('/api/user/getUserPage', pageInfo.value)
    // 假设后端返回的数据在 response.data 中
    people.value = response.data.data
  } catch (error) {
    console.error('Error fetching user list:', error)
  }
}
</script>