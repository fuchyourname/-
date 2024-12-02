<template>
  <div class="flex justify-between items-center">
    <span class="ml-32 text-xl font-bold">关注</span>
    <button class="mr-32 text-white px-4 py-2 rounded flex items-center bg-green-400" @click="open = true">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
        class="size-6">
        <path stroke-linecap="round" stroke-linejoin="round"
          d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
      </svg>
      发布
    </button>
  </div>

  <DynamicsList></DynamicsList>

  <TransitionRoot :show="open">
    <Dialog class="relative z-10">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
        leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 bg-gray-500/75 transition-opacity" />
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
          <TransitionChild as="template" enter="ease-out duration-300"
            enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
            enter-to="opacity-100 translate-y-0 sm:scale-100" leave="ease-in duration-200"
            leave-from="opacity-100 translate-y-0 sm:scale-100"
            leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95">
            <DialogPanel
              class="h-96 relative transform overflow-hidden rounded-lg bg-white px-4 pb-4 pt-5 text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-xl sm:p-6">
              <DialogTitle class="flex justify-center text-center">发动态</DialogTitle>
              <form class="relative">
                <div class="overflow-hidden rounded-lg border border-gray-300 shadow-sm">
                  <textarea rows="2" name="description" id="description"
                    class="h-40 block w-full resize-none border-0 py-0 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm/6"
                    placeholder="一起聊聊音乐吧~" />

                  <!-- Spacer element to match the height of the toolbar -->
                  <div>
                    <div class="py-2">
                      <div class="h-9" />
                    </div>
                    <div class="h-px" />
                    <div class="py-2">
                      <div class="py-px">
                        <div class="h-9" />
                      </div>
                    </div>
                  </div>
                </div>

                <div class="absolute inset-x-px bottom-0">
                  <!-- Actions: These are just examples to demonstrate the concept, replace/wire these up however makes sense for your project. -->
                  <div class="flex flex-nowrap justify-end space-x-2 px-2 py-2 sm:px-3">

                    <Listbox as="div" v-model="selected" class="shrink-0">
                      <ListboxLabel class="sr-only">Your mood</ListboxLabel>
                      <div class="relative">
                        <ListboxButton
                          class="relative inline-flex items-center whitespace-nowrap rounded-full bg-gray-50 px-2 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 sm:px-3">
                          <span class="flex items-center justify-center">
                            <span v-if="selected.value === null">
                              <FaceSmileIconOutline class="size-5 shrink-0" aria-hidden="true" />
                              <span class="sr-only">Add your mood</span>
                            </span>
                            <span v-if="!(selected.value === null)">
                              <span :class="[selected.bgColor, 'flex size-5 items-center justify-center rounded-full']">
                                <component :is="selected.icon" class="size-5 shrink-0 text-white" aria-hidden="true" />
                              </span>
                              <span class="sr-only">{{ selected.name }}</span>
                            </span>
                          </span>
                        </ListboxButton>
                        <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                          leave-to-class="opacity-0">
                          <ListboxOptions
                            class="absolute z-10 -ml-6 w-60 rounded-lg bg-white py-3 text-base shadow ring-1 ring-black/5 focus:outline-none sm:ml-auto sm:w-64 sm:text-sm flex flex-wrap">
                            <ListboxOption as="template" v-for="mood in moods" :key="mood.value" :value="mood"
                              v-slot="{ active }">
                              <li
                                :class="[active ? 'bg-gray-100' : 'bg-white', 'relative cursor-default select-none px-3 py-2 flex-5 min-w-[20%]']">
                                <div class="flex items-center">
                                  <div :class="[mood.bgColor, 'flex size-8 items-center justify-center rounded-full']">
                                    <component :is="mood.icon" :class="[mood.iconColor, 'size-5 shrink-0']"
                                      aria-hidden="true" />
                                  </div>
                                  <!-- <span class="ml-3 block truncate font-medium">{{ mood.name }}</span> -->
                                </div>
                              </li>
                            </ListboxOption>
                          </ListboxOptions>
                        </transition>
                      </div>
                    </Listbox>

                    <Listbox as="div" v-model="labelled" class="shrink-0">
                      <ListboxLabel class="sr-only">Add a label</ListboxLabel>
                      <div class="relative">
                        <ListboxButton
                          class="relative inline-flex items-center whitespace-nowrap rounded-full bg-gray-50 px-2 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 sm:px-3">
                          <TagIcon
                            :class="[labelled.value === null ? 'text-gray-300' : 'text-gray-500', 'size-5 shrink-0 sm:-ml-1']"
                            aria-hidden="true" />
                          <span
                            :class="[labelled.value === null ? '' : 'text-gray-900', 'hidden truncate sm:ml-2 sm:block']">{{
                              labelled.value === null ? 'Label' : labelled.name }}</span>
                        </ListboxButton>

                        <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                          leave-to-class="opacity-0">
                          <ListboxOptions
                            class="absolute right-0 z-10 mt-1 max-h-56 w-52 overflow-auto rounded-lg bg-white py-3 text-base shadow ring-1 ring-black/5 focus:outline-none sm:text-sm">
                            <ListboxOption as="template" v-for="label in labels" :key="label.value" :value="label"
                              v-slot="{ active }">
                              <li
                                :class="[active ? 'bg-gray-100' : 'bg-white', 'relative cursor-default select-none px-3 py-2']">
                                <div class="flex items-center">
                                  <span class="block truncate font-medium">{{ label.name }}</span>
                                </div>
                              </li>
                            </ListboxOption>
                          </ListboxOptions>
                        </transition>
                      </div>
                    </Listbox>
                  </div>
                  <div class="flex items-center justify-between space-x-3 border-t border-gray-200 px-2 py-2 sm:px-3">
                    <div v-if = "selectedSong == null" class="flex">
                      <button type="button" @click="showMusicModal = true"
                        class="group -my-2 -ml-2 inline-flex items-center rounded-full px-3 py-2 text-left text-gray-400 h-12">
                        <PaperClipIcon class="-ml-1 mr-2 size-5 group-hover:text-gray-500" aria-hidden="true" />
                        <span class="text-sm italic text-gray-500 group-hover:text-gray-600">给动态配上音乐</span>
                      </button>
                    </div>
                    <div 
                    v-else
                      class="relative inline-flex items-center whitespace-nowrap rounded-full bg-gray-50 px-2 py-2 text-sm font-medium text-gray-500 hover:bg-gray-100 sm:px-3">
                      <span class="flex items-center justify-center">
                        <img :src="selectedSong.image" alt="Song Cover" class="size-5 shrink-0 mr-2 w-12 h-12" />
                        <span class="flex flex-col">
                          <span class="font-bold">{{ selectedSong.title }}</span>
                          <span>{{ selectedSong.artist }}</span>
                        </span>
                      </span>
                      <button type="button" @click="removeMusic"
                        class="ml-32">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
