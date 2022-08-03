<template>
  <h3 class="text-center">Search Engine</h3>
  <div class="search-bar row justify-center ">
    <q-input class="col-5 text-field q-mr-md" outlined v-model="text" label="Type what you need" />
    <q-btn class="col-1 btn-search" color="secondary"  label="Search" />
  </div>
</template>

<style>
</style>

<script>
export default {
  name: 'HelloWorld',
};
</script>
