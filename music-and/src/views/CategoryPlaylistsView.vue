<template>
    <div class="mt-0">
        <div class="sm:flex sm:items-baseline ml-32">
            <div class="mt-4 sm:ml-10 sm:mt-0 grid grid-cols-6 gap-4">
                <button v-for="tab in tabs" :key="tab.name" @click="selectTab(tab)"
                    :class="{'bg-gray-300': tab.current, 'bg-gray-200': !tab.current}"
                    class="rounded-md px-2 py-1 w-44 text-sm font-semibold text-gray-500 shadow-sm ring-1 ring-inset hover:bg-gray-50">
                    {{ tab.name }}
                </button>
            </div>
        </div>
    </div>
    <div class="bg-white py-12 sm:py-0">
        <div class="mx-auto max-w-7xl px-6 lg:px-8">
            <div class="mx-auto mt-8 grid max-w-2xl auto-rows-fr grid-cols-1 gap-4 sm:mt-12 lg:mx-0 lg:max-w-none lg:grid-cols-6">
                <article v-for="post in posts" :key="post.id"
                    class="relative isolate flex flex-col justify-end overflow-hidden bg-gray-900 px-2 pb-2 pt-40 rounded-md">
                    <img :src="post.imageUrl" alt="" class="absolute inset-0 -z-10 h-full w-full object-cover" />
                    <div class="absolute inset-0 -z-10 bg-gradient-to-t from-gray-900 via-gray-900/40" />
                    <div class="absolute inset-0 -z-10 rounded-2xl ring-1 ring-inset ring-gray-900/10" />

                    <div class="flex flex-wrap items-center gap-y-1 overflow-hidden text-sm leading-6 text-gray-300">
                        <time :datetime="post.datetime" class="mr-8">{{ post.date }}</time>
                        <div class="-ml-4 flex items-center gap-x-4">
                            <svg viewBox="0 0 2 2" class="-ml-0.5 h-0.5 w-0.5 flex-none fill-white/50">
                                <circle cx="1" cy="1" r="1" />
                            </svg>
                            <div class="flex gap-x-2.5">
                                <img :src="post.userAvatar" alt=""
                                    class="h-6 w-6 flex-none rounded-full bg-white/10" />
                                {{ post.userName }}
                            </div>
                        </div>
                    </div>
                    <h3 class="mt-3 text-lg font-semibold leading-6 text-white">
                        <RouterLink :to="post.href">
                            <span class="absolute inset-0" />
                            {{ post.name }}
                        </RouterLink>
                    </h3>
                </article>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { RouterLink } from 'vue-router';
import axios from 'axios';

const tabs = ref([]);
const selectedTab = ref(null);
const posts = ref([]);

const selectTab = (tab) => {
    selectedTab.value = tab;
    tabs.value.forEach(t => t.current = t === tab);
    fetchPostsByCategory(tab.id);
};

const fetchCategories = async () => {
    try {
        const response = await axios.get('/api/index/getGenresList/3');
        tabs.value = response.data.data.map(category => ({
            name: category.name,
            id: category.genreId,
            current: false
        }));
        if (tabs.value.length > 0) {
            selectTab(tabs.value[0]);
        }
    } catch (error) {
        console.error('获取分类列表失败:', error);
    }
};

const fetchPostsByCategory = async (categoryId) => {
    try {
        const response = await axios.get('/api/playList/getPlayListByType', 
        { 
            params: 
            { 
                type: categoryId, 
            } 
        });
        posts.value = response.data.data.map(post => ({
            ...post,
            href: `/home/songlistdetails/${post.playlistId}` // 添加 href 属性
        }));
    } catch (error) {
        console.error('获取歌单数据失败:', error);
    }
};

onMounted(() => {
    fetchCategories();
});
</script>