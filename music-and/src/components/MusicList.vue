<template>
  <div>
    <div class="flow-root">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
          <table class="min-w-full divide-y divide-gray-300">
            <thead>
              <tr>
                <th scope="col" class="py-2 pl-4 pr-3 text-left text-xs font-semibold text-gray-500 sm:pl-3">#</th>
                <th scope="col" class="py-2 pl-4 pr-3 text-left text-xs font-semibold text-gray-500 sm:pl-3">标题</th>
                <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">专辑</th>
                <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">喜欢</th>
                <th scope="col" class="px-3 py-2 text-left text-xs font-semibold text-gray-500">时长</th>
              </tr>
            </thead>
            <tbody class="bg-white">
              <tr v-for="person in people" :key="person.id" class="even:bg-gray-50">
                <td class="whitespace-nowrap py-2 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-3">{{ person.id }}</td>
                <td class="whitespace-nowrap py-2 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-3">{{ person.title }}</td>
                <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500">{{ person.albums }}</td>
                <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500 pl-2">
                  <svg xmlns="http://www.w3.org/2000/svg"
                    :fill="person.like ? 'red' : 'none'" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                      d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" />
                  </svg>
                </td>
                <td class="whitespace-nowrap px-3 py-2 text-sm text-gray-500">{{ person.time }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios';

// 定义 props
const props = defineProps(['id']);

const songs = ref([]);

onMounted(() => {
  console.log('歌单 ID:', props.id);
})
</script>