<template>
  <div class="newcard" style="text-align:center">
    <NewCardForm/>

  </div>
</template>

<script>
// @ is an alias to /src
import NewCardForm from '../components/NewCardForm.vue'

export default {
  name: 'NewCard',
  components: {
    NewCardForm
    
    
  }
}
</script>
