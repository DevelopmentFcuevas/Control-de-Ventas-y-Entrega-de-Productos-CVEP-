// Importación de las rutas y componentes necesarios para la navegación
import { Route, Routes } from "react-router-dom";

// Importación de las páginas que componen la aplicación
import OverViewPage from "./pages/OverViewPage";
import ProductsPage from "./pages/ProductsPage";
import Sidebar from "./components/common/Sidebar";
import PaisesPage from "./pages/PaisesPage";
import SalesPage from "./pages/SalesPage";
import OrdersPage from "./pages/OrdersPage";
import AnalyticsPage from "./pages/AnalyticsPage";
import SettingsPage from "./pages/SettingsPage";
import PaisForm from "./pages/PaisForm";
import PaisDetailPage from "./pages/PaisDetailPage";
import PaisEditPage from "./pages/PaisEditPage";
// import { Toaster } from 'react-hot-toast';

// Componente principal de la aplicación
function App() {
  return (
    // Contenedor principal de la app, usa flex para que Sidebar y el contenido estén en fila
    <div className='flex h-screen bg-gray-900 text-gray-100 overflow-hidden'>

      {/* <Toaster /> */}
      
      {/* Fondo decorativo con gradiente y efecto blur (difuminado) */}
      <div className="fixed inset-0 z-0">
        {/* Capa de color degradado */}
        <div className="absolute inset-0 bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 opacity-80"></div>
        
        {/* Capa con efecto de desenfoque */}
        <div className="absolute inset-0 backdrop-blur-sm"></div>
      </div>
      
      {/* Componente de navegación lateral (sidebar) que contiene enlaces a las distintas secciones */}
      <Sidebar />

      {/* Sistema de rutas: cada path carga una página específica */}
      <Routes>
        {/* Página principal o dashboard general */}
        <Route path="/" element={<OverViewPage />} />

        {/* Página de productos */}
        <Route path="/products" element={<ProductsPage />} />

        {/* Página de paises */}
        <Route path="/paises" element={<PaisesPage />} />

        <Route path="/paises/nuevo" element={<PaisForm />} />

        <Route path="/paises/:id" element={<PaisDetailPage />} />

        <Route path="/paises/:id/edit" element={<PaisEditPage />} />

        {/* Página de ventas */}
        <Route path="/sales" element={<SalesPage />} />

        {/* Página de órdenes/pedidos */}
        <Route path="/orders" element={<OrdersPage />} />

        {/* Página de analíticas (gráficos, estadísticas, etc.) */}
        <Route path="/analytics" element={<AnalyticsPage />} />

        {/* Página de configuración */}
        <Route path="/settings" element={<SettingsPage />} />
      </Routes>

    </div>
  )
}

/* 
Tip para modificar fácil:
-------------------------

* Si querés agregar una nueva sección, solo hacés 3 pasos:

1. Crear un nuevo archivo en pages/, por ejemplo ReportsPage.jsx.

2. Agregar la ruta:

import ReportsPage from "./pages/ReportsPage";
<Route path="/reports" element={<ReportsPage />} />

3. Agregar el enlace en Sidebar.
*/

export default App;