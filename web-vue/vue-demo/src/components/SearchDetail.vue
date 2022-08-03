<template>
<div class ="row justify-center q-mt-sm">
  <div class="search-detail column col-6 ">
    <router-link to="/docs" class="nav-link">{{dataDetail.header}}</router-link>
    <p>{{dataDetail.detail}}</p>
  </div>
</div>
</template>

<style>
   .nav-link{
    text-decoration: none ;
    color: #00CED1;
    font-size: 18px;
   }
   .nav-link:hover{
     color: black;
   }
   .router-link-active{
    color: black;
   }
</style>

<script>
import DetailModel from '../model/detailmodel';

export default {
  name: 'SearchDetail',
  props: { dataDetail: { type: DetailModel, require: true } },
};
</script>
