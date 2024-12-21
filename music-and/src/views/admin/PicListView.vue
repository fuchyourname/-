<!-- src/views/admin/PicListView.vue -->
<template>
  <div class="px-4 sm:px-6 lg:px-8">
    <div class="sm:flex sm:items-center">
      <div class="sm:flex-auto">
        <div class="flex space-x-4">
          <!-- Dropdown -->
          <div>
            <label for="type" class="sr-only">Type</label>
            <select id="type" name="type"
                    class="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    v-model="filterType">
              <option value="1">歌曲</option>
              <option value="2">歌手</option>
              <option value="3">歌单</option>
              <option value="4">专辑</option>
              <option value="5">用户</option>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-1">
      <div v-if="filteredPictures.length > 0" class="grid grid-cols-5 gap-4">
        <div v-for="picture in filteredPictures" :key="picture" class="bg-white p-4 rounded-lg shadow-md">
          <img :src="picture" alt="" class="w-full h-48 object-cover rounded-lg transition-transform duration-300 hover:scale-110" />
        </div>
      </div>
      <div v-else class="text-center mt-8">
        <p class="text-gray-500 text-lg">暂无数据</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import axios from 'axios'

const pictures = ref([])
const filterType = ref('3') // 默认选择歌单

const filteredPictures = computed(() => {
  return pictures.value
})

const fetchPictures = async () => {
  try {
    const response = await axios.post('/api/index/getImgList', {
      type: parseInt(filterType.value),
    })
    pictures.value = response.data.data
  } catch (error) {
    console.error('Error fetching pictures:', error)
  }
}

onMounted(() => {
  fetchPictures()
})

watch(filterType, (newVal, oldVal) => {
  console.log('filterType changed from', oldVal, 'to', newVal) // 添加调试信息
  fetchPictures()
})
</script>

<style scoped>
/* Add any additional styles here */
</style>