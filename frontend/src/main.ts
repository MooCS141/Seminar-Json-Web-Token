import { createApp } from 'vue'
import '@/style.css'
import App from '@/App.vue'
import { router } from './Router/route'

const app = createApp(App)
app.use(router)
app.mount('#app')
