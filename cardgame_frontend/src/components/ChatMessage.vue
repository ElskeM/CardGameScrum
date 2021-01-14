<template>
    <div>
        <div>{{message.name}}</div>
        <div>{{message.message}}</div>
    </div>
</template>

<script>
export default {
    props: ['message'],
}
</script>