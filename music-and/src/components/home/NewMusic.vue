<template>
    <div class="container mx-auto p-4 mb-32">
        <div class="grid grid-cols-5 gap-4">
            <div v-for="(song, index) in newSongs" :key="index" class="relative group">
                <img :src="song.coverPath" alt="Song Cover" class="w-full h-full object-cover rounded-lg">
                <div class="absolute inset-0 bg-black opacity-0 group-hover:opacity-30 transition duration-300"></div>
                <!-- 修改这里 -->
                <div
                    class="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition duration-300">
                    <button @click="playSong(song)" class="bg-white text-black px-4 py-2 rounded-full">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.347a1.125 1.125 0 0 1 0 1.972l-11.54 6.347a1.125 1.125 0 0 1-1.667-.986V5.653Z" />
                        </svg>

                    </button>
                </div>
                <div class="absolute bottom-2 left-2 right-2 text-white text-sm truncate">
                    {{ song.title }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useMusicStore } from '../../stores/useMusicStore';

const musicStore = useMusicStore();

const newSongs = ref([]);

onMounted(async () => {
    try {
        const response = await axios.post('/api/music/getSongPage', {
            pageNum: 1,
            pageSize: 5
        });
        newSongs.value = response.data.data.slice(0, 5); // 获取前五首新歌
        console.log(newSongs.value);
    } catch (error) {
        console.error("Failed to fetch new songs:", error);
    }
});

const playSong = (song) => {
    musicStore.setCurrentMusic(song);
    console.log(song);
};
</script>

<style scoped>
.container {
    max-width: 1200px;
}

.grid-cols-5 {
    grid-template-columns: repeat(5, minmax(0, 1fr));
}

.relative {
    position: relative;
}

.group:hover .opacity-0 {
    opacity: 0.3 !important;
    /* 修改这里 */
}

.button-container {
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
}

.play-button {
    background-color: white;
    color: black;
    padding: 8px 16px;
    border-radius: 50%;
    cursor: pointer;
}
</style>