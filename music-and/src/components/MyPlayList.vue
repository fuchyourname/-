<template>
    <div class="ml-32">
        <div class="mt-7">
            <span class="font-sans">我创建的歌单</span>
            <div
                class="mt-4 grid grid-cols-1 gap-x-4 gap-y-8 sm:grid-cols-2 sm:gap-y-8 lg:grid-cols-3 lg:gap-x-1 xl:grid-cols-6 w-full">
                <div v-for="playlist in playlists" :key="playlist.playlistId" class="group relative"
                    @click="handlePlaylistClick(playlist.playlistId)">
                    <div class="h-48 w-48 overflow-hidden rounded-md bg-gray-200 group-hover:opacity-75">
                        <img :src="playlist.imageUrl" class="h-48 w-48 object-cover object-center" />
                    </div>
                    <h3 class="mt-4 text-sm text-gray-500">
                        <span class="absolute inset-0" />
                        {{ playlist.name }}
                    </h3>
                    <p class="mt-1 text-lg font-medium">
                        <span class="text-gray-900">
                            {{ playlist.description }}
                        </span>
                    </p>
                </div>
            </div>
            <div>
            </div>
            <div class="mt-7">
                <span class="font-sans">我收藏的歌单</span>
                <div
                    class="mt-4 grid grid-cols-1 gap-x-4 gap-y-8 sm:grid-cols-2 sm:gap-y-8 lg:grid-cols-3 lg:gap-x-1 xl:grid-cols-6 w-full">
                    <div v-for="playlist in playlists" :key="playlist.playlistId" class="group relative"
                        @click="handlePlaylistClick(playlist.playlistId)">
                        <div class="h-48 w-48 overflow-hidden rounded-md bg-gray-200 group-hover:opacity-75">
                            <img :src="playlist.imageUrl" class="h-48 w-48 object-cover object-center" />
                        </div>
                        <h3 class="mt-4 text-sm text-gray-500">
                            <span class="absolute inset-0" />
                            {{ playlist.name }}
                        </h3>
                        <p class="mt-1 text-lg font-medium">
                            <span class="text-gray-900">
                                {{ playlist.description }}
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { onMounted } from 'vue'

const playlists = ref([])

onMounted(() => {
    axios.get('http://127.0.0.1:8080/playList/getRecommendPlayList')
        .then(response => {
            playlists.value = response.data.data
            console.log(response.data.data)
        })
        .catch(error => {
            console.error('Error fetching playlists:', error)
        })
})
const handlePlaylistClick = (playlistId) => {
  router.push(`/home/songlistdetails/${playlistId}`)
}

</script>