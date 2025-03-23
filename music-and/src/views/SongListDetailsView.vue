<template>
  <div class="bg-white">
    <div class="px-4 py-4 sm:px-6 sm:py-4 mb-24">
      <!-- 图片和文字在同一行 -->
      <div class="flex mb-8">
        <img class="inline-block h-40 w-40 rounded-md mr-4" :src="playlist.imageUrl" alt="" />
        <div>
          <h3 class="text-lg font-medium text-gray-900">{{ playlist.name }}</h3>
          <p class="mt-1 text-sm text-gray-600">{{ playlist.description }}</p>
          <button type="button"  @click="toggleFavorite"  class="w-28 mt-20 rounded-full bg-white px-3.5 py-2 text-sm font-semibold text-gray-900 ring-1 shadow-xs ring-gray-300 ring-inset hover:bg-gray-50">
            {{ isFavorited ? '已收藏' : '+ 收藏' }}</button>
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
                <table v-if="songs.length > 0" class="min-w-full divide-y divide-gray-300">
                  <thead>
                    <tr>
                      <th scope="col"
                        class="py-2 pl-4 pr-3 text-left text-xs font-semibold text-gray-500 sm:pl-3 w-1/3">歌曲信息</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500 w-1/6">喜欢</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500 w-1/10"></th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500 w-1/4">专辑</th>
                      <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500 w-1/6">时长</th>
                    </tr>
                  </thead>
                  <tbody class="bg-white">
                    <tr v-for="(song, index) in songs" :key="song.songId" class="even:bg-gray-50 cursor-pointer"
                      @mouseenter="hoveredIndex = index" @mouseleave="hoveredIndex = -1" @click="handleSongClick(song)"
                      :class="{ 'bg-gray-100': hoveredIndex === index }">
                      <td class="whitespace-nowrap py-2 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-3">
                        <div class="flex items-center">
                          <img class="inline-block h-10 w-10 rounded-md mr-2" :src="song.coverPath" alt="" />
                          <div>
                            <p>{{ song.title }}</p>
                            <p class="text-gray-500">{{ song.artist }}</p>
                          </div>
                        </div>
                      </td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 pl-2 w-1/6">
                        <!-- 点赞按钮 -->
                        <button @click.stop="toggleLike(index)" class="flex items-center">
                          <svg xmlns="http://www.w3.org/2000/svg" :fill="song.isLiked ? 'red' : 'none'"
                            viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                              d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" />
                          </svg>
                        </button>
                      </td>
                      <!-- 在歌曲列表的图标部分绑定点击事件以打开模态框 -->
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 relative w-1/10 text-left">
                        <svg v-if="hoveredIndex === index" xmlns="http://www.w3.org/2000/svg" fill="none"
                          viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 cursor-pointer"
                          @click.stop="openModal(song)">
                          <path stroke-linecap="round" stroke-linejoin="round"
                            d="M12 10.5v6m3-3H9m4.06-7.19-2.12-2.12a1.5 1.5 0 0 0-1.061-.44H4.5A2.25 2.25 0 0 0 2.25 6v12a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 18V9a2.25 2.25 0 0 0-2.25-2.25h-5.379a1.5 1.5 0 0 1-1.06-.44Z" />
                        </svg>
                      </td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 relative w-1/4">
                        {{ song.album }}
                      </td>
                      <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 w-1/6">{{ song.duration }}</td>
                    </tr>
                  </tbody>
                </table>
                <!-- 没有数据时的提示 -->
                <div v-else class="text-center py-8 text-gray-500">
                  <p>暂无歌曲数据</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AddToPlaylistModal v-if="showModal" :song="selectedSong" @close-modal="showModal = false"
    @add-to-playlist="addToPlaylist" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMusicStore } from '../stores/useMusicStore';
import AddToPlaylistModal from '../components/AddToPlaylistModal.vue'
import axios from 'axios';

const musicStore = useMusicStore();
const route = useRoute()
const router = useRouter()
const playlist = ref({})
const selectedButton = ref('song')
const playlistId = ref(route.params.id)
const songs = ref([])
const hoveredIndex = ref(-1)
const isFavorited = ref(false)

