<template>
  <div class="p-4 ml-32">
    <table class="w-full table-auto border-collapse">
      <thead>
        <tr>
          <th class="px-4 py-2"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
          <th class="px-4 py-2 text-left text-lg font-bold"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="album in albums" :key="album.albumId">
          <td class="px-4 py-2">
            <img :src="album.coverImage" alt="Album Cover" class="w-12 h-12 rounded">
          </td>
          <td class="px-4 py-2">
            <h2 class="text-lg font-semibold">{{ album.title }}</h2>
          </td>
          <td class="px-4 py-2">
            <p class="text-gray-600">{{ album.artist }}</p>
          </td>
          <td class="px-4 py-2">
            <p class="text-gray-600">{{ formatDate(album.createTime) }}</p>
          </td>
          <td class="px-4 py-2">
            <p class="text-gray-600">{{ album.songCount }} 首歌曲</p>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="albums.length === 0" class="text-center p-4 text-gray-500">
      <p>没有找到相关专辑</p>
    </div>
    <div v-if="albums.length > 0 && !hasMoreData" class="text-center p-4 text-gray-500">
      <p>没有更多数据了</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import dayjs from 'dayjs'; // 使用 dayjs 来格式化日期

const props = defineProps({
  keyword: String
});

const searchQuery = ref(props.keyword);

const currentPage = ref(1);
const pageSize = ref(10);
const albums = ref([]);
const hasMoreData = ref(true); // 假设有更多数据

async function fetchAlbums() {
  try {
    const response = await axios.post('/api/album/getAlbumPage', {
      name: searchQuery.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    });
    albums.value = response.data.data;
    // 假设响应中有 hasMore 字段来判断是否有更多数据
    hasMoreData.value = response.data.hasMore || false;
  } catch (error) {
    console.error('获取专辑列表失败:', error);
  }
}

function formatDate(date) {
  return dayjs(date).format('YYYY-MM-DD');
}

onMounted(() => {
  fetchAlbums();
});
</script>

<style scoped>
table {
  border-collapse: collapse; /* 合并边框 */
}
td, th {
  border: none; /* 移除边框 */
}
</style>