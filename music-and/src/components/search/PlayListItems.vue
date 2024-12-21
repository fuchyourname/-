<template>
  <div v-if="playlists.length > 0" class="p-4 ml-32">
    <div class="max-w-4/5 mx-auto">
      <table class="w-full border-collapse table-fixed">
        <thead>
          <tr class="bg-gray-100 text-left">
            <th class="w-1/6 p-2">封面</th>
            <th class="w-1/4 p-2">名称</th>
            <th class="w-1/6 p-2">创建者</th>
            <th class="w-1/6 p-2">播放次数</th>
            <th class="w-1/6 p-2">歌曲数量</th>
          </tr>
        </thead>
        <tbody>
          <tr 
            v-for="(playlist, index) in playlists" 
            :key="playlist.playlistId"
            @mouseover="hoveredPlaylist = playlist.playlistId"
            @mouseleave="hoveredPlaylist = null"
            @click="goToPlaylist(playlist.playlistId)"
            :class="{ 'bg-gray-100': hoveredPlaylist === playlist.playlistId }"
            class="border-b border-gray-200 hover:bg-gray-50 cursor-pointer"
          >
            <td class="w-1/3 p-2 flex items-center">
              <img :src="playlist.imageUrl" alt="Playlist Cover" class="w-12 h-12 mr-4">
            </td>
            <td class="w-1/4 p-2">
              <h2 class="text-lg font-semibold">{{ playlist.name }}</h2>
            </td>
            <td class="w-1/6 p-2">
              <p class="text-gray-600">{{ playlist.userName }}</p>
            </td>
            <td class="w-1/6 p-2">
              <p class="text-gray-600">{{ playlist.playCount }} 次播放</p>
            </td>
            <td class="w-1/6 p-2">
              <p class="text-gray-600">{{ playlist.songCount }} 首</p>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 将“没有更多数据了”提示移动到这里 -->
      <div v-if="playlists.length > 0 && !hasMoreData" class="text-center p-4 text-gray-500">
        <p>没有更多数据了</p>
      </div>
    </div>
  </div>

  <div v-else class="flex flex-col items-center justify-center p-8 text-gray-500 space-y-4">
    <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 17V7m0 10a2 2 0 01-2 2H5a2 2 0 01-2-2V7a2 2 0 012-2h2a2 2 0 012 2v10zm4 0a2 2 0 012 2h2a2 2 0 012-2V7a2 2 0 01-2-2h-2a2 2 0 01-2 2v10z"/>
    </svg>
    <p class="text-lg">没有找到相关歌单</p>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const props = defineProps({
  keyword: String,
  currentTab: String
});

const searchQuery = ref(props.keyword);
const currentPage = ref(1);
const pageSize = ref(10);

const playlists = ref([]);
const hoveredPlaylist = ref(null);

async function fetchPlaylists() {
  try {
    const response = await axios.post('/api/playList/getPlayListPage', {
      name: searchQuery.value,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    });
    playlists.value = response.data.data;
  } catch (error) {
    console.error('获取歌单列表失败:', error);
  }
}

function goToPlaylist(playlistId) {
  router.push({ path: `/home/songlistdetails/${playlistId}` });
}

onMounted(() => {
  fetchPlaylists();
});
</script>
<style scoped>
/* 移除所有边框样式 */
table {
  width: 100%;
}

th, td {
  padding: 8px;
}
</style>