<template>
  <main class="relative isolate min-h-full bg-gray-100 p-4 sm:p-6 lg:p-8"> <!-- 减少整体左侧间距 -->
    <div class="ml-32 max-w-7xl mx-auto">
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
          class="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500 flex items-center px-4 py-2">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
            stroke="currentColor" class="size-5"> <!-- 减小SVG图标大小 -->
            <path stroke-linecap="round" stroke-linejoin="round"
              d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z" />
          </svg>
          <span class="ml-2 text-green-500">上传</span> <!-- 增加间距 -->
          <input id="file-upload" type="file" class="sr-only" @change="handleFileUpload" accept="audio/*" />
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
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(song, index) in uploadedSongs" :key="index">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ index + 1 }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="flex items-center">
                  <img class="h-10 w-10 flex-shrink-0 bg-gray-300" :src="song.image" alt="" />
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">{{ song.title }}</div>
                    <div class="text-sm text-gray-500">{{ song.artist }}</div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.album }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.format }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.uploadTime }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ song.size }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <footer>
    <div class="mx-auto max-w-7xl px-6 py-12 md:flex md:items-center md:justify-between lg:px-8">
      <p class="text-center text-sm/6 text-gray-600 md:order-1 md:mt-0">&copy; 2024 Your Company, Inc. All rights reserved.</p>
    </div>
  </footer>
  </main>
</template>

<script setup>
import { ref } from 'vue';

const usedSpace = ref('20 GB');
const totalSpace = ref('100 GB');
const spaceUsagePercentage = ref(20);
const uploadedSongs = ref([
  {
    image: 'https://via.placeholder.com/100',
    title: '歌曲标题',
    artist: '未知艺术家',
    album: '未知专辑',
    format: 'mp3',
    uploadTime: '2023-04-15',
    size: '10 MB',
  },
  {
    image: 'https://via.placeholder.com/100',
    title: '歌曲标题2',
    artist: '未知艺术家2',
    album: '未知专辑2',
    format: 'wav',
    uploadTime: '2023-04-16',
    size: '15 MB',
  },
  {
    image: 'https://via.placeholder.com/100',
    title: '歌曲标题2',
    artist: '未知艺术家2',
    album: '未知专辑2',
    format: 'wav',
    uploadTime: '2023-04-16',
    size: '15 MB',
  },
  {
    image: 'https://via.placeholder.com/100',
    title: '歌曲标题2',
    artist: '未知艺术家2',
    album: '未知专辑2',
    format: 'wav',
    uploadTime: '2023-04-16',
    size: '15 MB',
  },
  {
    image: 'https://via.placeholder.com/100',
    title: '歌曲标题2',
    artist: '未知艺术家2',
    album: '未知专辑2',
    format: 'wav',
    uploadTime: '2023-04-16',
    size: '15 MB',
  },
]);

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const newSong = {
      image: 'https://via.placeholder.com/100', // 示例图片
      title: file.name,
      artist: '未知艺术家',
      album: '未知专辑',
      format: file.type,
      uploadTime: new Date().toLocaleString(),
      size: `${(file.size / (1024 * 1024)).toFixed(2)} MB`,
    };
    uploadedSongs.value.push(newSong);
    // 更新网盘使用情况
    usedSpace.value = '51 GB'; // 示例更新
    spaceUsagePercentage.value = 51; // 示例更新
  }
};
</script>

<style scoped>
/* 添加一些样式以美化界面 */
</style>