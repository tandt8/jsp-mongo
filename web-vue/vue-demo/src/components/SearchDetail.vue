<template>
<div class ="row justify-center q-mt-sm">
  <div class="search-detail column col-6 ">
    <router-link to="/" class="nav-link">Link doc</router-link>
    <p>This is some text in a paragraph</p>
  </div>
</div>
</template>

<style>
   .nav-link{
    text-decoration: none ;
    color: #00CED1;
    font-size: 18px;
   }
</style>

<script>
export default {
  name: 'SearchDetail',
};
</script>
