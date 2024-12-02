<template>
  <TransitionRoot as="template" :show="open">
    <Dialog class="relative z-10" @close="open = false">
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
              class="relative transform overflow-hidden rounded-lg bg-white px-4 pb-4 pt-5 text-left shadow-xl transition-all sm:my-8 sm:w-[600px] sm:h-[500px] sm:p-6">
              <div class="flex flex-col h-full">
                <!-- 添加关闭按钮 -->
                <div class="flex justify-between items-center mb-4">
                  <DialogTitle as="h3"
                    class="text-base font-semibold text-gray-900 text-center w-full">{{ user.name }}
                  </DialogTitle>
                  <button type="button" class="text-gray-400 hover:text-gray-500" @click="open = false">
                    <XMarkIcon class="w-6 h-6" aria-hidden="true" />
                  </button>
                </div>

                <div class="flex-1 overflow-y-auto">
                  <ul>
                    <li v-for="(message, index) in messages" :key="index"
                        :class="message.from === 'me' ? 'flex justify-end mb-2' : 'flex justify-start mb-2'">
                      <div class="flex items-start">
                        <div v-if="message.from === 'other'" class="flex flex-col items-center mr-2">
                          <img :src="user.avatar" alt="User Avatar" class="w-12 h-12" />
                          <small class="block text-center text-gray-500 mt-1">{{ formattedTime(message.timestamp) }}</small>
                        </div>
                        <div :class="message.from === 'me' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-900'"
                          class="rounded-lg px-4 py-2 max-w-md">
                          <p>{{ message.text }}</p>
                          <img v-if="message.image" :src="message.image" alt="Message Image" class="mt-2 max-w-full">
                        </div>
                        <div v-if="message.from === 'me'" class="flex flex-col items-center ml-2">
                          <img :src="user.avatar" alt="User Avatar" class="w-12 h-12" />
                          <small class="block text-center text-gray-500 mt-1">{{ formattedTime(message.timestamp) }}</small>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
                <div class="mt-4 flex space-x-2">
                  <input v-model="newMessage" type="text" placeholder="Type a message..."
                    class="w-full rounded-lg border-gray-300 px-4 py-2" @keyup.enter="sendMessage" />
                  <button type="button"
                    class="inline-flex justify-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    @click="sendMessage">Send</button>
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
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { XMarkIcon, PhotoIcon } from '@heroicons/vue/24/outline'
import { format } from 'date-fns'

const user = {
  name: 'John Doe',
  avatar: 'http://192.168.154.1:9000/music/cover/1/1005_1732423111003.jpg'
}

const emit = defineEmits(['close'])

const open = ref(true)
const newMessage = ref('')
const newMessageImage = ref(null)
const messages = ref([
  { from: 'other', text: 'Hello! How are you?', timestamp: new Date().toISOString() },
  { from: 'me', text: 'Hi, I am doing well. How about you?', timestamp: new Date().toISOString() }
])

const websocket = ref(null)

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    const timestamp = new Date().toISOString()
    const message = { from: 'me', text: newMessage.value, timestamp, image: newMessageImage.value }
    messages.value.push(message)
    websocket.value.send(JSON.stringify(message))
    newMessage.value = ''
    newMessageImage.value = null
  }
}

const formattedTime = (timestamp) => {
  return format(new Date(timestamp), 'MM-dd HH:mm')
}

const close = () => {
  emit('close')
}

const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      newMessageImage.value = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const openFilePicker = () => {
  const input = document.querySelector('input[type="file"]')
  input.click()
}

onMounted(() => {
  websocket.value = new WebSocket('ws://127.0.0.1:8080/websocket/1')

  websocket.value.onopen = () => {
    console.log('WebSocket connection established')
  }

  websocket.value.onmessage = (event) => {
    const message = JSON.parse(event.data)
    messages.value.push(message)
  }

  websocket.value.onclose = () => {
    console.log('WebSocket connection closed')
  }

  websocket.value.onerror = (error) => {
    console.error('WebSocket error:', error)
  }
})

onBeforeUnmount(() => {
  if (websocket.value) {
    websocket.value.close()
  }
})
</script>

<style scoped>
/* 你可以在这里添加一些样式 */
</style>