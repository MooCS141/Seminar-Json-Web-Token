import { createBrowserRouter } from "react-router";
import DefaultLayout from "@/layouts/DefaultLayout";
import Home from "@/pages/Home";
import Login from "@/pages/Login";
import Register from "@/pages/Register";

export const routers = createBrowserRouter([
    {
        path: "",
        Component: DefaultLayout,
        children: [
            {
                path: "",
                Component: Home
            },
            {
                path: "login",
                Component: Login
            },
            {
                path: "register",
                Component: Register
            },
        ]
    }
])