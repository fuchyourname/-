<template>
  <div class="fixed bottom-0 left-0 right-0 p-4 bg-white shadow-md flex flex-row z-50">
    <div class="flex items-center">
      <img :src="currentSong.coverPath" class="w-12 h-12 rounded-lg mr-4 z-10" @click="showLyrics">
      <div>
        <p class="text-gray-800 font-semibold">{{ currentSong.title }}</p>
        <p class="text-gray-600 text-sm">{{ currentSong.artist }}</p>
      </div>
    </div>
    <div class="fixed left-0 right-0 bottom-4 flex justify-center items-center">
      <div class="flex flex-col items-center">
        <div class="flex items-center">
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none" @click="prevSong">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path
                d="M9.195 18.44c1.25.714 2.805-.189 2.805-1.629v-2.34l6.945 3.968c1.25.715 2.805-.188 2.805-1.628V8.69c0-1.44-1.555-2.343-2.805-1.628L12 11.029v-2.34c0-1.44-1.555-2.343-2.805-1.628l-7.108 4.061c-1.26.72-1.26 2.536 0 3.256l7.108 4.061Z" />
            </svg>
          </button>
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none mx-4"
            @click="playSong(currentSong.songId)">
            <svg v-if="!isPlaying" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
              class="size-6">
              <path fill-rule="evenodd"
                d="M4.5 5.653c0-1.427 1.529-2.33 2.779-1.643l11.54 6.347c1.295.712 1.295 2.573 0 3.286L7.28 19.99c-1.25.687-2.779-.217-2.779-1.643V5.653Z"
                clip-rule="evenodd" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path fill-rule="evenodd"
                d="M6.75 5.25a.75.75 0 0 1 .75-.75H9a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H7.5a.75.75 0 0 1-.75-.75V5.25Zm7.5 0A.75.75 0 0 1 15 4.5h1.5a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H15a.75.75 0 0 1-.75-.75V5.25Z"
                clip-rule="evenodd" />
            </svg>
          </button>
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none" @click="nextSong">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path
                d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
            </svg>
          </button>
        </div>
        <div class="flex items-center mt-2">
          <span class="text-gray-600 text-sm mr-2">{{ currentTimeFormatted }}</span>
          <input type="range" min="0" max="100" v-model="currentTime"
            class="custom-range w-80 h-1 bg-gray-200 rounded-full outline-none appearance-none cursor-pointer">
          <span class="text-gray-600 text-sm ml-2">{{ totalDurationFormatted }}</span>
        </div>
      </div>
    </div>
    <div class="ml-auto mt-4 z-50">
      <button @click="open = true">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
          class="size-6 w-7 h-7">
          <path stroke-linecap="round" stroke-linejoin="round"
            d="M3 4.5h14.25M3 9h9.75M3 13.5h9.75m4.5-4.5v12m0 0-3.75-3.75M17.25 21 21 17.25" />
        </svg>
      </button>
    </div>
  </div>
  <audio ref="audioPlayer" :src="currentSong.filePath" @timeupdate="updateTime" @loadedmetadata="loadMetadata"></audio>
  <TransitionRoot as="template" :show="open">
    <Dialog class="relative z-10" @close="open = false">
      <div class="fixed inset-0" />
      <div class="fixed inset-0 overflow-hidden">
        <div class="absolute inset-0 overflow-hidden">
          <div class="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
            <TransitionChild as="template" enter="transform transition ease-in-out duration-500 sm:duration-700"
              enter-from="translate-x-full" enter-to="translate-x-0"
              leave="transform transition ease-in-out duration-500 sm:duration-700" leave-from="translate-x-0"
              leave-to="translate-x-full">
              <DialogPanel class="pointer-events-auto w-screen max-w-md">
                <div class="flex h-full flex-col overflow-y-scroll bg-white py-6 shadow-xl">
                  <div class="px-4 sm:px-6">
                    <div class="flex items-start justify-between">
                      <DialogTitle class="text-base font-semibold text-gray-900">播放队列</DialogTitle>
                      <div class="ml-3 flex h-7 items-center">
                        <button type="button"
                          class="relative rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                          @click="open = false">
                          <span class="absolute -inset-2.5" />
                          <span class="sr-only">Close panel</span>
                          <XMarkIcon class="size-6" aria-hidden="true" />
                        </button>
                      </div>
                    </div>
                  </div>
                  <div class="relative mt-6 flex-1 px-4 sm:px-6">
                    <ul>
                      <li v-for="song in songs" :key="song.songId"
                        class="py-2 border-b last:border-b-0 flex items-center"
                        :class="{ 'bg-gray-100': song.songId === currentMusicId }">
                        <img :src="song.coverPath" class="w-10 h-10 rounded-lg mr-4" alt="Song Cover">
                        <div>
                          <p class="text-gray-800 font-semibold">{{ song.title }}</p>
                          <p class="text-gray-600 text-sm">{{ song.artist }}</p>
                        </div>
                        <button class="text-gray-600 hover:text-gray-800 focus:outline-none ml-auto"
                          @click="playSong(song.songId)">
                          <svg v-if="!(song.songId === currentMusicId && isPlaying)" xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path fill-rule="evenodd"
                              d="M4.5 5.653c0-1.427 1.529-2.33 2.779-1.643l11.54 6.347c1.295.712 1.295 2.573 0 3.286L7.28 19.99c-1.25.687-2.779-.217-2.779-1.643V5.653Z"
                              clip-rule="evenodd" />
                          </svg>
                          <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
                            class="size-6">
                            <path fill-rule="evenodd"
                              d="M6.75 5.25a.75.75 0 0 1 .75-.75H9a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H7.5a.75.75 0 0 1-.75-.75V5.25Zm7.5 0A.75.75 0 0 1 15 4.5h1.5a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H15a.75.75 0 0 1-.75-.75V5.25Z"
                              clip-rule="evenodd" />
                          </svg>
                        </button>
                      </li>
                    </ul>
                  </div>
                </div>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>

  <!-- 遮罩层 -->
  <div v-if="showLyricsOverlay" class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 z-49 lyrics-overlay">
    <div class="p-4 rounded-lg relative flex flex-row">
      <img :src="currentSong.coverPath" class="ml-32 mt-24 w-1/4 h-1/4 mb-4 rounded-lg">
      <div class="ml-80 flex flex-col text-center mt-20">
        <div class="mb-4">
          <p class="text-xl font-bold text-white">{{ currentSong.title }}</p>
          <p class="text-lg text-white">{{ currentSong.artist }}</p>
        </div>
        <div class="lyrics-container overflow-y-auto max-h-96 text-gray-400">
          <p v-for="(line, index) in parsedLyrics" :key="index" :class="{ 'active-line': index === currentLine }">
            {{ line.text }}
          </p>
        </div>
      </div>
      <!-- 关闭按钮，使用图标并定位到右上角 -->
      <button class="absolute top-2 right-2 text-white rounded p-2" @click="hideLyrics">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
          class="size-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
        </svg>
      </button>
    </div>
  </div>

