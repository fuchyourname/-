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
              <path d="M9.195 18.44c1.25.714 2.805-.189 2.805-1.629v-2.34l6.945 3.968c1.25.715 2.805-.188 2.805-1.628V8.69c0-1.44-1.555-2.343-2.805-1.628L12 11.029v-2.34c0-1.44-1.555-2.343-2.805-1.628l-7.108 4.061c-1.26.72-1.26 2.536 0 3.256l7.108 4.061Z" />
            </svg>
          </button>
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none mx-4" @click="playSong">
            <svg v-if="!isPlaying" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path fill-rule="evenodd" d="M4.5 5.653c0-1.427 1.529-2.33 2.779-1.643l11.54 6.347c1.295.712 1.295 2.573 0 3.286L7.28 19.99c-1.25.687-2.779-.217-2.779-1.643V5.653Z" clip-rule="evenodd" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path fill-rule="evenodd" d="M6.75 5.25a.75.75 0 0 1 .75-.75H9a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H7.5a.75.75 0 0 1-.75-.75V5.25Zm7.5 0A.75.75 0 0 1 15 4.5h1.5a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H15a.75.75 0 0 1-.75-.75V5.25Z" clip-rule="evenodd" />
            </svg>
          </button>
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none" @click="nextSong">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
            </svg>
          </button>
        </div>
        <div class="flex items-center mt-2">
          <span class="text-gray-600 text-sm mr-2">{{ currentTimeFormatted }}</span>
          <input type="range" min="0" max="100" v-model="currentTimePercentage" class="custom-range w-80 h-1 bg-gray-200 rounded-full outline-none appearance-none cursor-pointer">
          <span class="text-gray-600 text-sm ml-2">{{ totalDurationFormatted }}</span>
        </div>
      </div>
    </div>
    <div class="ml-auto mt-4 z-50">
      <button @click="togglePlayList">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 w-7 h-7">
          <path stroke-linecap="round" stroke-linejoin="round" d="M3 4.5h14.25M3 9h9.75M3 13.5h9.75m4.5-4.5v12m0 0-3.75-3.75M17.25 21 21 17.25" />
        </svg>
      </button>
    </div>
  </div>
  <audio ref="audioPlayer" @play="onPlay" @pause="onPause" @timeupdate="onTimeUpdate" @loadedmetadata="onLoadedMetadata" @error="handleAudioError"></audio>
  <PlayList v-if="isOpen" :isOpen="isOpen" :close="togglePlayList" @close="togglePlayList" />
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
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
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
import PlayList from './play/PlayList.vue';

const musicStore = useMusicStore();
const audioPlayer = ref(null);
const currentSong = computed(() => musicStore.currentMusic);

const currentTime = ref(0);
const totalDuration = ref(0);
const isPlaying = ref(false);

const isOpen = ref(false);

const showLyricsOverlay = ref(false);

const parsedLyrics = ref([{ time: 0, text: '歌词加载中...' }]);
const currentLine = ref(0);

const showLyrics = () => {
  showLyricsOverlay.value = !showLyricsOverlay.value;
  fetchLyrics();
};

const hideLyrics = () => {
  showLyricsOverlay.value = false;
};

const togglePlayList = () => {
  isOpen.value = !isOpen.value;
};

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

const currentTimePercentage = computed({
  get() {
    return (currentTime.value / totalDuration.value) * 100;
  },
  set(value) {
    const newTime = (value / 100) * totalDuration.value;
    audioPlayer.value.currentTime = newTime;
    currentTime.value = newTime;
  }
});

