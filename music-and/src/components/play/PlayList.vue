<template>
  <TransitionRoot as="template" :show="isOpen">
    <Dialog class="relative z-10" @close="closePlayList">
      <div class="fixed inset-0" />
      <div class="fixed inset-0 overflow-hidden">
        <div class="absolute inset-0 overflow-hidden">
          <div class="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
            <TransitionChild as="template" enter="transform transition ease-in-out duration-500 sm:duration-700"
              enter-from="translate-x-full" enter-to="translate-x-0"
              leave="transform transition ease-in-out duration-500 sm:duration-700" leave-from="translate-x-0"
              leave-to="translate-x-full" :show="isOpen">
              <DialogPanel class="pointer-events-auto w-screen max-w-md">
                <div class="flex h-full flex-col overflow-y-scroll bg-white py-6 shadow-xl">
                  <div class="px-4 sm:px-6">
                    <div class="flex items-start justify-between">
                      <DialogTitle class="text-base font-semibold text-gray-900">播放队列</DialogTitle>
                      <div class="ml-3 flex h-7 items-center">
                        <button type="button"
                          class="relative rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                          @click="closePlayList">
                          <span class="absolute -inset-2.5" />
                          <span class="sr-only">Close panel</span>
                          <XMarkIcon class="size-6" aria-hidden="true" />
                        </button>
                      </div>
                    </div>
                  </div>
                  <div class="relative mt-6 flex-1 px-4 sm:px-6">
                    <ul>
                      <li v-for="song in songs" :key="song.songId"
                        class="py-2 border-b last:border-b-0 flex items-center"
                        :class="{ 'bg-gray-100': song.songId === currentMusicId }">
                        <img :src="song.coverPath" class="w-10 h-10 rounded-lg mr-4" alt="Song Cover">
                        <div>
                          <p class="text-gray-800 font-semibold">{{ song.title }}</p>
                          <p class="text-gray-600 text-sm">{{ song.artist }}</p>
                        </div>
                        <button class="text-gray-600 hover:text-gray-800 focus:outline-none ml-auto"
                          @click="playSong(song.songId)">
                          <svg v-if="!(song.songId === currentMusicId && isPlaying)" xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path fill-rule="evenodd"
                              d="M4.5 5.653c0-1.427 1.529-2.33 2.779-1.643l11.54 6.347c1.295.712 1.295 2.573 0 3.286L7.28 19.99c-1.25.687-2.779-.217-2.779-1.643V5.653Z"
                              clip-rule="evenodd" />
                          </svg>
                          <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
                            class="size-6">
                            <path fill-rule="evenodd"
                              d="M6.75 5.25a.75.75 0 0 1 .75-.75H9a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H7.5a.75.75 0 0 1-.75-.75V5.25Zm7.5 0A.75.75 0 0 1 15 4.5h1.5a.75.75 0 0 1 .75.75v13.5a.75.75 0 0 1-.75.75H15a.75.75 0 0 1-.75-.75V5.25Z"
                              clip-rule="evenodd" />
                          </svg>
                        </button>
                      </li>
                    </ul>
                  </div>
                </div>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue';
import { XMarkIcon } from '@heroicons/vue/24/outline';
import { useMusicStore } from '../../stores/useMusicStore';

const musicStore = useMusicStore();

const props = defineProps({
  close: {
    type: Function,
    required: true
  },
  isOpen: {
    type: Boolean,
    required: true
  }
});

const songs = musicStore.currentPlaylist;
const currentMusicId = computed(() => musicStore.currentMusic?.songId);
const isPlaying = computed(() => !musicStore.isPaused);

const closePlayList = () => {
  console.log('Closing PlayList');
  props.close();
};

watch(() => props.isOpen, (newVal) => {
  console.log(`PlayList visibility changed to: ${newVal}`);
});
</script>