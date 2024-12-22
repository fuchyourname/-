<template>
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
import { useMusicStore } from '../../stores/useMusicStore';

const props = defineProps({
  audioPlayer: {
    type: Object,
    required: true,
    default: () => ({ value: null })
  },
  currentSong: {
    type: Object,
    required: true
  },
  showLyricsOverlay: {
    type: Boolean,
    required: true
  }
});

const musicStore = useMusicStore();
const parsedLyrics = ref([{ time: 0, text: '歌词加载中...' }]);
const currentLine = ref(0);

// 方法
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
  const audio = props.audioPlayer?.value;
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

// 显示歌词遮罩层
const showLyrics = () => {
  fetchLyrics(); // 加载歌词
};

// 关闭歌词遮罩层
const hideLyrics = () => {
  emit('hide');
};

const emit = defineEmits(['hide']);

watch(() => musicStore.getCurrentMusic(), () => {
  fetchLyrics();
});

// 使用 watch 来监听 audioPlayer 的变化
let timeUpdateListener = null;

watch(() => props.audioPlayer, (newAudioPlayer) => {
  if (newAudioPlayer && newAudioPlayer.value) {
    if (timeUpdateListener) {
      newAudioPlayer.value.removeEventListener('timeupdate', timeUpdateListener);
    }
    timeUpdateListener = updateCurrentLine;
    newAudioPlayer.value.addEventListener('timeupdate', timeUpdateListener);
    console.log('audioPlayer updated and listener added');
  } else {
    if (timeUpdateListener && props.audioPlayer?.value) {
      props.audioPlayer.value.removeEventListener('timeupdate', timeUpdateListener);
      timeUpdateListener = null;
    }
    console.error('audioPlayer is not defined');
  }
}, { immediate: true });

onMounted(() => {
  fetchLyrics();
});

onBeforeUnmount(() => {
  if (props.audioPlayer && props.audioPlayer.value && timeUpdateListener) {
    props.audioPlayer.value.removeEventListener('timeupdate', timeUpdateListener);
  }
});
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

.active-line {
  color: yellow; /* 设置当前行的字体颜色 */
  font-weight: bold; /* 可选：加粗当前行的字体 */
}
</style>