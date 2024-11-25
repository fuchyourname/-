<script setup lang="ts">
import { onMounted } from 'vue';
import { RouterView } from 'vue-router';
import { useRouter } from 'vue-router';
const router = useRouter();
onMounted(() => {
    router.push('/home/my/like');
});
</script>

<template>
  <div class="relative bg-cover bg-center" style="background-image: url('http://192.168.154.1:9000/music/系统/1.jpg'); background-size: cover; background-position: center; height: 250px;">
    <div class="flex flex-col items-center justify-center p-8 rounded-lg shadow-lg">
      <div>
        <img
          class="inline-block h-20 w-20 rounded-full transition-transform duration-300 hover:scale-125"
          src="http://192.168.154.1:9000/music/系统/admin.jpg"
          alt=""
        />
      </div>
      <div class="mt-3 text-center">
        <p class="text-lg font-medium text-white">Tom Cook</p>
        <div class="flex justify-center mt-2">
          <div class="flex flex-col items-center mr-4">
            <span class="text-sm font-medium text-white">1000</span>
            <span class="text-xs font-medium text-white">关注</span>
          </div>
          <div class="flex flex-col items-center">
            <span class="text-sm font-medium text-white">500</span>
            <span class="text-xs font-medium text-white">粉丝</span>
          </div>
        </div>
      </div>
    </div>
    <div class="absolute bottom-4 left-16">
      <span class="isolate inline-flex rounded-md">
        <button type="button" class="relative inline-flex items-center rounded-l-md bg-transparent px-3 py-2 text-sm font-semibold text-white hover:bg-white/10 focus:z-10"><router-link to="/home/my/like">我喜欢</router-link></button>
        <button type="button" class="relative -ml-3 inline-flex items-center bg-transparent px-3 py-2 text-sm font-semibold text-white hover:bg-white/10 focus:z-10"><router-link to="/home/my/playlist">我的歌单</router-link></button>
        <button type="button" class="relative -ml-3 inline-flex items-center rounded-r-md bg-transparent px-3 py-2 text-sm font-semibold text-white hover:bg-white/10 focus:z-10"><router-link to="/home/my/follow">关注</router-link></button>
        <button type="button" class="relative -ml-3 inline-flex items-center rounded-r-md bg-transparent px-3 py-2 text-sm font-semibold text-white hover:bg-white/10 focus:z-10"><router-link to="/home/my/fans">粉丝</router-link></button>
      </span>
    </div>
  </div>
  <RouterView></RouterView>
</template>