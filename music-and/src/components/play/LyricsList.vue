<template>
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

const musicStore = useMusicStore();
const audioPlayer = ref(null);
const parsedLyrics = ref([{ time: 0, text: '歌词加载中...' }]);
const currentLine = ref(0);
const showLyricsOverlay = ref(false); // 新增状态变量


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
</style>