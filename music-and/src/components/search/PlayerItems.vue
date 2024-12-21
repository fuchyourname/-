<template>
  <div class="p-4">
    <div v-if="artists.length > 0"
      class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4 mx-auto max-w-screen-xl">
      <div v-for="artist in artists" :key="artist.artistId"
        class="bg-white rounded-lg shadow-md hover:bg-gray-100 cursor-pointer transition duration-300 ease-in-out">
        <img :src="artist.picUrl" alt="Artist Image" class="w-full h-48 object-cover">
        <div class="p-4">
          <h2 class="text-lg font-semibold text-center">{{ artist.name }}</h2>
          <p class="text-sm text-gray-600 text-center mt-1">歌曲: {{ artist.songs }} | 专辑: {{ artist.albums }}</p>
        </div>
      </div>
      <!-- 移动并添加条件判断 -->
      <div v-if="!hasMoreData" class="col-span-full text-center p-4 text-gray-500">
        <p>没有更多数据了</p>
      </div>
    </div>
    <div v-else class="text-center p-4 text-gray-500">
      <p>没有找到相关歌手</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps({
  keyword: String
});

const searchQuery = ref(props.keyword);
const currentPage = ref(1);
const pageSize = ref(10);
const artists = ref([]);

async function fetchArtists() {
  try {
    const response = await axios.post('/api/player/getPlayerPage', {
      name: searchQuery.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    });
    artists.value = response.data.data;
  } catch (error) {
    console.error('获取歌手列表失败:', error);
  }
}

onMounted(() => {
  fetchArtists();
});
</script>

<style scoped>
/* 所有自定义样式已移除，完全使用 Tailwind CSS */
</style>