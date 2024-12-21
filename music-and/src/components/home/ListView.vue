<template>
  <div class="container mx-auto p-4">
    <div class="flex justify-between">
      <!-- 热歌榜 -->
      <section class="mb-12 w-full md:w-1/2 lg:w-1/2 xl:w-1/2 pr-4">
        <div class="rank-list bg-hot-songs rounded-lg shadow-md p-4">
          <div class="flex justify-between items-center">
            <h2 class="text-2xl font-bold mb-4 text-white">热歌榜</h2>
            <router-link to="/hot-songs" class="text-blue-300 hover:text-blue-500">更多</router-link>
          </div>
          <ul>
            <li v-for="(song, index) in hotSongs.slice(0, 3)" :key="index" class="mb-4">
              <div class="flex items-start">
                <span class="mr-4 text-gray-300">{{ index + 1 }}.</span>
                <div>
                  <h3 class="text-xl font-semibold text-white">{{ song.title }}</h3>
                  <p class="text-gray-300">{{ song.artist }}</p>
                </div>
              </div>

            </li>
          </ul>
        </div>
      </section>

      <!-- 流行指数榜 -->
      <section class="mb-12 w-full md:w-1/2 lg:w-1/2 xl:w-1/2 pl-4">
        <div class="rank-list bg-popular-songs rounded-lg shadow-md p-4">
          <div class="flex justify-between items-center">
            <h2 class="text-2xl font-bold mb-4 text-white">新歌榜</h2>
            <router-link to="/popular-songs" class="text-blue-300 hover:text-blue-500">更多</router-link>
          </div>
          <ul>
            <li v-for="(song, index) in popularSongs.slice(0, 3)" :key="index" class="mb-4">
              <div class="flex items-start">
                <span class="mr-4 text-gray-300">{{ index + 1 }}.</span>
                <div>
                  <h3 class="text-xl font-semibold text-white">{{ song.title }}</h3>
                  <p class="text-gray-300">{{ song.artist }}</p>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const hotSongs = ref([]);
const popularSongs = ref([]);

onMounted(async () => {
  try {
    const [hotResponse, popularResponse] = await Promise.all([
      axios.get('/api/music/hot'),
      axios.get('/api/music/new')
    ]);

    hotSongs.value = hotResponse.data.data;
    popularSongs.value = popularResponse.data.data;
  } catch (error) {
    console.error("Failed to fetch songs:", error);
  }
});

// 处理“更多”按钮的点击事件
const handleMoreClick = (path) => {
  router.push(path);
};
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.flex.justify-between {
  display: flex;
  justify-content: space-between;
}

.rank-list {
  position: relative;
  overflow: hidden;
}

.bg-hot-songs {
  background-color: #ff6b6b; /* 自定义背景颜色 */
}

.bg-popular-songs {
  background-color: #6c5ce7; /* 自定义背景颜色 */
}

.text-white {
  color: white;
}

.text-gray-300 {
  color: #a0aec0;
}

.text-gray-400 {
  color: #cbd5e0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: flex;
  flex-direction: column;
}

.flex.items-start {
  align-items: flex-start;
}

.w-16.h-16.object-cover.rounded.mr-4 {
  width: 4rem;
  height: 4rem;
  object-fit: cover;
  border-radius: 0.25rem;
  margin-right: 1rem;
}

@media (max-width: 768px) {
  .md\:w-1\/2 {
    width: 100%;
  }
}

@media (min-width: 769px) and (max-width: 1024px) {
  .lg\:w-1\/2 {
    width: 100%;
  }
}

@media (min-width: 1025px) {
  .xl\:w-1\/2 {
    width: 48%; /* Adjusted for spacing */
  }
}
</style>