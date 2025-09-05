<template>
  <div>
    <h1>Home Page</h1>
    <RouterLink to="/login">
      <Button class="cursor-pointer">Go to Login Page</Button>
    </RouterLink>
  </div>
</template>

<script lang="ts" setup>
  import { Button } from '@/components/ui/button';
</script>

<style>

</style>