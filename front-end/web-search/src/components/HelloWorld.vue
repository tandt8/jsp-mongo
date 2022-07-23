<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
  <div clas="search">
    <label for="id"><input type="text" name="text_input" id="input1"></label>
    <button type="submit">Search</button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.search {
  display: flex;

}
 input[type=text] {
  width: 60%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
 }
 button {
  background-color: #4CAF50;
  border: 1px solid #4CAF50;
  color: white;
  padding: 12px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 5px;
 }
 h1 {
    color: #11472f;
 }

h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