</svg>

                        </button>
                    </div>
                  </div>
                  <div
                    class="flex justify-center text-center space-x-3 border-t border-gray-200 px-2 py-2 sm:px-3 h-11">
                    <div class="flex">
                      <button type="button"
                        @click="open = false"
                        class="group -my-2 -ml-2 inline-flex items-center rounded-full px-3 py-2 text-left text-gray-400">
                        <span class="text-sm italic text-gray-500 group-hover:text-gray-600">发布</span>
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>

  <TransitionRoot :show="showMusicModal">
    <Dialog class="relative z-10">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
        leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 bg-gray-500/75 transition-opacity" />
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
          <TransitionChild as="template" enter="ease-out duration-300"
            enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
            enter-to="opacity-100 translate-y-0 sm:scale-100" leave="ease-in duration-200"
            leave-from="opacity-100 translate-y-0 sm:scale-100"
            leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95">
            <DialogPanel
              class="relative transform overflow-hidden rounded-lg bg-white px-4 pb-4 pt-5 text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-xl sm:p-6">
              <DialogTitle class="flex justify-center text-center">选择音乐</DialogTitle>
              <div class="mt-2">
                <input type="text" v-model="searchQuery" placeholder="搜索歌曲..." class="w-full p-2 border rounded mb-4" />
                <div class="grid grid-cols-2 gap-4">
                  <div v-for="song in filteredSongs" :key="song.id" class="border p-2 rounded cursor-pointer flex flex-row"
                    @click="selectSong(song)">
                    <img :src="song.image" alt="Song Cover" class="w-14 h-14 object-cover" />
                    <div class="ml-2">
                      <p class="font-bold">{{ song.title }}</p>
                      <p>{{ song.artist }}</p>
                    </div>
                  </div>
                </div>
                <div class="mt-4 flex justify-end">
                  <button type="button" @click="showMusicModal = false"
                    class="inline-flex justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 text-sm font-medium text-blue-700 hover:bg-blue-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2">
                    关闭
                  </button>
                </div>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>
<script setup>
import { ref } from 'vue'
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions } from '@headlessui/vue'
import { PaperClipIcon, TagIcon } from '@heroicons/vue/20/solid'
import { FaceSmileIcon as FaceSmileIconOutline } from '@heroicons/vue/24/outline'
import {
  FaceFrownIcon,
  FaceSmileIcon as FaceSmileIconMini,
  FireIcon,
  HandThumbUpIcon,
  HeartIcon,
  XMarkIcon,
} from '@heroicons/vue/20/solid'
import DynamicsList from '../components/DynamicsList.vue'
import { computed } from 'vue';

const songs = [
  { id: 1, title: 'Song 1', artist: 'Artist 1', image: 'https://via.placeholder.com/150' },
  { id: 2, title: 'Song 2', artist: 'Artist 2', image: 'https://via.placeholder.com/150' },
  // 更多歌曲...
];

const searchQuery = ref('');
const selectedSong = ref(null);
const filteredSongs = computed(() => {
  return songs.filter(song =>
    song.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    song.artist.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});
const open = ref(false)
const showMusicModal = ref(false);
const moods = [
  { name: 'Excited', value: 'excited', icon: FireIcon, iconColor: 'text-white', bgColor: 'bg-red-500' },
  { name: 'Loved', value: 'loved', icon: HeartIcon, iconColor: 'text-white', bgColor: 'bg-pink-400' },
  { name: 'Happy', value: 'happy', icon: FaceSmileIconMini, iconColor: 'text-white', bgColor: 'bg-green-400' },
  { name: 'Sad', value: 'sad', icon: FaceFrownIcon, iconColor: 'text-white', bgColor: 'bg-yellow-400' },
  { name: 'Thumbsy', value: 'thumbsy', icon: HandThumbUpIcon, iconColor: 'text-white', bgColor: 'bg-blue-500' },
  { name: 'I feel nothing', value: null, icon: XMarkIcon, iconColor: 'text-gray-400', bgColor: 'bg-transparent' },
]
const selected = ref(moods[5])
const labels = [
  { name: 'Unlabelled', value: null },
  { name: 'Engineering', value: 'engineering' },
]
const labelled = ref(labels[0])

const removeMusic = () => {
  selectedSong.value = null;
};
const selectSong = (song) => {
  selectedSong.value = song;
  showMusicModal.value = false;
};
</script>