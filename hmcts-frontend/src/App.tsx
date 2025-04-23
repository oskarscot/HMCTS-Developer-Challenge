import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { DashboardPage} from "@/pages/DashboardPage.tsx";
import { MainLayout } from "./components/layout/MainLayout";
import { ThemeProvider } from "./components/ui/theme-provider";

function App() {
  return (
      <ThemeProvider defaultTheme="system" storageKey="hmcts-task-theme">
        <BrowserRouter>
          <Routes>
              <Route element={<MainLayout />}>
                  <Route path="/" element={<DashboardPage />} />
                  <Route path="*" element={<Navigate to="/" replace />} />
              </Route>
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
  )
}

export default App
