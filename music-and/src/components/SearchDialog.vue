<template>
    <TransitionRoot :show="open" as="template" @after-leave="query = ''" appear>
      <Dialog class="relative z-10" @close="$emit('close')">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100" leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 bg-gray-500/25 transition-opacity" />
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto p-4 sm:p-6 md:p-20">
        <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0 scale-95" enter-to="opacity-100 scale-100" leave="ease-in duration-200" leave-from="opacity-100 scale-100" leave-to="opacity-0 scale-95">
          <DialogPanel class="mx-auto max-w-xl transform divide-y divide-gray-100 overflow-hidden rounded-xl bg-white shadow-2xl ring-1 ring-black/5 transition-all">
            <Combobox @update:modelValue="onSelect">
              <div class="relative">
                <MagnifyingGlassIcon class="pointer-events-none absolute left-4 top-3.5 size-5 text-gray-400" aria-hidden="true" />
                <ComboboxInput 
                  class="h-12 w-full border-0 bg-transparent pl-11 pr-4 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm" 
                  placeholder="Search..." 
                  @input="query = $event.target.value"
                  @keydown.enter="handleEnter" 
                />
              </div>

              <ComboboxOptions v-if="filteredPeople.length > 0" static class="max-h-72 scroll-py-2 overflow-y-auto py-2 text-sm text-gray-800">
                <ComboboxOption v-for="person in filteredPeople" :key="person.id" :value="person" as="template" v-slot="{ active }">
                  <li :class="['cursor-default select-none px-4 py-2', active && 'bg-indigo-600 text-white outline-none']">
                    <span v-html="highlightMatch(person.name)"></span>
                  </li>
                </ComboboxOption>
              </ComboboxOptions>

              <p v-if="query !== '' && filteredPeople.length === 0" class="p-4 text-sm text-gray-500">No people found.</p>
            </Combobox>
          </DialogPanel>
        </TransitionChild>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { MagnifyingGlassIcon } from '@heroicons/vue/20/solid'
import {
  Combobox,
  ComboboxInput,
  ComboboxOptions,
  ComboboxOption,
  Dialog,
  DialogPanel,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue'

const people = []

const open = ref(true)
const query = ref('')
const filteredPeople = computed(() =>
  query.value === ''
    ? []
    : people.filter((person) => {
        return person.name.toLowerCase().includes(query.value.toLowerCase())
      }),
)

function onSelect(person) {
  if (person) {
    window.location = person.url
  }
}

function highlightMatch(text) {
  const regex = new RegExp(`(${query.value})`, 'gi')
  return text.replace(regex, '<strong>$1</strong>')
}

const router = useRouter()
function handleEnter() {
  if (query.value) {
    router.push({ name: 'serachRes', params: { keyword: query.value } })
    open.value = false // 添加这行代码以关闭弹窗
  }
}
</script>

<style scoped>
strong {
  color: blue;
}
</style>