</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch } from 'vue';
import axios from 'axios';
import { useMusicStore } from '../stores/useMusicStore';
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { XMarkIcon } from '@heroicons/vue/24/outline'

const musicStore = useMusicStore();
const currentMusicId = computed(() => musicStore.getCurrentMusicId());
const songs = computed(() => {
  if (musicStore.currentPlaylist) {
    return musicStore.currentPlaylist.songs || [];
  }
  return [];
}
);
const open = ref(false)
const currentSongIndex = ref(0);
const isPlaying = ref(false);
const audioPlayer = ref(null);
const currentTime = ref(0);
const totalDuration = ref(0);
const currentTimePercentage = ref(0);
const parsedLyrics = ref([{ time: 0, text: '歌词加载中...' }]);
const currentLine = ref(0);
const showLyricsOverlay = ref(false); // 新增状态变量

const currentSong = computed(() => {
  if (musicStore.currentPlaylist) {
    const songs = musicStore.currentPlaylist.songs;
    const song = songs.find(song => song.songId === currentMusicId.value);
    currentSongIndex.value = songs.indexOf(song);
    return song || songs[0];
  }
  return {};
});

const currentTimeFormatted = computed(() => {
  const minutes = Math.floor(currentTime.value / 60);
  const seconds = Math.floor(currentTime.value % 60);
  return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
});

