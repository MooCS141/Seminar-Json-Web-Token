import { createRouter, createWebHistory } from "vue-router";
import Home from "@/pages/Home.vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import Login from "@/pages/Login.vue";

const routes = [
    {
        path: '/',
        component: DefaultLayout,
        children: [
            {
                path: "",
                component: Home
            },
            {
                path: "home",
                component: Home
            },
            {
                path: "login",
                component: Login
            }
        ]
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})