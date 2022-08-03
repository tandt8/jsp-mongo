<template>
<HelloWorld/>
<div class="q-my-xl">
  <SearchDetail/>
  <SearchDetail/>
  <SearchDetail/>
  <SearchDetail/>
</div>
</template>

<script>
import { ref } from 'vue';
import HelloWorld from './components/HelloWorld.vue';
import SearchDetail from './components/SearchDetail.vue';

export default {
  name: 'LayoutDefault',

  components: {
    HelloWorld,
    SearchDetail,
    },

  setup() {
    return {
      leftDrawerOpen: ref(false),
    };
  },
};
</script>
