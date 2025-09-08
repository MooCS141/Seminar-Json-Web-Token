import { Outlet } from "react-router";

export default function DefaultLayout() {
    return (
        <div>
            <main className="p-3">
                <Outlet />
            </main>
        </div>
    )
}