const fetchLyrics = () => {
  const lyricsUrl = musicStore.getCurrentMusic().lyricPath;
  axios.get(lyricsUrl)
    .then(response => {
      if (response.data && response.data) {
        parseLyrics(response.data);
      } else {
        parsedLyrics.value = [{ time: 0, text: '歌词加载失败' }];
      }
    })
    .catch(error => {
      parsedLyrics.value = [{ time: 0, text: '歌词加载失败' }];
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

const updateCurrentLine = () => {
  const audio = audioPlayer.value;
  if (!audio) return;

  const currentTime = audio.currentTime;
  const parsedLyricsValue = parsedLyrics.value;

  const index = parsedLyricsValue.findIndex(line => line.time > currentTime);
  currentLine.value = index === -1 ? parsedLyricsValue.length - 1 : index - 1;

  const lyricsContainer = document.querySelector('.lyrics-container');
  const activeLine = document.querySelector('.active-line');
  if (activeLine && lyricsContainer) {
    const activeLineRect = activeLine.getBoundingClientRect();
    const containerRect = lyricsContainer.getBoundingClientRect();
    const lineHeight = activeLine.offsetHeight;

    const scrollTop = activeLineRect.top - containerRect.top - (containerRect.height - lineHeight) / 2;
    lyricsContainer.scrollTop = scrollTop;
  }
};

watch(musicStore.getIsPaused, () => {
  if (musicStore.getIsPaused) {
    audioPlayer.value.pause();
  } else {
    audioPlayer.value.play().catch(error => {
      console.error('Failed to play the audio:', error);
    });
  }
});

const playSong = () => {
  if (audioPlayer.value.paused) {
    musicStore.setIsPaused(false);
    audioPlayer.value.play().catch(error => {
      console.error('Failed to play the audio:', error);
    });
  } else {
    audioPlayer.value.pause();
  }
};

const nextSong = () => {
  musicStore.playNext();
};

const prevSong = () => {
  musicStore.playPrevious();
};

const onPlay = () => {
  isPlaying.value = true;
};

const onPause = () => {
  isPlaying.value = false;
};

const onTimeUpdate = () => {
  currentTime.value = audioPlayer.value.currentTime;
  updateCurrentLine();
};

const onLoadedMetadata = () => {
  totalDuration.value = audioPlayer.value.duration;

  // 如果之前正在播放，则自动播放新歌曲
  if (isPlaying.value) {
    audioPlayer.value.play().catch(error => {
      console.error('Failed to play the audio:', error);
    });
  }
};

const handleAudioError = (event) => {
  console.error('Audio error:', event.target.error.code, event.target.error.message);
};

// 监听歌曲变化
watch(currentSong, async () => {
  // 重置时间
  currentTime.value = 0;
  totalDuration.value = 0;

  // 设置新的音频源
  audioPlayer.value.src = currentSong.value.filePath;
  
  musicStore.addPlayCount();

  try {
    await audioPlayer.value.load(); // 加载新歌曲

    // 如果之前正在播放，则自动播放新歌曲
    if (isPlaying.value) {
      musicStore.setIsPaused(false);
      await audioPlayer.value.play();
    }
  } catch (error) {
    console.error('Failed to load or play the audio:', error);
  }
});

onMounted(() => {
  audioPlayer.value.addEventListener('play', onPlay);
  audioPlayer.value.addEventListener('pause', onPause);
  audioPlayer.value.addEventListener('timeupdate', onTimeUpdate);
  audioPlayer.value.addEventListener('loadedmetadata', onLoadedMetadata);
  audioPlayer.value.addEventListener('error', handleAudioError);
});

onBeforeUnmount(() => {
  audioPlayer.value.removeEventListener('play', onPlay);
  audioPlayer.value.removeEventListener('pause', onPause);
  audioPlayer.value.removeEventListener('timeupdate', onTimeUpdate);
  audioPlayer.value.removeEventListener('loadedmetadata', onLoadedMetadata);
  audioPlayer.value.removeEventListener('error', handleAudioError);
});
</script>

<style scoped>
.custom-range::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 16px;
  height: 16px;
  background: #4299e1;
  border-radius: 50%;
  cursor: pointer;
}

.custom-range::-moz-range-thumb {
  width: 16px;
  height: 16px;
  background: #4299e1;
  border-radius: 50%;
  cursor: pointer;
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

.active-line {
  color: yellow; /* 设置当前行的字体颜色 */
  font-weight: bold; /* 可选：加粗当前行的字体 */
}
</style>