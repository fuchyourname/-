<template>
  <main class="relative isolate min-h-full bg-gray-100 p-4 sm:p-6 lg:p-8">
    <div class="ml-32 max-w-7xl mx-auto mb-32">
      <h1 class="text-balance text-xl font-semibold tracking-tight text-gray-900">上传音乐单曲</h1>
      <p class="mt-2 text-sm font-medium text-gray-600">上传你的音乐单曲到我们的平台</p>

      <!-- 网盘容量和进度条 -->
      <div class="mt-6">
        <div class="flex items-center">
          <p class="text-sm text-gray-700">网盘容量: </p>
          <div class="max-w-md w-full bg-gray-200 rounded-full h-1.5 dark:bg-gray-700 mx-2">
            <div class="bg-blue-600 h-1.5 rounded-full" :style="{ width: spaceUsagePercentage + '%' }"></div>
          </div>
          <p class="text-sm text-gray-700">{{ usedSpace }} / {{ totalSpace }}</p>
        </div>
      </div>

      <!-- 上传按钮 -->
      <div class="mt-6 w-28">
        <label for="file-upload"
               :class="['relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500 flex items-center px-4 py-2', {'opacity-50 cursor-not-allowed': isUploadDisabled || isUploading}]"
               :disabled="isUploadDisabled || isUploading">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
               stroke="currentColor" class="size-5"> <!-- 减小SVG图标大小 -->
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z"/>
          </svg>
          <span class="ml-2 text-green-500">{{ isUploading ? '上传中...' : '上传' }}</span> <!-- 增加间距 -->
          <input id="file-upload" type="file" class="sr-only" @change="handleFileUpload" accept="audio/*" :disabled="isUploadDisabled || isUploading"/>
        </label>
      </div>

      <!-- 上传的单曲列表 -->
      <div class="mt-6">
        <h2 class="text-xl font-semibold text-gray-900">上传的单曲</h2>
        <div v-if="uploadedSongs.length === 0" class="mt-4 text-gray-600">暂无上传的单曲</div>
        <table v-else class="mt-4 divide-y divide-gray-200 w-2/3">
          <thead class="bg-gray-50">
          <tr>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">#</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">标题</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">专辑</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">格式</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">上传时间</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">大小</th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
          </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="(song, index) in uploadedSongs" :key="index">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ index + 1 }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="flex items-center">
                <img class="h-10 w-10 flex-shrink-0 bg-gray-300" :src="song.picUrl" alt=""/>
                <div class="ml-4">
                  <div class="text-sm font-medium text-gray-900">{{ song.name }}</div>
                  <div class="text-sm text-gray-500">{{ song.singer }}</div>
                </div>
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.album }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.type }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ formateDate(song.createTime) }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.size  + "MB" }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              <button @click="downloadSong(song.userSongsId)" class="text-blue-500 hover:text-blue-700">下载</button>
            </td>
          </tr>
          </tbody>
        </table>
        <div v-if="loadingMore" class="text-center mt-4">加载中...</div>
        <div v-if="noMoreData" class="text-center mt-4">没有更多数据了</div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const usedSpace = ref('0 GB');
const totalSpace = ref('6 GB');
const spaceUsagePercentage = ref(0);
const uploadedSongs = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const loadingMore = ref(false);
const noMoreData = ref(false);
const isUploading = ref(false);

// 计算属性判断是否禁用上传按钮
const isUploadDisabled = computed(() => {
  return parseFloat(usedSpace.value.replace(/[^0-9.]/g, '')) >= parseFloat(totalSpace.value.replace(/[^0-9.]/g, ''));
});

onMounted(() => {
  fetchUploadedSongs();
});

const formateDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString();
};

const fetchUploadedSongs = async () => {
  try {
    loadingMore.value = true;
    const response = await axios.post('/api/user/getUserSongs', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
    });
    const data = response.data;

    if (data.data) {
      uploadedSongs.value.push(...data.data);
      currentPage.value++;
    } else {
      noMoreData.value = true;
    }

    // 更新网盘使用情况
    usedSpace.value = data.total + " GB";
    totalSpace.value = "6 GB";
    spaceUsagePercentage.value = usedSpace.value / totalSpace.value * 100;
  } catch (error) {
    console.error("Failed to fetch uploaded songs:", error);
  } finally {
    loadingMore.value = false;
  }
};

const handleScroll = () => {
  const scrollPosition = window.scrollY + window.innerHeight;
  const documentHeight = document.documentElement.scrollHeight;

  if (scrollPosition >= documentHeight - 10 && !loadingMore.value && !noMoreData.value) {
    fetchUploadedSongs();
  }
};

window.addEventListener('scroll', handleScroll);

const handleFileUpload = async (event) => {
  if (isUploadDisabled.value) {
    alert('网盘空间不足，无法上传文件。');
    return;
  }

  const file = event.target.files[0];
  if (file) {
    const formData = new FormData();
    formData.append('file', file);

    try {
      isUploading.value = true;
      const response = await axios.post('/api/user/uploadSong', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      if (response.status === 200) {
        // 更新网盘使用情况（假设后端返回了最新的使用情况）
        usedSpace.value = response.data.usedSpace;
        spaceUsagePercentage.value = response.data.spaceUsagePercentage;

        // 刷新列表
        uploadedSongs.value = [];
        currentPage.value = 1;
        noMoreData.value = false;
        fetchUploadedSongs();
      }
    } catch (error) {
      console.error("Failed to upload file:", error);
    } finally {
      isUploading.value = false;
    }
  }
};

const downloadSong = async (songId) => {
  try {
    const response = await axios.get(`/api/user/download/${songId}`, {
      responseType: 'json', // 设置为json
    });

    const base64Content = response.data.data.file;
    const byteCharacters = atob(base64Content);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: response.data.data.fileType });

    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `${response.data.data.fileName}.${response.data.data.fileType}`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error("Failed to download song:", error);
  }
};

</script>

<style scoped>
/* 添加一些样式以美化界面 */
</style>