const totalDurationFormatted = computed(() => {
  const minutes = Math.floor(totalDuration.value / 60);
  const seconds = Math.floor(totalDuration.value % 60);
  return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
});

watch(() => musicStore.currentPlaylistId, (newId) => {
  console.log('newId', newId);
  if (newId) {
    musicStore.fetchPlaylistSongs();
  }
});

watch(() => musicStore.currentMusicId, (newId) => {
  if (newId) {
    playSong(newId);
  }
});

onMounted(() => {
  audioPlayer.value = document.querySelector('audio');
  audioPlayer.value.addEventListener('loadedmetadata', loadMetadata);
  audioPlayer.value.addEventListener('timeupdate', updateTime);
  audioPlayer.value.addEventListener('ended', nextSong);
});

onBeforeUnmount(() => {
  audioPlayer.value.removeEventListener('loadedmetadata', loadMetadata);
  audioPlayer.value.removeEventListener('timeupdate', updateTime);
  audioPlayer.value.removeEventListener('ended', nextSong);
  audioPlayer.value.removeEventListener('loadeddata', () => {}); // 移除 loadeddata 事件监听器
});

// 方法
const fetchLyrics = () => {
  const lyricsUrl = currentSong.value.lyricPath;
  axios.get(lyricsUrl)
    .then(response => {
      parseLyrics(response.data);
    })
    .catch(error => {
      console.error('Error fetching lyrics:', error);
    });
};

const parseLyrics = (lrcContent) => {
  const regex = /\[(\d{2}):(\d{2})\.(\d{2})\](.*)/;
  const lines = [];

  lrcContent.split('\n').forEach(line => {
    const match = line.match(regex);
    if (match) {
      const minutes = parseInt(match[1], 10);
      const seconds = parseInt(match[2], 10);
      const milliseconds = parseInt(match[3], 10);
      const totalTimeInSeconds = minutes * 60 + seconds + milliseconds / 100;
      const text = match[4].trim();
      if (text) {
        lines.push({ time: totalTimeInSeconds, text });
      }
    }
  });

  parsedLyrics.value = lines;
  currentLine.value = 0;
};
const playSong = (songId) => {
  console.log('playSong called with songId:', songId);
  if (currentMusicId.value === songId) {
    if (isPlaying.value) {
      audioPlayer.value.pause();
    } else {
      audioPlayer.value.play().catch((error) => {
        console.error('Error playing song:', error);
      });
    }
    isPlaying.value = !isPlaying.value;
  } else {
    if (isPlaying.value) {
      audioPlayer.value.pause();
    }
    musicStore.setCurrentMusicId(songId); // 确保这里设置了当前歌曲ID
    audioPlayer.value.src = currentSong.value.filePath;
    audioPlayer.value.load();

    audioPlayer.value.addEventListener('loadeddata', () => {
      console.log('loadeddata event triggered');
      audioPlayer.value.play().catch((error) => {
        console.error('Error playing song:', error);
      });
      isPlaying.value = true;
    }, { once: true });
  }
};