// 引入新的响应式变量
const showModal = ref(false);
const selectedSong = ref(null);

// 定义打开模态框的方法
const openModal = (song) => {
  selectedSong.value = song;
  showModal.value = true;
};

// 定义添加到歌单的方法
const addToPlaylist = async (playlistId) => {
  try {
    await axios.post("/api/playList/addSong", null, {
      params: {
        songId: selectedSong.value.songId,
        playlistId: playlistId
      }
    });
    console.log("歌曲已成功添加到歌单");
    showModal.value = false; // 关闭模态框
  } catch (error) {
    console.error("Error adding song to playlist:", error);
  }
};
const fetchPlaylistData = async () => {
  // 清空歌曲列表
  songs.value = [];
  try {
    const response = await axios.get("/api/playList/getPlaylistDetail", {
      params: {
        playlistId: playlistId.value
      }
    });
    playlist.value = response.data.data;
    // 检查是否已收藏
    checkIfFavorited();
    songs.value = response.data.data.songs.map(song => ({
      ...song,
      isLiked: false // 初始化点赞状态
    }));
    // 检查每首歌的点赞状态
    checkLikes();
  } catch (error) {
    console.error("Error fetching playlist data:", error);
  }
};

const checkIfFavorited = async () => {
  try {
    const response = await axios.get("/api/playList/isFavorite", {
      params: {
        playlistId: playlistId.value
      }
    });
    isFavorited.value = response.data.data;
  } catch (error) {
    console.error("Error checking if favorited:", error);
  }
};

const checkLikes = async () => {
  try {
    const response = await axios.get("/api/playList/getFavoriteSongs", {
      params: {
        playlistId: playlistId.value
      }
    });
    const likedSongs = response.data.data || [];
    songs.value.forEach((song, index) => {
      song.isLiked = likedSongs.includes(song.songId);
    });
  } catch (error) {
    console.error("Error checking likes:", error);
  }
};

const toggleFavorite = async () => {
  try {
    if (isFavorited.value) {
      await axios.get("/api/playList/cancelFavorite", {
        params: {
          playlistId: playlistId.value
        }
      });
    } else {
      await axios.get("/api/playList/favorite", {
        params: {
          playlistId: playlistId.value
        }
      });
    }
    isFavorited.value = !isFavorited.value;
  } catch (error) {
    console.error("Error toggling favorite status:", error);
  }
};

const toggleLike = async (index) => {
  const song = songs.value[index];
  try {
    if (song.isLiked) {
      await axios.get("/api/music/unlike", {
        params: {
          songId: song.songId
        }
      });
    } else {
      await axios.get("/api/music/like", {
        params: {
          songId: song.songId
        }
      });
    }
    song.isLiked = !song.isLiked;
  } catch (error) {
    console.error("Error toggling like status:", error);
  }
};

onMounted(() => {
  fetchPlaylistData();
});

watch(
  () => route.params.id,
  (newId) => {
    playlistId.value = newId;
    fetchPlaylistData();
  }
);

const handleSongClick = (song) => {
  musicStore.setCurrentPlaylist(songs.value);
  musicStore.setCurrentMusic(song);
  musicStore.setCurrentPlaylistId(playlistId.value);
};

const handleSong = () => {
  selectedButton.value = 'song';
  // router.push(`/home/songlistdetails/${playlistId}/songs`)
};

const handleComment = () => {
  selectedButton.value = 'comment';
  // router.push(`/home/songlistdetails/${playlistId}/comment`)
};
</script>

<style scoped>
/* 去掉之前的宽度设置 */
/* 增加自定义宽度 */
table th,
table td {
  padding: 8px;
}

table th:first-child,
table td:first-child {
  width: 33.33%;
}

table th:nth-child(2),
table td:nth-child(2) {
  width: 10%;
}

table th:nth-child(3),
table td:nth-child(3) {
  width: 10%;
}

table th:nth-child(4),
table td:nth-child(4) {
  width: 25%;
}

table th:last-child,
table td:last-child {
  width: 16.67%;
}

/* 鼠标悬停时改变背景颜色 */
tr:hover {
  background-color: #f1f5f9;
}
</style>