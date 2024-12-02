<template>
  <div class="bg-white">
    <div class="ml-32 max-w-2xl px-4 py-4 sm:px-6 sm:py-4">
      <!-- 图片和文字在同一行 -->
      <div class="flex mb-8">
        <img class="inline-block h-40 w-40 rounded-md mr-4" :src="playlist.imageUrl" alt="" />
        <div>
          <h3 class="text-lg font-medium text-gray-900">{{ playlist.name }}</h3>
          <p class="mt-1 text-sm text-gray-600">{{ playlist.description }}</p>
        </div>
      </div>
      <div class="w-full">
        <span class="isolate inline-flex rounded-md shadow-sm w-full mb-0">
          <div class="flex flex-1 space-x-2">
            <button type="button" @click="handleSong"
              :class="['relative inline-flex items-center rounded-full bg-white px-4 py-2 text-sm font-semibold', selectedButton === 'song' ? 'text-green-500' : 'text-gray-900']">
              歌曲
            </button>
            <button type="button" @click="handleComment"
              :class="['relative inline-flex items-center rounded-full bg-white px-4 py-2 text-sm font-semibold', selectedButton === 'comment' ? 'text-green-500' : 'text-gray-900']">
              评论
            </button>
          </div>
        </span>
        <div>
          <div class="flow-root">
            <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
              <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
                <table class="min-w-full divide-y divide-gray-300">
                  <thead>
                    <tr>
                      <th scope="col" class="py-2 pl-4 pr-3 text-left text-xs font-semibold text-gray-500 sm:pl-3">序号</th>
                      <th scope="col" class="py-2 pl-4 pr-3 text-left text-xs font-semibold text-gray-500 sm:pl-3">歌曲信息</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">专辑</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">喜欢</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">时长</th>
                    </tr>
                  </thead>
                  <tbody class="bg-white">
                    <tr v-for="(song, index) in songs" :key="song.songId" class="even:bg-gray-50 cursor-pointer"
                      @click="handleSongClick(song)">
                      <td class="whitespace-nowrap py-2 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-3">{{ index + 1 }}</td>
                      <td class="whitespace-nowrap py-2 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-3">
                        <div class="flex items-center">
                          <img class="inline-block h-10 w-10 rounded-md mr-2" :src="song.coverPath" alt="" />
                          <div>
                            <p>{{ song.title }}</p>
                            <p class="text-gray-500">{{ song.artist }}</p>
                          </div>
                        </div>
                      </td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500">{{ song.album }}</td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 pl-2">
                        <svg xmlns="http://www.w3.org/2000/svg" :fill="true ? 'red' : 'none'" viewBox="0 0 24 24"
                          stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                          <path stroke-linecap="round" stroke-linejoin="round"
                            d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" />
                        </svg>
                      </td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500">{{ song.time }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMusicStore } from '../stores/useMusicStore';
import axios from 'axios';

const musicStore = useMusicStore();
const route = useRoute()
const router = useRouter()
const playlist = ref({})
const selectedButton = ref('song')
const playlistId = ref(route.params.id)
const songs = ref([])

const fetchPlaylistData = async () => {
  try {
    const response = await axios.get("http://localhost:8080/playList/getPlaylistDetail", {
      params: {
        playlistId: playlistId.value
      }
    });
    playlist.value = response.data.data;
    songs.value = response.data.data.songs;
  } catch (error) {
    console.error("Error fetching playlist data:", error);
  }
};

onMounted(() => {
  fetchPlaylistData();
})

watch(
  () => route.params.id,
  (newId) => {
    playlistId.value = newId;
    fetchPlaylistData();
  }
);

const handleSongClick = (song) => {
  musicStore.setCurrentMusicId(song.songId); // 设置当前音乐ID
  musicStore.setCurrentPlaylistId(playlistId.value);
  console.log(musicStore.currentMusicId);
  console.log(musicStore.currentPlaylistId);
}

const handleSong = () => {
  selectedButton.value = 'song';
  // router.push(`/home/songlistdetails/${playlistId}/songs`)
}

const handleComment = () => {
  selectedButton.value = 'comment';
  // router.push(`/home/songlistdetails/${playlistId}/comment`)
}
</script>