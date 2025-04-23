import { Outlet } from "react-router-dom";
import { Header } from "@/components/layout/Header";
import { Toaster } from "@/components/ui/sonner";

export function MainLayout() {
  return (
    <div className="flex min-h-screen flex-col">
      <Header />
      <main className="flex-1">
        <Outlet />
      </main>
      <Toaster richColors/>
    </div>
  );
}