const updateCurrentLine = () => {
  const audio = audioPlayer.value;
  const currentTime = audio.currentTime;
  const parsedLyricsValue = parsedLyrics.value;

  const index = parsedLyricsValue.findIndex(line => line.time >= currentTime);

  if (index !== -1) {
    if (parsedLyricsValue[index].time === currentTime) {
      currentLine.value = index;
    } else {
      currentLine.value = index - 1;
    }
  } else {
    currentLine.value = parsedLyricsValue.length - 1;
  }

  currentLine.value = Math.max(0, currentLine.value);

  const lyricsContainer = document.querySelector('.lyrics-container');
  const activeLine = document.querySelector('.active-line');
  if (activeLine) {
    const activeLineRect = activeLine.getBoundingClientRect();
    const containerRect = lyricsContainer.getBoundingClientRect();
    const lineHeight = activeLine.offsetHeight;

    let scrollTop = 0;
    if (currentLine.value === 0) {
      // 如果是第一行歌词，将其居中显示
      scrollTop = (containerRect.height - lineHeight) / 2;
    } else {
      // 其他情况下，保持原来的滚动逻辑
      scrollTop = activeLineRect.top - (containerRect.height - lineHeight) / 2;
    }
    lyricsContainer.scrollTop = scrollTop;
  }
};

const nextSong = () => {
  currentSongIndex.value = (currentSongIndex.value + 1) % songs.value.length;
  const nextSongId = songs.value[currentSongIndex.value].songId;
  musicStore.setCurrentMusicId(nextSongId);
};

const prevSong = () => {
  console.log('prevSong called');
  currentSongIndex.value = (currentSongIndex.value - 1 + songs.value.length) % songs.value.length;
  const nextSongId = songs.value[currentSongIndex.value].songId;
  musicStore.setCurrentMusicId(nextSongId);
};

const updateTime = () => {
  currentTime.value = audioPlayer.value.currentTime;
  currentTimePercentage.value = Math.floor((currentTime.value / totalDuration.value) * 100);
  updateCurrentLine();
};

const loadMetadata = () => {
  totalDuration.value = audioPlayer.value.duration;
};

const updateProgress = (value) => {
  const newTime = (value / 100) * totalDuration.value;
  audioPlayer.value.currentTime = newTime;
};

// 显示歌词遮罩层
const showLyrics = () => {
  console.log('showLyrics');
  showLyricsOverlay.value = !showLyricsOverlay.value;
  fetchLyrics(); // 加载歌词
};

// 关闭歌词遮罩层
const hideLyrics = () => {
  showLyricsOverlay.value = false;
};
</script>

<style scoped>
.active-line {
  @apply text-green-500 font-bold;
}

.lyrics-overlay {
  animation: slideInFromBottomLeft 0.5s forwards;
}

@keyframes slideInFromBottomLeft {
  from {
    transform: translate(-100%, 100%);
    opacity: 0;
  }

  to {
    transform: translate(0, 0);
    opacity: 1;
  }
}

.custom-range::-webkit-slider-thumb {
  height: 1px;
  /* 与进度条高度一致 */
  width: 1px;
  /* 保持滑块的宽度 */
  background-color: white;
  visibility: hidden;
  /* 默认隐藏滑块 */
}

.custom-range:hover::-webkit-slider-thumb {
  visibility: visible;
  /* 鼠标悬停时显示滑块 */
}

.custom-range::-moz-range-thumb {
  height: 1px;
  /* 与进度条高度一致 */
  width: 1px;
  /* 保持滑块的宽度 */
  background-color: white;
  visibility: hidden;
  /* 默认隐藏滑块 */
}

.custom-range:hover::-moz-range-thumb {
  visibility: visible;
  /* 鼠标悬停时显示滑块 */
}

.custom-range::-ms-thumb {
  height: 1px;
  /* 与进度条高度一致 */
  width: 1px;
  /* 保持滑块的宽度 */
  background-color: white;
  visibility: hidden;
  /* 默认隐藏滑块 */
}

.custom-range:hover::-ms-thumb {
  visibility: visible;
  /* 鼠标悬停时显示滑块 */
}
</style>