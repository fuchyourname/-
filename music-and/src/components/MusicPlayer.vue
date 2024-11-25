<template>
  <div class="fixed bottom-0 left-0 right-0 p-4 bg-white shadow-md flex flex-row">
    <div class="flex items-center">
      <img :src="currentSong.cover" alt="Song Cover" class="w-12 h-12 rounded-lg mr-4">
      <div>
        <p class="text-gray-800 font-semibold">{{ currentSong.title }}</p>
        <p class="text-gray-600 text-sm">{{ currentSong.artist }}</p>
      </div>
    </div>
    <div class="fixed left-0 right-0 bottom-4 flex justify-center items-center">
      <div class="flex flex-col items-center">
        <div class="flex items-center">
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path
                d="M9.195 18.44c1.25.714 2.805-.189 2.805-1.629v-2.34l6.945 3.968c1.25.715 2.805-.188 2.805-1.628V8.69c0-1.44-1.555-2.343-2.805-1.628L12 11.029v-2.34c0-1.44-1.555-2.343-2.805-1.628l-7.108 4.061c-1.26.72-1.26 2.536 0 3.256l7.108 4.061Z" />
            </svg>
          </button>
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none mx-4" @click="play">
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
          <button class="text-gray-600 hover:text-gray-800 focus:outline-none">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
              <path
                d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
            </svg>

          </button>
        </div>
        <div class="flex items-center mt-2">
          <span class="text-gray-600 text-sm mr-2">{{ currentTimeFormatted }}</span>
          <input type="range" min="0" max="100" v-model="currentTime" 
            class="w-80 h-1 bg-gray-200 rounded-full outline-none appearance-none cursor-pointer">
          <span class="text-gray-600 text-sm ml-2">{{ totalDurationFormatted }}</span>
        </div>
      </div>
    </div>
  </div>
  <audio ref="audioPlayer" :src="currentSong.src"  @timeupdate="updateTime" @loadedmetadata="loadMetadata"></audio>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

// 数据
const songs = ref([
  {
    title: '可乐（抖音版） (长音频) - 天天AdamC',
    artist: '未知歌手',
    src: 'http://192.168.154.1:9000/music/file/27_1732354594236.mp3',
    wordListSrc: 'http://127.0.0.1:9000/test/罗生门（Follow） - 梨冻紧_Wiz_H张子豪.lrc',
    cover: 'http://192.168.154.1:9000/music/cover/1/27_1732354594609.jpg',
  },
]);
const currentSongIndex = ref(0);
const isPlaying = ref(false);
const audioPlayer = ref(null);
const currentTime = ref(0);
const totalDuration = ref(0);
const currentTimePercentage = ref(0);
const parsedLyrics = ref([{ time: 0, text: '歌词加载中...' }]);
const currentLine = ref(0);

// 计算属性
const currentSong = computed(() => songs.value[currentSongIndex.value]);
const currentTimeComputed = computed(() => {
  if (totalDuration.value === 0) return 0;
  currentTimePercentage.value = Math.floor((currentTime.value / totalDuration.value) * 100);
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

// 生命周期钩子
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
});

// 方法
const fetchLyrics = () => {
  const lyricsUrl = 'http://127.0.0.1:9000/test/罗生门（Follow） - 梨冻紧_Wiz_H张子豪.lrc';
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

const play = () => {
  console.log('play');
  if (isPlaying.value) {
    audioPlayer.value.pause();
  } else {
    audioPlayer.value.play();
  }
  isPlaying.value = !isPlaying.value;
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

    const scrollTop = activeLineRect.top - (containerRect.height - lineHeight) / 2;
    lyricsContainer.scrollTop = scrollTop;
  }
};

const nextSong = () => {
  currentSongIndex.value = (currentSongIndex.value + 1) % songs.value.length;
  audioPlayer.value.src = currentSong.value.src;
  audioPlayer.value.load();
  isPlaying.value = true;
  audioPlayer.value.play();
};

const prevSong = () => {
  currentSongIndex.value = (currentSongIndex.value - 1 + songs.value.length) % songs.value.length;
  audioPlayer.value.src = currentSong.value.src;
  audioPlayer.value.load();
  isPlaying.value = true;
  audioPlayer.value.play();
};

const updateTime = () => {
  currentTime.value = audioPlayer.value.currentTime;
  currentTimePercentage.value = Math.floor((currentTime.value / totalDuration.value) * 100);
  // updateCurrentLine();
};

const loadMetadata = () => {
  totalDuration.value = audioPlayer.value.duration;
};

const updateProgress = (value) => {
  const newTime = (value / 100) * totalDuration.value;
  audioPlayer.value.currentTime = newTime;
};

const songsList = [
]
</script>
<style scoped>
.active-line {
  @apply text-green-500 font-bold;
}
</style>