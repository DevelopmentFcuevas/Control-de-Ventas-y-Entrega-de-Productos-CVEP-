// Importación de íconos desde `lucide-react`, una librería de íconos modernos.
import { BarChart2, DollarSign, Menu, Settings, ShoppingBag, ShoppingCart, TrendingUp, Users } from 'lucide-react';

// Importación de React y hooks
import React, { useState } from 'react';

// Librerías para animaciones (animación de transición del sidebar y textos)
import { AnimatePresence, motion } from 'framer-motion';

// Navegación interna con React Router
import { Link } from 'react-router-dom';

/* 
  Lista de elementos que van en el sidebar (menú lateral),
  cada uno con:
  - nombre visible
  - ícono
  - color del ícono
  - ruta de navegación (href)
*/
const SIDEBAR_ITEMS = [
    { name:"Overview", icon:BarChart2, color:"#6366f1", href:"/" },
    { name:"Products", icon:ShoppingBag, color:"#8B5CF6", href:"/products" },
    { name:"Paises", icon:Users, color:"#EC4899", href:"/paises" },
    { name:"Sales", icon:DollarSign, color:"#10B981", href:"/sales" },
    { name:"Orders", icon:ShoppingCart, color:"#F59E0B", href:"/orders" },
    { name:"Analytics", icon:TrendingUp, color:"#3B82F6", href:"/analytics" },
    { name:"Settings", icon:Settings, color:"#6EE7B7", href:"/settings" }
]

// Componente del sidebar (menú lateral)
const Sidebar = () => {
    
    // Estado local para controlar si el sidebar está abierto o colapsado
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);

    return (
        // Contenedor del sidebar con animaciones al cambiar de tamaño
        <motion.div className={`relative z-10 transition-all duration-300 ease-in-out flex-shrink-0 
                                ${ isSidebarOpen ? "w-64" : "w-20"}
                                `}
                    animate={{ whith: isSidebarOpen ? 256 : 80 }}
        >
            
            {/* Estilo visual del contenedor lateral */}
            <div className='h-full bg-gray-800 bg-opacity-50 backdrop-blur-md p-4 flex flex-col border-r border-gray-700'>
                
                {/* Botón para abrir/cerrar el sidebar */}
                <motion.button
                    whileHover={{ scale:1.1 }}
                    whileTap={{ scale: 0.9 }}
                    onClick={() => setIsSidebarOpen(!isSidebarOpen)}
                    className='p-2 rounded-full hover:bg-gray-700 transition-colors max-w-fit'>
                    <Menu size={24} />
                </motion.button>
                
                {/* Navegación del menú */}
                <nav className='mt-8 flex-grow'>
                    
                    {/* Renderiza cada ítem del menú */}
                    {SIDEBAR_ITEMS.map( (item, index) => (
                        <Link key={item.href} to={item.href}>
                            <motion.div className='flex items-center p-4 text-sm font-medium rounded-lg hover:bg-gray-700 transition-colors mb-2'>
                                {/* Ícono del ítem con color personalizado */}
                                <item.icon size={20} style={{ color:item.color, minWidth:"20px" }} />
                                
                                {/* Texto del ítem, animado para aparecer/desaparecer al abrir/cerrar el sidebar */}
                                <AnimatePresence>
                                    {isSidebarOpen && (
                                        <motion.span
                                            className='ml-4 whitespace-nowrap'
                                            initial={{ opacity: 0, width: 0 }}
                                            animate={{opacity: 1, width: "auto"}}
                                            exit={{opacity: 0, width: 0}}
                                            transition={{duration: 0.2, delay: 0.3}}
                                        >
                                            {item.name}
                                        </motion.span>
                                    )}
                                </AnimatePresence>
                            </motion.div>
                        </Link>
                    ))}

                </nav>
            </div>
        </motion.div>
    )
}

/* 
🧠 Consejos extra:
-------------------

* Si querés resaltar el ítem actual, podés usar useLocation() de react-router-dom y compararlo con item.href .

* Podrías extraer cada SidebarItem como un componente propio si el código crece.

* Si en un futuro sumás autenticación o roles, podés filtrar los SIDEBAR_ITEMS según permisos del usuario.
*/
export default Sidebar;