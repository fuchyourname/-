<template>
  <div class="flex flex-col space-y-4 pl-4 ml-20">
    <div v-for="category in categories" :key="category.title" class="flex space-x-2">
      <button v-for="item in category.items" :key="item" @click="selectItem(category, item)" :class="{
        ' text-gray-700 font-semibold py-2 px-4 rounded hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm': true,
        'bg-green-500 text-green-100': isItemSelected(category, item)
      }">
        {{ item }}
      </button>
    </div>
  </div>
  <div class="bg-white py-24 sm:py-32">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto mt-1 flow-root max-w-2xl sm:mt-1 lg:mx-0 lg:max-w-none">
        <div class="-mt-20 sm:-mx-4 sm:columns-2 sm:text-[0] lg:columns-3">
          <div v-for="artist in artists" :key="artist.artistId"
            class="mb-4 sm:inline-block sm:w-full sm:px-4">
            <figure class="rounded-2xl bg-gray-50 text-sm leading-6">
              <blockquote class="text-gray-900 p-0">
                <p>{{ `“${artist.bio}”` }}</p>
              </blockquote>
              <figcaption class="flex items-center gap-x-2 p-0">
                <img class="h-6 w-6 rounded-full bg-gray-50" :src="artist.picUrl" alt="" />
                <div>
                  <div class="font-semibold text-gray-900">{{ artist.name }}</div>
                  <div class="text-gray-600">{{ `@${artist.nationality}` }}</div>
                </div>
              </figcaption>
            </figure>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

// 定义数据
const categories = ref([
  { title: '全部字母', items: ['全部', ...Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i))] },
  { title: '地区', items: ['全部', '内地', '港台', '欧美', '日本', '韩国'] },
  { title: '类型', items: ['全部', '男', '女', '组合'] },
]);
const selectedItems = ref({});
const artists = ref([]);
const params = ref({
  nationality: null,
  sex: null,
  initials: null
});

// 初始化 selectedItems 和 params
onMounted(() => {
  categories.value.forEach(category => {
    selectedItems.value[category.title] = category.items[0];
  });
  updateParams();
  fetchArtists();
});

// 方法
const selectItem = (category, item) => {
  // 取消当前行已选择的项
  if (selectedItems.value[category.title] === item) {
    delete selectedItems.value[category.title];
  } else {
    selectedItems.value[category.title] = item;
  }
  updateParams();
  fetchArtists();
};

const isItemSelected = (category, item) => {
  return selectedItems.value[category.title] === item;
};

const updateParams = () => {
  params.value.nationality = selectedItems.value['地区'] === '全部' ? null : selectedItems.value['地区'];
  params.value.sex = selectedItems.value['类型'] === '全部' ? null : (selectedItems.value['类型'] === '男' ? 1 : selectedItems.value['类型'] === '女' ? 2 : 0);
  params.value.initials = selectedItems.value['全部字母'] === '全部' ? null : selectedItems.value['全部字母'];
};

const fetchArtists = () => {
  axios.post('http://127.0.0.1:8080/player/getPlayers', params.value)
    .then(response => {
      artists.value = response.data.data;
    })
    .catch(error => {
      console.error('Error fetching artist data:', error);
    });
};
</script>

<style scoped>
/* 你可以在这里添加样式 */
</style>