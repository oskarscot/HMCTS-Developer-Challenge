import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import { DashboardPage} from "@/pages/DashboardPage.tsx";
import { MainLayout } from "./components/layout/MainLayout";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route element={<MainLayout />}>
                  <Route path="/" element={<DashboardPage />} />
                  <Route path="*" element={<Navigate to="/" replace />} />
              </Route>
          </Routes>
      </BrowserRouter>
  )
}

export default App
